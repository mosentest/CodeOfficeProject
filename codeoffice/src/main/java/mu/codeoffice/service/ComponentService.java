package mu.codeoffice.service;

import static mu.codeoffice.query.CaseSpecifications.all;
import static mu.codeoffice.query.CaseSpecifications.pageSpecification;
import static mu.codeoffice.query.CaseSpecifications.sort;

import java.util.List;
import java.util.Map;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.data.Summary;
import mu.codeoffice.dto.ComponentDTO;
import mu.codeoffice.entity.Case;
import mu.codeoffice.entity.Component;
import mu.codeoffice.entity.EnterpriseUser;
import mu.codeoffice.entity.Label;
import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.RoleGroup;
import mu.codeoffice.entity.Version;
import mu.codeoffice.enums.CasePriority;
import mu.codeoffice.enums.CaseStatus;
import mu.codeoffice.enums.ProjectPermission;
import mu.codeoffice.security.EnterpriseAuthentication;

import org.springframework.data.domain.Page;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComponentService extends ProjectStatisticService {
	
	@Transactional
	public void merge(EnterpriseAuthentication auth, ComponentDTO<Component> componentDTO, String projectCode) 
			throws AuthenticationException, InformationException {
		
	}
	
	@Transactional(readOnly = true)
	public List<Component> getComponents(EnterpriseAuthentication auth, ComponentDTO<Component> componentDTO) {
		return componentRepository.getComponents(auth.getEnterprise(), componentDTO.getProject(), componentDTO.getComponentCode());
	}
	
	@Transactional(readOnly = true)
	public List<Component> getProjectComponents(EnterpriseAuthentication auth, String project) {
		return componentRepository.getProjectComponents(auth.getEnterprise(), project);
	}
	
	@Transactional(readOnly = true)
	public List<RoleGroup> getAuthorizedUsers(EnterpriseAuthentication auth, Long project, ProjectPermission permission) {
		return roleGroupRepository.getAuthorizedGroups(project, permission.getValue());
	}
	
	@Transactional
	public void delete(String projectCode, String componentCode, EnterpriseAuthentication auth) 
			throws AuthenticationException, InformationException {
		Component component = componentRepository.getProjectComponent(projectCode, componentCode, auth.getEnterprise());
		if (component.getNoCase() > 0) {
			throw new InformationException(String.format("Unable to delete component '%s', it has %d related cases.", component.getName(), component.getNoCase()));
		}
		componentRepository.delete(component);
	}
	
	public Component getProjectComponent(String projectCode, String componentCode, EnterpriseAuthentication auth) {
		return componentRepository.getProjectComponent(projectCode, componentCode, auth.getEnterprise());
	}

	public Component getProjectComponent(Project project, String componentCode) {
		return componentRepository.getProjectComponent(project.getId(), componentCode);
	}
	
	@Transactional(readOnly = true)
	public List<Summary> getComponentMonthlySummary(Project project, Component component) {
		return getMonthlySummary(project.getId(), null, null, component.getId(), null, null, null, null, null, null);
	}

	@Transactional(readOnly = true)
	public Page<Case> getRelatedCase(Project project, Component component, int pageIndex, int size, boolean ascending, String column) {
		Page<Case> page = caseRepository.findAll(all(null, project.getId(), null, null, component.getId(), null, null, null, null, null, null), 
				pageSpecification(pageIndex, size, sort(ascending, Case.getSortColumn(column))));
		return page;
	}
	
	@Transactional(readOnly = true)
	public Map<Version, Integer> getVersionSummary(Project project, Component component) {
		return getVersionSummary(versionRepository.getProjectVersions(project.getId()), project.getId(), null, component.getId(), null, null, null, null, null, null);
	}

	@Transactional(readOnly = true)
	public Map<Label, Integer> getLabelSummary(Project project, Component component) {
		return getLabelSummary(labelRepository.getProjectLabels(project.getId()), project.getId(), null, null, component.getId(), null, null, null, null, null);
	}

	@Transactional(readOnly = true)
	public Map<CaseStatus, Integer> getCaseStatusSummary(Project project, Component component) {
		return getCaseStatusSummary(project.getId(), null, null, component.getId(), null, null, null, null, null);
	}

	@Transactional(readOnly = true)
	public Map<CasePriority, Integer> getCasePrioritySummary(Project project, Component component) {
		return getCasePrioritySummary(project.getId(), null, null, component.getId(), null, null, null, null, null);
	}

	@Transactional(readOnly = true)
	public Map<EnterpriseUser, Integer> getAssigneeSummary(Project project, Component component) {
		return getAssigneeSummary(roleGroupRepository.getUsers(project.getId()), project.getId(), null, null, component.getId(), null, null, null, null, null);
	}
}
