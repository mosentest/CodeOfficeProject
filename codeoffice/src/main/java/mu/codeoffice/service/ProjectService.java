package mu.codeoffice.service;

import static mu.codeoffice.query.CaseSpecifications.countAll;
import static mu.codeoffice.query.CaseSpecifications.countUnresolved;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.ProjectActivity;
import mu.codeoffice.entity.Summary;
import mu.codeoffice.entity.Version;
import mu.codeoffice.repository.CaseRepository;
import mu.codeoffice.repository.ProjectActivityRepository;
import mu.codeoffice.repository.ProjectRepository;
import mu.codeoffice.repository.VersionRepository;
import mu.codeoffice.security.EnterpriseAuthentication;
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
	private CaseRepository caseRepository;

	@Resource
	private ProjectActivityRepository projectActivityRepository;
	
	@Transactional(readOnly = true)
	@Cacheable(value = "projectInfoCache", key = "#code + #auth.enterprise.code")
	public Project getProjectInfo(String code, EnterpriseAuthentication auth) {
		Project project = projectRepository.getProject(code, auth.getEnterprise(), auth.getEnterpriseUser());
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
		List<Summary> summary = new ArrayList<Summary>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -30);
		for (int i = 0; i < 30; i++) {
			Date date = calendar.getTime();
			int noCount = (int) caseRepository.count(countAll(date, project.getId(), 0l, 0l, 0l, 0l, 0l, null, null, null));
			int noResolved = (int) caseRepository.count(countUnresolved(date, project.getId(), 0l, 0l, 0l, 0l, 0l, null, null, null));
			Summary s = new Summary(date, noCount, noResolved);
			setVersionMark(project.getVersions(), s, date);
			summary.add(s);
			calendar.add(Calendar.DATE, 1);
		}
		return summary;
	}
	
	@Transactional(readOnly = true)
	@Cacheable(value = "projectWeeklySummaryCache", key = "#project.id")
	public List<Summary> getProjectWeeklySummary(Project project) {
		List<Summary> summary = new ArrayList<Summary>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -7);
		for (int i = 0; i < 7; i++) {
			Date date = calendar.getTime();
			int noCount = (int) caseRepository.count(countAll(date, project.getId(), 0l, 0l, 0l, 0l, 0l, null, null, null));
			int noResolved = (int) caseRepository.count(countUnresolved(date, project.getId(), 0l, 0l, 0l, 0l, 0l, null, null, null));
			Summary s = new Summary(date, noCount, noResolved);
			setVersionMark(project.getVersions(), s, date);
			summary.add(s);
			calendar.add(Calendar.DATE, 1);
		}
		return summary;
	}

	@Cacheable(value = "unreleasedVersionCache", key = "#project.id")
	public List<Version> getUnreleasedVersions(Project project) {
		return versionRepository.getUnreleasedVersions(project);
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
	
	@SuppressWarnings("unused")
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