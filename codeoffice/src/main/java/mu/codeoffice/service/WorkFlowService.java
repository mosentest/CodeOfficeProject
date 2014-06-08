package mu.codeoffice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.settings.IssueStatus;
import mu.codeoffice.entity.settings.WorkFlow;
import mu.codeoffice.entity.settings.WorkFlowTransition;
import mu.codeoffice.repository.settings.IssueStatusRepository;
import mu.codeoffice.repository.settings.WorkFlowRepository;
import mu.codeoffice.repository.settings.WorkFlowTransitionRepository;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.ProjectPermission;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkFlowService {

	@Resource
	private WorkFlowRepository workFlowRepository;

	@Resource
	private WorkFlowTransitionRepository workFlowTransitionRepository;
	
	@Resource
	private IssueStatusRepository issueStatusRepository;

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public void create(EnterpriseAuthentication auth, WorkFlow workFlow) throws InformationException {
		if (!workFlowRepository.isNameAvailable(auth.getEnterprise(), workFlow.getName(), 0l)) {
			throw new InformationException("Work Flow Name is not available.");
		}
		IssueStatus defaultStatus = issueStatusRepository.getIssueStatus(auth.getEnterprise(), workFlow.getDefaultStatus().getId());
		if (defaultStatus == null) {
			throw new InformationException("Default Status can not be null");
		}
		IssueStatus resolvedStatus = issueStatusRepository.getIssueStatus(auth.getEnterprise(), workFlow.getResolvedStatus().getId());
		if (resolvedStatus == null) {
			throw new InformationException("Resolved Status can not be null");
		}
		IssueStatus closedStatus = issueStatusRepository.getIssueStatus(auth.getEnterprise(), workFlow.getClosedStatus().getId());
		if (closedStatus == null) {
			throw new InformationException("Closed Status can not be null");
		}
		if (defaultStatus.equals(resolvedStatus) || resolvedStatus.equals(closedStatus) || closedStatus.equals(defaultStatus)) {
			throw new InformationException("Status must be different.");
		}
		
		workFlow.setId(null);
		workFlow.setEnterprise(auth.getEnterprise());
		workFlow.setCreator(auth.getUser());
		workFlow.setModified(new Date());
		workFlow.setSteps(0);
		workFlow.setProjects(null);
		workFlow.setTransitions(null);
		workFlow.setDefaultStatus(defaultStatus);
		workFlow.setResolvedStatus(resolvedStatus);
		workFlow.setClosedStatus(closedStatus);
		workFlow.setIssueStatus(Arrays.asList(defaultStatus, resolvedStatus, closedStatus));
		workFlowRepository.save(workFlow);
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public void edit(EnterpriseAuthentication auth, WorkFlow workFlow) throws InformationException {
		WorkFlow original = workFlowRepository.getWorkFlow(auth.getEnterprise(), workFlow.getId());
		if (original == null) {
			throw new InformationException("Work Flow doesn't exist.");
		}
		if (!workFlowRepository.isNameAvailable(auth.getEnterprise(), workFlow.getName(), original.getId())) {
			throw new InformationException("Work Flow Name is not available.");
		}
		IssueStatus defaultStatus = issueStatusRepository.getIssueStatus(auth.getEnterprise(), workFlow.getDefaultStatus().getId());
		if (defaultStatus == null) {
			throw new InformationException("Default Status can not be null");
		}
		IssueStatus resolvedStatus = issueStatusRepository.getIssueStatus(auth.getEnterprise(), workFlow.getResolvedStatus().getId());
		if (resolvedStatus == null) {
			throw new InformationException("Resolved Status can not be null");
		}
		IssueStatus closedStatus = issueStatusRepository.getIssueStatus(auth.getEnterprise(), workFlow.getClosedStatus().getId());
		if (closedStatus == null) {
			throw new InformationException("Closed Status can not be null");
		}
		if (defaultStatus.equals(resolvedStatus) || resolvedStatus.equals(closedStatus) || closedStatus.equals(defaultStatus)) {
			throw new InformationException("Status must be different.");
		}		
		
		original.setModified(new Date());
		if (!workFlowTransitionRepository.isStatusInvolved(auth.getEnterprise(), original.getId(), 0l, original.getDefaultStatus())) {
			original.getIssueStatus().remove(original.getDefaultStatus());
		}
		if (!workFlowTransitionRepository.isStatusInvolved(auth.getEnterprise(), original.getId(), 0l, original.getResolvedStatus())) {
			original.getIssueStatus().remove(original.getResolvedStatus());
		}
		if (!workFlowTransitionRepository.isStatusInvolved(auth.getEnterprise(), original.getId(), 0l, original.getClosedStatus())) {
			original.getIssueStatus().remove(original.getClosedStatus());
		}
		original.setName(workFlow.getName());
		original.setDescription(workFlow.getDescription());
		original.setDefaultStatus(defaultStatus);
		original.setResolvedStatus(resolvedStatus);
		original.setClosedStatus(closedStatus);
		original.setModified(new Date());
		if (!original.getIssueStatus().contains(defaultStatus)) {
			original.getIssueStatus().add(defaultStatus);
		}
		if (!original.getIssueStatus().contains(resolvedStatus)) {
			original.getIssueStatus().add(resolvedStatus);
		}
		if (!original.getIssueStatus().contains(closedStatus)) {
			original.getIssueStatus().add(closedStatus);
		}
		//TODO validate, count steps
		workFlowRepository.save(original);
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public void clone(EnterpriseAuthentication auth, String name) throws InformationException {
		WorkFlow original = workFlowRepository.getWorkFlow(auth.getEnterprise(), name);
		if (original == null) {
			throw new InformationException("Work Flow doens't exist.");
		}
		if (!workFlowRepository.isNameAvailable(auth.getEnterprise(), "CLONE - " + name, 0l)) {
			throw new InformationException("Work Flow Name is not available.");
		}
		WorkFlow workFlow = new WorkFlow();
		workFlow.setCreator(auth.getUser());
		workFlow.setName("CLONE - " + name);
		workFlow.setDescription(original.getDescription());
		workFlow.setEnterprise(auth.getEnterprise());
		workFlow.setDefaultStatus(original.getDefaultStatus());
		workFlow.setResolvedStatus(original.getResolvedStatus());
		workFlow.setClosedStatus(original.getClosedStatus());
		workFlow.setModified(new Date());
		workFlow.setSteps(original.getSteps());
		workFlow.setValid(original.isValid());
		workFlow.setIssueStatus(new ArrayList<>());
		for (IssueStatus status : original.getIssueStatus()) {
			workFlow.getIssueStatus().add(issueStatusRepository.getIssueStatus(auth.getEnterprise(), status.getId()));
		}
		workFlowRepository.save(workFlow);
		for (WorkFlowTransition transition : original.getTransitions()) {
			WorkFlowTransition workFlowTransition = new WorkFlowTransition();
			workFlowTransition.setEnterprise(auth.getEnterprise());
			workFlowTransition.setRequiredPermissionValue(transition.getRequiredPermissionValue());
			workFlowTransition.setFrom(transition.getFrom());
			workFlowTransition.setTo(transition.getTo());
			workFlowTransition.setWorkFlow(workFlow);
			workFlowTransition.setTransition(workFlowTransition.getTransition());
			workFlowTransitionRepository.save(workFlowTransition);
		}
	}
	
	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public void delete(EnterpriseAuthentication auth, String name) throws InformationException {
		WorkFlow workFlow = workFlowRepository.getWorkFlow(auth.getEnterprise(), name);
		if (workFlow == null) {
			throw new InformationException("Work Flow doens't exist.");
		}
		if (workFlowRepository.isInUse(auth.getEnterprise(), workFlow.getId())) {
			throw new InformationException("Can not delete, several projects are using this workflow.");
		}
		workFlow.getIssueStatus().clear();
		workFlowRepository.save(workFlow);
		workFlowRepository.delete(workFlow);
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public void create(EnterpriseAuthentication auth, WorkFlowTransition workFlowTransition) throws InformationException {
		WorkFlow workFlow = workFlowRepository.getWorkFlow(auth.getEnterprise(), workFlowTransition.getWorkFlow().getId());
		if (workFlow == null) {
			throw new InformationException("Work Flow doens't exist.");
		}
		IssueStatus from = issueStatusRepository.getIssueStatus(auth.getEnterprise(), workFlowTransition.getFrom().getId());
		if (from == null) {
			throw new InformationException("From Status can not be null");
		}
		IssueStatus to = issueStatusRepository.getIssueStatus(auth.getEnterprise(), workFlowTransition.getTo().getId());
		if (to == null) {
			throw new InformationException("To Status can not be null");
		}
		if (from.equals(to)) {
			throw new InformationException("Transition can not be same.");
		}
		if (!workFlowTransitionRepository.isTransitionAvailable(auth.getEnterprise(), workFlow.getId(), workFlowTransition.getTransition(), from, to)) {
			throw new InformationException("There is a similar transition for work flow.");
		}
		workFlowTransition.setId(null);
		workFlowTransition.setFrom(from);
		workFlowTransition.setTo(to);
		workFlowTransition.setEnterprise(auth.getEnterprise());
		workFlowTransition.setWorkFlow(workFlow);
		if (workFlowTransition.getRequiredPermissions() != null) {
			for (ProjectPermission permission : workFlowTransition.getRequiredPermissions()) {
				if (ProjectPermission.WORKFLOW_PERMISSIONS.contains(permission)) {
					workFlowTransition.setRequiredPermissionValue(workFlowTransition.getRequiredPermissionValue() | permission.getAuthority());
				}
			}
		} else {
			for (ProjectPermission permission : ProjectPermission.WORKFLOW_PERMISSIONS) {
				workFlowTransition.setRequiredPermissionValue(workFlowTransition.getRequiredPermissionValue() | permission.getAuthority());
			}
		}
		workFlowTransitionRepository.save(workFlowTransition);
		
		workFlow.setModified(new Date());
		if (!workFlow.getIssueStatus().contains(from)) {
			workFlow.getIssueStatus().add(from);
		}
		if (!workFlow.getIssueStatus().contains(to)) {
			workFlow.getIssueStatus().add(to);
		}
		//TODO validate, count steps
		workFlowRepository.save(workFlow);
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public void delete(EnterpriseAuthentication auth, String workFlow, Long transition) throws InformationException {
		WorkFlow original = workFlowRepository.getWorkFlow(auth.getEnterprise(), workFlow);
		if (workFlow == null) {
			throw new InformationException("Work Flow doens't exist.");
		}
		WorkFlowTransition workFlowTransition = workFlowTransitionRepository.getWorkFlowTransition(auth.getEnterprise(), transition);
		if (workFlowTransition == null) {
			throw new InformationException("Work Flow Transition doens't exist.");
		}
		if (workFlowRepository.isInUse(auth.getEnterprise(), original.getId())) {
			throw new InformationException("Can not delete, several projects are using this workflow.");
		}
		//TODO validate, count steps
		original.setModified(new Date());
		if (!original.getDefaultStatus().equals(workFlowTransition.getFrom()) && 
				!original.getResolvedStatus().equals(workFlowTransition.getFrom()) &&
				!original.getClosedStatus().equals(workFlowTransition.getFrom())) {
			if (!workFlowTransitionRepository.isStatusInvolved(auth.getEnterprise(), original.getId(), workFlowTransition.getId(), workFlowTransition.getFrom())) {
				original.getIssueStatus().remove(workFlowTransition.getFrom());
			}
		}
		if (!original.getDefaultStatus().equals(workFlowTransition.getTo()) && 
				!original.getResolvedStatus().equals(workFlowTransition.getTo()) &&
				!original.getClosedStatus().equals(workFlowTransition.getTo())) {
			if (!workFlowTransitionRepository.isStatusInvolved(auth.getEnterprise(), original.getId(), workFlowTransition.getId(), workFlowTransition.getTo())) {
				original.getIssueStatus().remove(workFlowTransition.getTo());
			}
		}
		workFlowRepository.save(original);
		workFlowTransitionRepository.delete(workFlowTransition);
	}
	
	@Transactional(readOnly = true)
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public List<WorkFlow> getWorkFlows(EnterpriseAuthentication auth, boolean initialize) {
		List<WorkFlow> workFlows = workFlowRepository.getWorkFlows(auth.getEnterprise());
		if (initialize) {
			for (WorkFlow workFlow : workFlows) {
				workFlow.getCreator().getId();
				workFlow.getIssueStatus().size();
				workFlow.getProjects().size();
				workFlow.getTransitions().size();
			}
		}
		return workFlows;
	}
	
	@Transactional(readOnly = true)
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public WorkFlow getWorkFlow(EnterpriseAuthentication auth, String name) {
		WorkFlow workFlow = workFlowRepository.getWorkFlow(auth.getEnterprise(), name);
		workFlow.getIssueStatus().size();
		workFlow.getProjects().size();
		workFlow.getTransitions().size();
		for (WorkFlowTransition transition : workFlow.getTransitions()) {
			transition.setRequiredPermissions(ProjectPermission.getPermissions(transition.getRequiredPermissionValue()));
		}
		return workFlow;
	}
	
}
