package mu.codeoffice.service;

import static mu.codeoffice.query.CaseSpecifications.all;
import static mu.codeoffice.query.CaseSpecifications.pageSpecification;
import static mu.codeoffice.query.CaseSpecifications.sort;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.data.Summary;
import mu.codeoffice.entity.Case;
import mu.codeoffice.entity.Component;
import mu.codeoffice.entity.EnterpriseUser;
import mu.codeoffice.entity.Label;
import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.Version;
import mu.codeoffice.enums.CasePriority;
import mu.codeoffice.enums.CaseStatus;
import mu.codeoffice.enums.CaseType;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;

import org.springframework.data.domain.Page;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VersionService extends ProjectStatisticService {
	
	@Transactional
	public void create(EnterpriseAuthentication auth, String projectCode, Version version) throws AuthenticationException, InformationException {
		
	}
	
	@Transactional
	public void delete(EnterpriseAuthentication auth, String projectCode, String versionCode) throws AuthenticationException, InformationException {
		Version version = versionRepository.getProjectVersion(auth.getEnterprise(), projectCode, versionCode);
		if (version == null) {
			throw new EnterpriseAuthenticationException("Access denied.");
		}
		if (version.getNoRelated() > 0 || version.getNoRelease() > 0) {
			throw new InformationException("Version has related cases, can not delete.");
		}
		versionRepository.delete(version);
	}
	
	@Transactional
	public void edit(EnterpriseAuthentication auth, String projectCode, String versionCode, Version version) throws AuthenticationException, InformationException {
		
	}

	@Transactional
	public void start(EnterpriseAuthentication auth, String projectCode, String versionCode) throws AuthenticationException, InformationException {
		Version version = versionRepository.getProjectVersion(auth.getEnterprise(), projectCode, versionCode);
		if (version == null) {
			throw new EnterpriseAuthenticationException("Access denied.");
		}
		if (version.isStarted()) {
			throw new InformationException("Version '" + versionCode + "' has already started.");
		}
		version.setStarted(true);
		version.setStart(new Date());
		versionRepository.save(version);
	}
	
	@Transactional
	public void stop(EnterpriseAuthentication auth, String projectCode, String versionCode) throws AuthenticationException, InformationException {
		Version version = versionRepository.getProjectVersion(auth.getEnterprise(), projectCode, versionCode);
		if (version == null) {
			throw new EnterpriseAuthenticationException("Access denied.");
		}
		if (!version.isStarted()) {
			throw new InformationException("Can not proceed.");
		}
		version.setStarted(false);
		version.setStart(null);
		versionRepository.save(version);
	}
	
	@Transactional
	public void release(EnterpriseAuthentication auth, String projectCode, String versionCode) throws AuthenticationException, InformationException {
		Version version = versionRepository.getProjectVersion(auth.getEnterprise(), projectCode, versionCode);
		if (version == null) {
			throw new EnterpriseAuthenticationException("Access denied.");
		}
		if (!version.isStarted()) {
			throw new InformationException("Version '" + versionCode + "' is not started.");
		}
		if (version.isReleased()) {
			throw new InformationException("Version '" + versionCode + "' has already been released.");
		}
		version.setReleased(true);
		version.setRelease(new Date());
		versionRepository.save(version);
	}
	
	
	@Transactional(readOnly = true)
	public Version getProjectVersion(EnterpriseAuthentication auth, String projectCode, String versionCode) throws AuthenticationException {
		Version version = versionRepository.getProjectVersion(auth.getEnterprise(), projectCode, versionCode);
		if (version == null) {
			throw new EnterpriseAuthenticationException("Permission denied.");
		}
		return version;
	}
	
	@Transactional(readOnly = true)
	public List<Summary> getVersionMonthlySummary(Project project, Version version) {
		return getMonthlySummary(project.getId(), version.getId(), null, null, null, null, null, null, null, null);
	}
	
	@Transactional(readOnly = true)
	public Map<CaseType, List<Case>> getReleaseNote(Long project, Long version) {
		Map<CaseType, List<Case>> map = new HashMap<>();
		for (CaseType type : CaseType.values()) {
			List<Case> cases = caseRepository.findAll(all(null, project, null, version, null, null, null, null, null, type, null));
			if (cases.size() > 0) {
				map.put(type, cases);
			}
		}
		return map;
	}

	@Transactional(readOnly = true)
	public Page<Case> getRelatedCase(Long project, Long version, int pageIndex, int size, boolean ascending, String column) {
		Page<Case> page = caseRepository.findAll(all(null, project, version, null, null, null, null, null, null, null, null), 
				pageSpecification(pageIndex, size, sort(ascending, Case.getSortColumn(column))));
		return page;
	}
	
	@Transactional(readOnly = true)
	public Page<Case> getReleaseCase(Long project, Long version, int pageIndex, int size, boolean ascending, String column) {
		Page<Case> page = caseRepository.findAll(all(null, project, null, version, null, null, null, null, null, null, null), 
				pageSpecification(pageIndex, size, sort(ascending, Case.getSortColumn(column))));
		return page;
	}
	
	@Transactional(readOnly = true)
	public Map<Component, Integer> getComponentSummary(Project project, Long version, Long releaseVersion) {
		return getComponentSummary(componentRepository.getProjectComponents(project.getId()), project.getId(), version, releaseVersion, null, null, null, null, null, null);
	}

	@Transactional(readOnly = true)
	public Map<Label, Integer> getLabelSummary(Project project, Long version, Long releaseVersion) {
		return getLabelSummary(labelRepository.getProjectLabels(project.getId()), project.getId(), version, releaseVersion, null, null, null, null, null, null);
	}

	@Transactional(readOnly = true)
	public Map<CaseStatus, Integer> getCaseStatusSummary(Project project, Long version, Long releaseVersion) {
		return getCaseStatusSummary(project.getId(), version, releaseVersion, null, null, null, null, null, null);
	}

	@Transactional(readOnly = true)
	public Map<CasePriority, Integer> getCasePrioritySummary(Project project, Long version, Long releaseVersion) {
		return getCasePrioritySummary(project.getId(), version, releaseVersion, null, null, null, null, null, null);
	}

	@Transactional(readOnly = true)
	public Map<EnterpriseUser, Integer> getAssigneeSummary(Project project, Long version, Long releaseVersion) {
		return getAssigneeSummary(roleGroupRepository.getUsers(project.getId()), project.getId(), version, releaseVersion, null, null, null, null, null, null);
	}
}
