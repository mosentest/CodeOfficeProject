package mu.codeoffice.service;

import java.util.List;

import javax.annotation.Resource;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.settings.WorkFlow;
import mu.codeoffice.repository.settings.WorkFlowRepository;
import mu.codeoffice.repository.settings.WorkFlowTransitionRepository;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkFlowService {

	@Resource
	private WorkFlowRepository workFlowRepository;

	@Resource
	private WorkFlowTransitionRepository workFlowTransitionRepository;
	
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public void delete(EnterpriseAuthentication auth, String name) throws AuthenticationException, InformationException {
		WorkFlow workFlow = workFlowRepository.getWorkFlow(auth.getEnterprise(), name);
		if (workFlow == null) {
			throw new EnterpriseAuthenticationException("Access denied.");
		}
		if (workFlow.getProjects().size() > 0) {
			throw new InformationException("Can not delete, several projects are using this workflow.");
		}
		workFlow.getIssueStatus().clear();
		workFlowRepository.save(workFlow);
		workFlowRepository.delete(workFlow);
	}
	
	@Transactional(readOnly = true)
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public List<WorkFlow> getWorkFlows(EnterpriseAuthentication auth) {
		List<WorkFlow> workFlows = workFlowRepository.getWorkFlows(auth.getEnterprise());
		for (WorkFlow workFlow : workFlows) {
			workFlow.getCreator().getId();
			workFlow.getIssueStatus().size();
			workFlow.getProjects().size();
			workFlow.getTransitions().size();
		}
		return workFlows;
	}
	
	@Transactional(readOnly = true)
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public WorkFlow getWorkFlow(EnterpriseAuthentication auth, String name) {
		WorkFlow workFlow = workFlowRepository.getWorkFlow(auth.getEnterprise(), name);
		workFlow.getIssueStatus().size();
		workFlow.getProjects().size();
		workFlow.getTransitions().size();
		return workFlow;
	}
	
}
