package mu.codeoffice.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mu.codeoffice.entity.Component;
import mu.codeoffice.entity.EnterpriseUser;
import mu.codeoffice.entity.Label;
import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.ProjectActivity;
import mu.codeoffice.entity.ProjectNote;
import mu.codeoffice.entity.RoleGroup;
import mu.codeoffice.entity.Summary;
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
public class ProjectService extends VersionStatisticService {

	@Resource
	private ProjectRepository projectRepository;
	
	@Resource
	private CaseRepository caseRepository;

	@Resource
	private ProjectActivityRepository projectActivityRepository;
	
	@Resource
	private ProjectNoteRepository projectNoteRepository;
	
	public List<ProjectNote> getProjectNote(Long project) {
		return projectNoteRepository.getProjectNotes(project);
	}
	
	public List<Version> getProjectVersions(Long project) {
		return versionRepository.getProjectVersions(project);
	}
	
	public List<Component> getProjectComponents(Long project) {
		return componentRepository.getProjectComponents(project);
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
		List<RoleGroup> roleGroups = roleGroupRepository.getProjectRoleGroups(project);
		for (RoleGroup roleGroup : roleGroups) {
			roleGroup.getRole().getId();
			roleGroup.getUsers().size();
		}
		return roleGroups;
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
		project.getLead().getId();
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