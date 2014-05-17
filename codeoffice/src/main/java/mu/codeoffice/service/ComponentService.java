package mu.codeoffice.service;

import static mu.codeoffice.query.CaseSpecifications.all;
import static mu.codeoffice.query.CaseSpecifications.pageSpecification;
import static mu.codeoffice.query.CaseSpecifications.sort;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import mu.codeoffice.repository.ProjectRepository;
import mu.codeoffice.security.EnterpriseAuthentication;

import org.springframework.data.domain.Page;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComponentService extends ProjectStatisticService {
	
	@Resource
	private ProjectRepository projectRepository;
	
	@Transactional
	public void merge(EnterpriseAuthentication auth, ComponentDTO componentDTO, String projectCode) 
			throws AuthenticationException, InformationException {
		
	}
	
	@Transactional
	public void create(EnterpriseAuthentication auth, Component component, String projectCode)  
			throws AuthenticationException, InformationException {
		if (!componentRepository.isCodeAvailable(projectCode, component.getCode(), auth.getEnterprise(), 0l)) {
			throw new InformationException("Component code '" + component.getCode() + "' is not available.");
		}
		Project project = projectRepository.getProject(projectCode, auth.getEnterprise());
		if (project == null) {
			throw new InformationException("Project is not available.");
		}
		component.setProject(project);
		component.setEnterprise(auth.getEnterprise());
		List<RoleGroup> userGroup = roleGroupRepository.getAuthorizedGroups(project.getId(), ProjectPermission.CASE.getValue());
		List<RoleGroup> leadGroup = roleGroupRepository.getAuthorizedGroups(project.getId(), ProjectPermission.VERSION_COMPONENT_MANAGE.getValue());
		if (!RoleGroup.hasUser(userGroup, component.getDefaultAssignee())) {
			throw new InformationException("Default assignee for component is not valid.");
		}
		if (!RoleGroup.hasUser(userGroup, component.getDefaultReporter())) {
			throw new InformationException("Default reporter for component is not valid.");
		}
		if (!RoleGroup.hasUser(leadGroup, component.getLead())) {
			throw new InformationException("Component lead is not valid.");
		}
		componentRepository.save(component);
	}
	
	@Transactional
	public void update(EnterpriseAuthentication auth, String originalCode, Component component, String projectCode) 
			throws AuthenticationException, InformationException {
		Project project = projectRepository.getProject(projectCode, auth.getEnterprise());
		if (project == null) {
			throw new InformationException("Project is not available.");
		}
		if (!componentRepository.isSameObject(auth.getEnterprise(), originalCode, component.getId())) {
			throw new InformationException("Component is not available");
		}
		if (!componentRepository.isCodeAvailable(projectCode, component.getCode(), auth.getEnterprise(), component.getId())) {
			throw new InformationException("Component code '" + component.getCode() + "' is not available.");
		}
		component.setProject(project);
		component.setEnterprise(auth.getEnterprise());
		List<RoleGroup> userGroup = roleGroupRepository.getAuthorizedGroups(project.getId(), ProjectPermission.CASE.getValue());
		List<RoleGroup> leadGroup = roleGroupRepository.getAuthorizedGroups(project.getId(), ProjectPermission.VERSION_COMPONENT_MANAGE.getValue());
		if (!RoleGroup.hasUser(userGroup, component.getDefaultAssignee())) {
			throw new InformationException("Default assignee for component is not valid.");
		}
		if (!RoleGroup.hasUser(userGroup, component.getDefaultReporter())) {
			throw new InformationException("Default reporter for component is not valid.");
		}
		if (!RoleGroup.hasUser(leadGroup, component.getLead())) {
			throw new InformationException("Component lead is not valid.");
		}
		componentRepository.save(component);
	}
	
	@Transactional(readOnly = true)
	public List<Component> getComponents(EnterpriseAuthentication auth, ComponentDTO componentDTO) {
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
