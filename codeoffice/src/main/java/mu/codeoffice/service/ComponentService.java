package mu.codeoffice.service;

import static mu.codeoffice.query.IssueSpecifications.all;
import static mu.codeoffice.query.GenericSpecifications.pageSpecification;
import static mu.codeoffice.query.GenericSpecifications.sort;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.data.Summary;
import mu.codeoffice.dto.ComponentDTO;
import mu.codeoffice.entity.Component;
import mu.codeoffice.entity.EnterpriseUser;
import mu.codeoffice.entity.Issue;
import mu.codeoffice.entity.Label;
import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.Version;
import mu.codeoffice.enums.IssuePriority;
import mu.codeoffice.enums.IssueStatus;
import mu.codeoffice.repository.ProjectRepository;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComponentService extends ProjectStatisticService {
	
	private Logger logger = Logger.getLogger(ComponentService.class);
	
	@Resource
	private ProjectRepository projectRepository;
	
	@Transactional
	public void merge(EnterpriseAuthentication auth, String projectCode, String componentCode, ComponentDTO mergeComponent) 
			throws AuthenticationException, InformationException {
		if (!componentRepository.isSameObject(auth.getEnterprise(), projectCode, mergeComponent.getCode(), mergeComponent.getId())) {
			throw new EnterpriseAuthenticationException("Unable to access component '" + componentCode + "'");
		}
		Component component = componentRepository.getProjectComponent(projectCode, componentCode, auth.getEnterprise());
		List<Component> mergeList = componentRepository.getComponents(auth.getEnterprise(), projectCode, componentCode, mergeComponent.getComponentCode());
		for (Component componentObject : mergeList) {
			List<Issue> relatedCases = caseRepository.findAll(
					all(null, component.getProject().getId(), null, null, componentObject.getId(), null, null, null, null, null, null));
			for (Issue caseObject : relatedCases) {
				if (!component.getIssues().contains(caseObject)) {
					component.getIssues().add(caseObject);
				}
				if (!caseObject.getComponents().contains(component)) {
					caseObject.getComponents().add(component);
				}
				caseObject.getComponents().remove(componentObject);
				caseRepository.save(caseObject);
			}
			componentObject.getIssues().clear();
			componentRepository.save(componentObject);
		}
		componentRepository.delete(mergeList);
		logger.debug("Components: '" + String.join("', '", mergeComponent.getComponentCode()) + "' has been merged.");
		component.setTotalIssues(component.getIssues().size());
		componentRepository.save(component);
	}
	
	@Transactional
	public void create(EnterpriseAuthentication auth, Component component, String projectCode)  
			throws AuthenticationException, InformationException {
		if (!componentRepository.isCodeAvailable(projectCode, component.getCode(), auth.getEnterprise(), 0l)) {
			throw new InformationException("Component code '" + component.getCode() + "' is not available");
		}
		Project project = projectRepository.getProject(projectCode, auth.getEnterprise());
		if (project == null) {
			throw new EnterpriseAuthenticationException("Unable to access project '" + projectCode + "'");
		}
		component.setProject(project);
		component.setEnterprise(auth.getEnterprise());
		componentRepository.save(component);
	}
	
	@Transactional
	public void update(EnterpriseAuthentication auth, String originalCode, Component component, String projectCode) 
			throws AuthenticationException, InformationException {
		Project project = projectRepository.getProject(projectCode, auth.getEnterprise());
		if (project == null) {
			throw new EnterpriseAuthenticationException("Unable to access project '" + projectCode + "'");
		}
		if (!componentRepository.isSameObject(auth.getEnterprise(), projectCode, originalCode, component.getId())) {
			throw new EnterpriseAuthenticationException("Unable to access component '" + component.getCode() + "'");
		}
		if (!componentRepository.isCodeAvailable(projectCode, component.getCode(), auth.getEnterprise(), component.getId())) {
			throw new InformationException("Component code '" + component.getCode() + "' is not available.");
		}
		component.setProject(project);
		component.setEnterprise(auth.getEnterprise());
		componentRepository.save(component);
	}
	
	@Transactional(readOnly = true)
	public List<Component> getComponents(EnterpriseAuthentication auth, ComponentDTO componentDTO) {
		return componentRepository.getComponents(auth.getEnterprise(), componentDTO.getProject(), "", componentDTO.getComponentCode());
	}
	
	@Transactional(readOnly = true)
	public List<Component> getProjectComponents(EnterpriseAuthentication auth, String project) {
		return componentRepository.getProjectComponents(auth.getEnterprise(), project);
	}
	
	@Transactional
	public void delete(String projectCode, String componentCode, EnterpriseAuthentication auth) 
			throws AuthenticationException, InformationException {
		Component component = componentRepository.getProjectComponent(projectCode, componentCode, auth.getEnterprise());
		if (component.getTotalIssues() > 0) {
			throw new InformationException(String.format("Unable to delete component '%s', it has %d related cases.", component.getName(), component.getTotalIssues()));
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
	public Page<Issue> getRelatedCase(Project project, Component component, int pageIndex, int size, boolean ascending, String column) {
		Page<Issue> page = caseRepository.findAll(all(null, project.getId(), null, null, component.getId(), null, null, null, null, null, null), 
				pageSpecification(pageIndex, size, sort(ascending, Issue.getSortColumn(column))));
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
	public Map<IssueStatus, Integer> getIssueStatusSummary(Project project, Component component) {
		return getIssueStatusSummary(project.getId(), null, null, component.getId(), null, null, null, null, null);
	}

	@Transactional(readOnly = true)
	public Map<IssuePriority, Integer> getCasePrioritySummary(Project project, Component component) {
		return getIssuePrioritySummary(project.getId(), null, null, component.getId(), null, null, null, null, null);
	}

	@Transactional(readOnly = true)
	public Map<EnterpriseUser, Integer> getAssigneeSummary(Project project, Component component) {
		return getAssigneeSummary(null, project.getId(), null, null, component.getId(), null, null, null, null, null);
	}
}
