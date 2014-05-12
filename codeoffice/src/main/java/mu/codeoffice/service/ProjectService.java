package mu.codeoffice.service;

import static mu.codeoffice.query.CaseSpecifications.countAll;
import static mu.codeoffice.query.CaseSpecifications.countUnresolved;
import static mu.codeoffice.query.CaseSpecifications.countResolved;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import mu.codeoffice.repository.ComponentRepository;
import mu.codeoffice.repository.LabelRepository;
import mu.codeoffice.repository.ProjectActivityRepository;
import mu.codeoffice.repository.ProjectNoteRepository;
import mu.codeoffice.repository.ProjectRepository;
import mu.codeoffice.repository.RoleGroupRepository;
import mu.codeoffice.repository.VersionRepository;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;
import mu.codeoffice.utility.DateUtil;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService {

	@Resource
	private ProjectRepository projectRepository;
	
	@Resource
	private VersionRepository versionRepository;
	
	@Resource
	private LabelRepository labelRepository;
	
	@Resource
	private ComponentRepository componentRepository;
	
	@Resource
	private CaseRepository caseRepository;

	@Resource
	private ProjectActivityRepository projectActivityRepository;
	
	@Resource
	private RoleGroupRepository roleGroupRepository;
	
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
		List<Summary> summary = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -29);
		for (int i = 0; i < 30; i++) {
			Date date = calendar.getTime();
			int noCount = (int) caseRepository.count(countAll(date, project.getId(), null, null, null, null, null, null, null, null));
			int noResolved = (int) caseRepository.count(countUnresolved(date, project.getId(), null, null, null, null, null, null, null, null));
			Summary s = new Summary(date, noCount, noResolved);
			setVersionMark(versionRepository.getProjectVersions(project.getId()), s, date);
			summary.add(s);
			calendar.add(Calendar.DATE, 1);
		}
		return summary;
	}
	
	@Transactional(readOnly = true)
	@Cacheable(value = "projectWeeklySummaryCache", key = "#project.id")
	public List<Summary> getProjectWeeklySummary(Project project) {
		List<Summary> summary = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -6);
		for (int i = 0; i < 7; i++) {
			Date date = calendar.getTime();
			int noCount = (int) caseRepository.count(countAll(date, project.getId(), null, null, null, null, null, null, null, null));
			int noResolved = (int) caseRepository.count(countUnresolved(date, project.getId(), null, null, null, null, null, null, null, null));
			Summary s = new Summary(date, noCount, noResolved);
			setVersionMark(versionRepository.getProjectVersions(project.getId()), s, date);
			summary.add(s);
			calendar.add(Calendar.DATE, 1);
		}
		return summary;
	}

	@Transactional(readOnly = true)
	public List<Summary> getAssigneeSummary(EnterpriseUser assignee) {
		List<Summary> summary = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -29);
		for (int i = 0; i < 30; i++) {
			Date date = calendar.getTime();
			int noCount = (int) caseRepository.count(countAll(date, null, null, null, null, assignee.getId(), null, null, null, null));
			int noResolved = (int) caseRepository.count(countResolved(date, null, null, null, null, assignee.getId(), null, null, null));
			summary.add(new Summary(date, noCount, noResolved));
			calendar.add(Calendar.DATE, 1);
		}
		return summary;
	}

	@Transactional(readOnly = true)
	public List<Summary> getReporterSummary(EnterpriseUser reporter) {
		List<Summary> summary = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -29);
		for (int i = 0; i < 30; i++) {
			Date date = calendar.getTime();
			int noCount = (int) caseRepository.count(countAll(date, null, null, null, null, null, reporter.getId(), null, null, null));
			int noResolved = (int) caseRepository.count(countResolved(date, null, null, null, null, null, reporter.getId(), null, null));
			summary.add(new Summary(date, noCount, noResolved));
			calendar.add(Calendar.DATE, 1);
		}
		return summary;
	}
	
	public List<Summary> getProjectAssigneeSummary(Project project, EnterpriseUser assignee) {
		List<Summary> summary = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -29);
		for (int i = 0; i < 30; i++) {
			Date date = calendar.getTime();
			int noCount = (int) caseRepository.count(countAll(date, project.getId(), null, null, null, assignee.getId(), null, null, null, null));
			int noResolved = (int) caseRepository.count(countResolved(date, project.getId(), null, null, null, assignee.getId(), null, null, null));
			Summary s = new Summary(date, noCount, noResolved);
			setVersionMark(project.getVersions(), s, date);
			summary.add(s);
			calendar.add(Calendar.DATE, 1);
		}
		return summary;
	}

	@Transactional(readOnly = true)
	public Map<Version, Integer> getVersionSummary(Project project) {
		Map<Version, Integer> map = new LinkedHashMap<>();
		for (Version version : versionRepository.getProjectVersions(project.getId())) {
			map.put(version, (int) caseRepository.count(countUnresolved(null, project.getId(), version.getId(), null, null, null, null, null, null, null)));
		}
		clearZeroValues(map);
		return map;
	}

	@Transactional(readOnly = true)
	public Map<Version, Integer> getRoadMap(Project project) {
		Map<Version, Integer> map = new LinkedHashMap<>();
		for (Version version : versionRepository.getProjectVersions(project.getId())) {
			map.put(version, (int) caseRepository.count(countResolved(null, project.getId(), version.getId(), null, null, null, null, null, null)));
		}
		clearZeroValues(map);
		return map;
	}

	@Transactional(readOnly = true)
	public Map<Component, Integer> getComponentSummary(Project project) {
		Map<Component, Integer> map = new LinkedHashMap<>();
		for (Component component : componentRepository.getProjectComponents(project.getId())) {
			map.put(component, (int) caseRepository.count(countUnresolved(null, project.getId(), null, component.getId(), null, null, null, null, null, null)));
		}
		clearZeroValues(map);
		return map;
	}

	@Transactional(readOnly = true)
	public Map<Label, Integer> getLabelSummary(Project project) {
		Map<Label, Integer> map = new LinkedHashMap<>();
		for (Label label : labelRepository.getProjectLabels(project.getId())) {
			map.put(label, (int) caseRepository.count(countUnresolved(null, project.getId(), null, null, label.getId(), null, null, null, null, null)));
		}
		clearZeroValues(map);
		return map;
	}

	@Transactional(readOnly = true)
	public Map<CaseStatus, Integer> getCaseStatusSummary(Project project) {
		Map<CaseStatus, Integer> map = new LinkedHashMap<>();
		for (CaseStatus status : CaseStatus.values()) {
			map.put(status, (int) caseRepository.count(countAll(null, project.getId(), null, null, null, null, null, status, null, null)));
		}
		clearZeroValues(map);
		return map;
	}

	@Transactional(readOnly = true)
	public Map<CasePriority, Integer> getCasePrioritySummary(Project project) {
		Map<CasePriority, Integer> map = new LinkedHashMap<>();
		for (CasePriority priority : CasePriority.values()) {
			map.put(priority, (int) caseRepository.count(countUnresolved(null, project.getId(), null, null, null, null, null, null, null, priority)));
		}
		clearZeroValues(map);
		return map;
	}

	@Transactional(readOnly = true)
	public Map<EnterpriseUser, Integer> getAssigneeSummary(Project project) {
		Map<EnterpriseUser, Integer> map = new LinkedHashMap<>();
		for (RoleGroup roleGroup : roleGroupRepository.getProjectRoleGroups(project.getId())) {
			for (EnterpriseUser user : roleGroup.getUsers()) {
				if (map.get(user) == null) {
					map.put(user, (int) caseRepository.count(countUnresolved(null, project.getId(), null, null, null, user.getId(), null, null, null, null)));
				}
			}
		}
		clearZeroValues(map);
		return map;
	}
	
	private void setVersionMark(List<Version> versions, Summary summary, Date date) {
		for (Version version : versions) {
			summary.setVersionStart(DateUtil.isSameDay(version.getStart(), date));
			summary.setVersionRelease(DateUtil.isSameDay(version.getRelease(), date));
			summary.setVersionDelay(DateUtil.isSameDay(version.getDelay(), date));
			if (summary.isVersionDelay() || summary.isVersionRelease() || summary.isVersionStart()) {
				summary.setVersionCode(version.getCode());
				break;
			}
		}
	}
	
	private <K> void clearZeroValues(Map<K, Integer> map) {
		Iterator<Map.Entry<K, Integer>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<K, Integer> entry = it.next();
			if (entry.getValue() == 0) {
				it.remove();
			}
		}
	}
	
}