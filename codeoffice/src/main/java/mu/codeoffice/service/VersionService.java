package mu.codeoffice.service;

import static mu.codeoffice.query.CaseSpecifications.all;
import static mu.codeoffice.query.CaseSpecifications.pageSpecification;
import static mu.codeoffice.query.CaseSpecifications.sort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VersionService extends VersionStatisticService {

	public Version getProjectVersion(Project project, String version) {
		return versionRepository.getProjectVersion(project, version);
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
