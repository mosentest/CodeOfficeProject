package mu.codeoffice.service;

import static mu.codeoffice.query.IssueSpecifications.all;
import static mu.codeoffice.query.IssueSpecifications.resolved;
import static mu.codeoffice.query.IssueSpecifications.unresolved;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mu.codeoffice.data.Summary;
import mu.codeoffice.entity.Component;
import mu.codeoffice.entity.User;
import mu.codeoffice.entity.Label;
import mu.codeoffice.entity.Version;
import mu.codeoffice.enums.IssuePriority;
import mu.codeoffice.enums.IssueStatus;
import mu.codeoffice.enums.IssueType;
import mu.codeoffice.repository.ComponentRepository;
import mu.codeoffice.repository.IssueRepository;
import mu.codeoffice.repository.LabelRepository;
import mu.codeoffice.repository.VersionRepository;
import mu.codeoffice.utility.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class ProjectStatisticService {

	@Autowired
	protected VersionRepository versionRepository;
	
	@Autowired
	protected IssueRepository caseRepository;
	
	@Resource
	protected ComponentRepository componentRepository;
	
	@Resource
	protected LabelRepository labelRepository;

	protected List<Summary> getMonthlySummary(Long project, Long version, Long releaseVersion, Long component, Long label, 
			Long assignee, Long reporter, IssueStatus status, IssueType type, IssuePriority priority) {
		List<Summary> summary = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -29);
		for (int i = 0; i < 30; i++) {
			Date date = calendar.getTime();
			int noCount = (int) caseRepository.count(all(date, project, version, releaseVersion, component, label, assignee, reporter, status, type, priority));
			int noResolved = (int) caseRepository.count(unresolved(date, project, version, releaseVersion, component, label, assignee, reporter, status, type, priority));
			Summary s = new Summary(date, noCount, noResolved);
			setVersionMark(versionRepository.getProjectVersions(project), s, date);
			summary.add(s);
			calendar.add(Calendar.DATE, 1);
		}
		return summary;
	}
	
	protected List<Summary> getWeeklySummary(Long project, Long version, Long releaseVersion, Long component, Long label, 
			Long assignee, Long reporter, IssueStatus status, IssueType type, IssuePriority priority) {
		List<Summary> summary = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -6);
		for (int i = 0; i < 7; i++) {
			Date date = calendar.getTime();
			int noCount = (int) caseRepository.count(all(date, project, version, releaseVersion, component, label, assignee, reporter, status, type, priority));
			int noResolved = (int) caseRepository.count(unresolved(date, project, version, releaseVersion, component, label, assignee, reporter, status, type, priority));
			Summary s = new Summary(date, noCount, noResolved);
			setVersionMark(versionRepository.getProjectVersions(project), s, date);
			summary.add(s);
			calendar.add(Calendar.DATE, 1);
		}
		return summary;
	}

	public Map<Version, Integer> getVersionSummary(Iterable<Version> versions, Long project, Long releaseVersion, Long component, Long label, 
			Long assignee, Long reporter, IssueStatus status, IssueType type, IssuePriority priority) {
		Map<Version, Integer> map = new LinkedHashMap<>();
		versions.forEach(v -> map.put(v, (int) caseRepository.count(
				all(null, project, v.getId(), releaseVersion, component, label, assignee, reporter, status, type, priority))));
		clearZeroValues(map);
		return map;
	}

	public Map<Version, Integer> getRoadMap(Iterable<Version> versions, Long project, Long version, Long component, Long label, 
			Long assignee, Long reporter, IssueType type, IssuePriority priority) {
		Map<Version, Integer> map = new LinkedHashMap<>();
		versions.forEach(v -> map.put(v, (int) caseRepository.count(
				resolved(null, project, version, v.getId(), component, label, assignee, reporter, type, priority))));
		clearZeroValues(map);
		return map;
	}

	public Map<Component, Integer> getComponentSummary(Iterable<Component> components, Long project, Long version, Long releaseVersion, Long label, 
			Long assignee, Long reporter, IssueStatus status, IssueType type, IssuePriority priority) {
		Map<Component, Integer> map = new LinkedHashMap<>();
		components.forEach(c -> map.put(c, (int) caseRepository.count(
				all(null, project, version, releaseVersion, c.getId(), label, assignee, reporter, status, type, priority))));
		clearZeroValues(map);
		return map;
	}

	public Map<Label, Integer> getLabelSummary(Iterable<Label> labels, Long project, Long version, Long releaseVersion, Long component, 
			Long assignee, Long reporter, IssueStatus status, IssueType type, IssuePriority priority) {
		Map<Label, Integer> map = new LinkedHashMap<>();
		labels.forEach(l -> map.put(l, (int) caseRepository.count(
				all(null, project, version, releaseVersion, component, l.getId(), assignee, reporter, status, type, priority))));
		clearZeroValues(map);
		return map;
	}

	public Map<IssueType, Integer> getIssueTypeSummary(Long project, Long version, Long releaseVersion, Long component, Long label, 
			Long assignee, Long reporter, IssueStatus status, IssuePriority priority) {
		Map<IssueType, Integer> map = new LinkedHashMap<>();
		for (IssueType type : IssueType.values()) {
			map.put(type, (int) caseRepository.count(all(null, project, version, releaseVersion, component, label, assignee, reporter, status, type, priority)));
		}
		clearZeroValues(map);
		return map;
	}

	public Map<IssueStatus, Integer> getIssueStatusSummary(Long project, Long version, Long releaseVersion, Long component, Long label, 
			Long assignee, Long reporter, IssueType type, IssuePriority priority) {
		Map<IssueStatus, Integer> map = new LinkedHashMap<>();
		for (IssueStatus status : IssueStatus.values()) {
			map.put(status, (int) caseRepository.count(all(null, project, version, releaseVersion, component, label, assignee, reporter, status, type, priority)));
		}
		clearZeroValues(map);
		return map;
	}

	public Map<IssuePriority, Integer> getIssuePrioritySummary(Long project, Long version, Long releaseVersion, Long component, Long label, 
			Long assignee, Long reporter, IssueStatus status, IssueType type) {
		Map<IssuePriority, Integer> map = new LinkedHashMap<>();
		for (IssuePriority priority : IssuePriority.values()) {
			map.put(priority, (int) caseRepository.count(all(null, project, version, releaseVersion, component, label, assignee, reporter, status, type, priority)));
		}
		clearZeroValues(map);
		return map;
	}

	public Map<User, Integer> getAssigneeSummary(Iterable<User> users, Long project, Long version, Long releaseVersion, Long component, Long label, 
			Long reporter, IssueStatus status, IssueType type, IssuePriority priority) {
		Map<User, Integer> map = new LinkedHashMap<>();
		users.forEach(u -> map.put(u, (int) caseRepository.count(
				all(null, project, version, releaseVersion, component, label, u.getId(), reporter, status, type, priority))));
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
