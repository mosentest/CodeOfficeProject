package mu.codeoffice.service;

import static mu.codeoffice.query.CaseSpecifications.all;
import static mu.codeoffice.query.CaseSpecifications.closed;
import static mu.codeoffice.query.CaseSpecifications.inProgress;
import static mu.codeoffice.query.CaseSpecifications.pageSpecification;
import static mu.codeoffice.query.CaseSpecifications.resolved;
import static mu.codeoffice.query.CaseSpecifications.sort;
import static mu.codeoffice.query.CaseSpecifications.unresolved;
import static mu.codeoffice.query.CaseSpecifications.assigned;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import mu.codeoffice.data.AssigneeData;
import mu.codeoffice.entity.Issue;
import mu.codeoffice.entity.EnterpriseUser;
import mu.codeoffice.enums.CasePriority;
import mu.codeoffice.enums.CaseStatus;
import mu.codeoffice.enums.CaseType;
import mu.codeoffice.repository.CaseRepository;
import mu.codeoffice.repository.RoleGroupRepository;
import mu.codeoffice.security.EnterpriseAuthentication;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CaseService {

	@Resource
	private CaseRepository caseRepository;
	
	@Resource 
	private RoleGroupRepository roleGroupRepository;
	
	public List<AssigneeData> getAssigneeStatus(Long project) {
		List<AssigneeData> assigneeStatus = new ArrayList<>();
		List<EnterpriseUser> users = roleGroupRepository.getUsers(project);
		for (EnterpriseUser user : users) {
			int total = (int) caseRepository.count(all(null, project, null, null, null, null, user.getId(), null, null, null, null));
			if (total == 0) {
				continue;
			}
			AssigneeData assigneeData = new AssigneeData(user);
			assigneeData.setData((int) caseRepository.count(inProgress(project, user.getId(), null)), 
					(int) caseRepository.count(closed(project, user.getId(), null)), 
					(int) caseRepository.count(resolved(null, project, null, null, null, null, user.getId(), null, null, null)), total);
			assigneeStatus.add(assigneeData);
		}
		return assigneeStatus;
	}
	
	@Transactional(readOnly = true)
	public Page<Issue> getProjectCaseStream(EnterpriseAuthentication auth, Long project, int pageIndex,  int size) {
		Page<Issue> page = caseRepository.findAll(unresolved(null, project, null, null, null, null, null, null, null, null, null), 
				pageSpecification(pageIndex, size, sort(false, Issue.getSortColumn("code"))));
		return page;
	}

	@Transactional(readOnly = true)
	public Page<Issue> getCaseInProgress(EnterpriseAuthentication auth, Long project, int pageIndex, int size) {
		Page<Issue> page = caseRepository.findAll(inProgress(project, auth.getEnterpriseUser().getId(), null), 
				pageSpecification(pageIndex, size, sort(false, Issue.getSortColumn("id"))));
		return page;
	}

	@Transactional(readOnly = true)
	public Page<Issue> getAssignedCase(EnterpriseAuthentication auth, Long project, int pageIndex, int size) {
		Page<Issue> page = caseRepository.findAll(assigned(project, auth.getEnterpriseUser().getId(), null), 
				pageSpecification(pageIndex, size, sort(false, Issue.getSortColumn("id"))));
		return page;
	}
	
	@Transactional(readOnly = true)
	public Page<Issue> getCasePage(EnterpriseAuthentication auth, Long project, Long version, Long releaseVersion, Long component, Long label, 
			Long assignee, Long reporter, CaseStatus status, CaseType type, CasePriority priority,
			int pageIndex, int size, String column, boolean ascending) {
		Page<Issue> page = caseRepository.findAll(all(null, project, version, releaseVersion, component, label, assignee, reporter, status, type, priority), 
				pageSpecification(pageIndex, size, sort(ascending, Issue.getSortColumn(column))));
		return page;
	}
	
}
