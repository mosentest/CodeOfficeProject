package mu.codeoffice.service;

import static mu.codeoffice.query.IssueSpecifications.all;
import static mu.codeoffice.query.IssueSpecifications.assigned;
import static mu.codeoffice.query.IssueSpecifications.closed;
import static mu.codeoffice.query.IssueSpecifications.inProgress;
import static mu.codeoffice.query.IssueSpecifications.resolved;
import static mu.codeoffice.query.IssueSpecifications.unresolved;
import static mu.codeoffice.query.GenericSpecifications.pageSpecification;
import static mu.codeoffice.query.GenericSpecifications.sort;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import mu.codeoffice.data.AssigneeData;
import mu.codeoffice.entity.User;
import mu.codeoffice.entity.Issue;
import mu.codeoffice.enums.IssuePriority;
import mu.codeoffice.enums.IssueStatus;
import mu.codeoffice.enums.IssueType;
import mu.codeoffice.repository.IssueRepository;
import mu.codeoffice.security.EnterpriseAuthentication;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IssueService {

	@Resource
	private IssueRepository caseRepository;
	
	public List<AssigneeData> getAssigneeStatus(Long project) {
		List<AssigneeData> assigneeStatus = new ArrayList<>();
		List<User> users = new ArrayList<>();
		for (User user : users) {
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
		Page<Issue> page = caseRepository.findAll(inProgress(project, auth.getUser().getId(), null), 
				pageSpecification(pageIndex, size, sort(false, Issue.getSortColumn("id"))));
		return page;
	}

	@Transactional(readOnly = true)
	public Page<Issue> getAssignedCase(EnterpriseAuthentication auth, Long project, int pageIndex, int size) {
		Page<Issue> page = caseRepository.findAll(assigned(project, auth.getUser().getId(), null), 
				pageSpecification(pageIndex, size, sort(false, Issue.getSortColumn("id"))));
		return page;
	}
	
	@Transactional(readOnly = true)
	public Page<Issue> getCasePage(EnterpriseAuthentication auth, Long project, Long version, Long releaseVersion, Long component, Long label, 
			Long assignee, Long reporter, IssueStatus status, IssueType type, IssuePriority priority,
			int pageIndex, int size, String column, boolean ascending) {
		Page<Issue> page = caseRepository.findAll(all(null, project, version, releaseVersion, component, label, assignee, reporter, status, type, priority), 
				pageSpecification(pageIndex, size, sort(ascending, Issue.getSortColumn(column))));
		return page;
	}
	
}
