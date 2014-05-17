package mu.codeoffice.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mu.codeoffice.data.Summary;
import mu.codeoffice.dto.ProjectDTO;
import mu.codeoffice.entity.Component;
import mu.codeoffice.entity.EnterpriseUser;
import mu.codeoffice.entity.Label;
import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.ProjectActivity;
import mu.codeoffice.entity.ProjectNote;
import mu.codeoffice.entity.ProjectRole;
import mu.codeoffice.entity.RoleGroup;
import mu.codeoffice.entity.Version;
import mu.codeoffice.enums.CasePriority;
import mu.codeoffice.enums.CaseStatus;
import mu.codeoffice.repository.CaseRepository;
import mu.codeoffice.repository.ProjectActivityRepository;
import mu.codeoffice.repository.ProjectNoteRepository;
import mu.codeoffice.repository.ProjectRepository;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService extends ProjectStatisticService {

	@Resource
	private ProjectRepository projectRepository;
	
	@Resource
	private CaseRepository caseRepository;

	@Resource
	private ProjectActivityRepository projectActivityRepository;
	
	@Resource
	private ProjectNoteRepository projectNoteRepository;
	
	@Transactional(readOnly = true)
	@Cacheable(value = "projectRoleCache", key = "#project + '_' + #user.id")
	public int getProjectAuthority(EnterpriseUser user, String project) throws EnterpriseAuthenticationException {
		try {
			ProjectRole role = roleGroupRepository.getProjectRole(user, project);
			return role.getValue();
		} catch (Exception e) {
			throw new EnterpriseAuthenticationException("Access denied.");
		}
	}
	
	@Transactional(readOnly = true)
	public Project createProject(EnterpriseAuthentication auth, ProjectDTO projectDTO) {
		return null;
	}
	
	public List<ProjectNote> getProjectNote(Long project) {
		return projectNoteRepository.getProjectNotes(project);
	}
	
	public List<Version> getProjectVersions(Long project) {
		return versionRepository.getProjectVersions(project);
	}
	
	@Transactional(readOnly = true)
	public List<Component> getProjectComponents(Long project) {
		List<Component> components = componentRepository.getProjectComponents(project);
		components.forEach(component -> {
			component.getLead().getId();
			component.getDefaultAssignee().getId();
			component.getDefaultReporter().getId();
		});
		return components;
	}
	
	public List<Label> getProjectLabels(Long project) {
		return labelRepository.getProjectLabels(project);
	}

	@Cacheable(value = "unreleasedVersionCache", key = "#project.id")
	public List<Version> getUnreleasedVersions(Project project) {
		return versionRepository.getUnreleasedVersions(project);
	}
	
	@Transactional(readOnly = true)
	public List<RoleGroup> getProjectRoleGroups(Long project) {
		return roleGroupRepository.getProjectRoleGroups(project);
	}
	
	@Transactional(readOnly = true)
	@Cacheable(value = "projectInfoCache", key = "#code + '_' + #auth.enterpriseUser.id")
	public Project getProjectInfo(String code, EnterpriseAuthentication auth) throws EnterpriseAuthenticationException {
		Project project = null;
		if (auth.hasProjectAuthority()) {
			project = projectRepository.getProject(code, auth.getEnterprise());
		} else {
			project = projectRepository.getProject(code, auth.getEnterprise(), auth.getEnterpriseUser());
		}
		if (project == null) {
			throw new EnterpriseAuthenticationException("You have no access to this project.");
		}
		project.getCategory().getId();
		return project;
	}

	@Transactional(readOnly = true)
	public List<ProjectActivity> getProjectActivities(String projectCode, EnterpriseAuthentication auth, int offset, int size) {
		return projectActivityRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	@Cacheable(value = "projectMonthlySummaryCache", key = "#project.id")
	public List<Summary> getProjectMonthlySummary(Project project) {
		return getMonthlySummary(project.getId(), null, null, null, null, null, null, null, null, null);
	}
	
	@Transactional(readOnly = true)
	@Cacheable(value = "projectWeeklySummaryCache", key = "#project.id")
	public List<Summary> getProjectWeeklySummary(Project project) {
		return getWeeklySummary(project.getId(), null, null, null, null, null, null, null, null, null);
	}

	@Transactional(readOnly = true)
	public Map<Version, Integer> getVersionSummary(Project project) {
		return getVersionSummary(versionRepository.getProjectVersions(project.getId()), project.getId(), null, null, null, null, null, null, null, null);
	}

	@Transactional(readOnly = true)
	public Map<Version, Integer> getRoadMap(Project project) {
		return getRoadMap(versionRepository.getProjectVersions(project.getId()), project.getId(), null, null, null, null, null, null, null);
	}

	@Transactional(readOnly = true)
	public Map<Component, Integer> getComponentSummary(Project project) {
		return getComponentSummary(componentRepository.getProjectComponents(project.getId()), project.getId(), null, null, null, null, null, null, null, null);
	}

	@Transactional(readOnly = true)
	public Map<Label, Integer> getLabelSummary(Project project) {
		return getLabelSummary(labelRepository.getProjectLabels(project.getId()), project.getId(), null, null, null, null, null, null, null, null);
	}

	@Transactional(readOnly = true)
	public Map<CaseStatus, Integer> getCaseStatusSummary(Project project) {
		return getCaseStatusSummary(project.getId(), null, null, null, null, null, null, null, null);
	}

	@Transactional(readOnly = true)
	public Map<CasePriority, Integer> getCasePrioritySummary(Project project) {
		return getCasePrioritySummary(project.getId(), null, null, null, null, null, null, null, null);
	}

	@Transactional(readOnly = true)
	public Map<EnterpriseUser, Integer> getAssigneeSummary(Project project) {
		return getAssigneeSummary(roleGroupRepository.getUsers(project.getId()), project.getId(), null, null, null, null, null, null, null, null);
	}
	
}