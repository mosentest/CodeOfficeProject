package mu.codeoffice.service;

import static mu.codeoffice.query.CaseSpecifications.all;
import static mu.codeoffice.query.CaseSpecifications.unresolved;
import static mu.codeoffice.query.CaseSpecifications.resolved;

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
import mu.codeoffice.entity.EnterpriseUser;
import mu.codeoffice.entity.Label;
import mu.codeoffice.entity.Version;
import mu.codeoffice.enums.CasePriority;
import mu.codeoffice.enums.CaseStatus;
import mu.codeoffice.enums.CaseType;
import mu.codeoffice.repository.CaseRepository;
import mu.codeoffice.repository.ComponentRepository;
import mu.codeoffice.repository.LabelRepository;
import mu.codeoffice.repository.RoleGroupRepository;
import mu.codeoffice.repository.VersionRepository;
import mu.codeoffice.utility.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class VersionStatisticService {

	@Autowired
	protected VersionRepository versionRepository;
	
	@Autowired
	protected CaseRepository caseRepository;
	
	@Resource
	protected RoleGroupRepository roleGroupRepository;
	
	@Resource
	protected ComponentRepository componentRepository;
	
	@Resource
	protected LabelRepository labelRepository;

	protected List<Summary> getMonthlySummary(Long project, Long version, Long releaseVersion, Long component, Long label, 
			Long assignee, Long reporter, CaseStatus status, CaseType type, CasePriority priority) {
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
			Long assignee, Long reporter, CaseStatus status, CaseType type, CasePriority priority) {
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
			Long assignee, Long reporter, CaseStatus status, CaseType type, CasePriority priority) {
		Map<Version, Integer> map = new LinkedHashMap<>();
		versions.forEach(v -> map.put(v, (int) caseRepository.count(
				all(null, project, v.getId(), releaseVersion, component, label, assignee, reporter, status, type, priority))));
		clearZeroValues(map);
		return map;
	}

	public Map<Version, Integer> getRoadMap(Iterable<Version> versions, Long project, Long version, Long component, Long label, 
			Long assignee, Long reporter, CaseType type, CasePriority priority) {
		Map<Version, Integer> map = new LinkedHashMap<>();
		versions.forEach(v -> map.put(v, (int) caseRepository.count(
				resolved(null, project, version, v.getId(), component, label, assignee, reporter, type, priority))));
		clearZeroValues(map);
		return map;
	}

	public Map<Component, Integer> getComponentSummary(Iterable<Component> components, Long project, Long version, Long releaseVersion, Long label, 
			Long assignee, Long reporter, CaseStatus status, CaseType type, CasePriority priority) {
		Map<Component, Integer> map = new LinkedHashMap<>();
		components.forEach(c -> map.put(c, (int) caseRepository.count(
				all(null, project, version, releaseVersion, c.getId(), label, assignee, reporter, status, type, priority))));
		clearZeroValues(map);
		return map;
	}

	public Map<Label, Integer> getLabelSummary(Iterable<Label> labels, Long project, Long version, Long releaseVersion, Long component, 
			Long assignee, Long reporter, CaseStatus status, CaseType type, CasePriority priority) {
		Map<Label, Integer> map = new LinkedHashMap<>();
		labels.forEach(l -> map.put(l, (int) caseRepository.count(
				all(null, project, version, releaseVersion, component, l.getId(), assignee, reporter, status, type, priority))));
		clearZeroValues(map);
		return map;
	}

	public Map<CaseStatus, Integer> getCaseStatusSummary(Long project, Long version, Long releaseVersion, Long component, Long label, 
			Long assignee, Long reporter, CaseType type, CasePriority priority) {
		Map<CaseStatus, Integer> map = new LinkedHashMap<>();
		for (CaseStatus status : CaseStatus.values()) {
			map.put(status, (int) caseRepository.count(all(null, project, version, releaseVersion, component, label, assignee, reporter, status, type, priority)));
		}
		clearZeroValues(map);
		return map;
	}

	public Map<CasePriority, Integer> getCasePrioritySummary(Long project, Long version, Long releaseVersion, Long component, Long label, 
			Long assignee, Long reporter, CaseStatus status, CaseType type) {
		Map<CasePriority, Integer> map = new LinkedHashMap<>();
		for (CasePriority priority : CasePriority.values()) {
			map.put(priority, (int) caseRepository.count(all(null, project, version, releaseVersion, component, label, assignee, reporter, status, type, priority)));
		}
		clearZeroValues(map);
		return map;
	}

	public Map<EnterpriseUser, Integer> getAssigneeSummary(Iterable<EnterpriseUser> users, Long project, Long version, Long releaseVersion, Long component, Long label, 
			Long reporter, CaseStatus status, CaseType type, CasePriority priority) {
		Map<EnterpriseUser, Integer> map = new LinkedHashMap<>();
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
