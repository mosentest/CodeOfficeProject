package mu.codeoffice.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mu.codeoffice.entity.settings.IssueStatus;
import mu.codeoffice.entity.settings.WorkFlow;
import mu.codeoffice.entity.settings.WorkFlowTransition;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.service.WorkFlowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/administration/")
public class WorkFlowController {

	@Autowired
	private WorkFlowService workFlowService;

	@RequestMapping(value = "workFlow/{name}.html", method = RequestMethod.GET)
	public ModelAndView workflow(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@PathVariable("name") String name, ModelMap model) {
		WorkFlow workFlow = workFlowService.getWorkFlow(auth, name);
		model.put("workFlow", workFlow);
		Map<IssueStatus, List<WorkFlowTransition>> transitionMap = new LinkedHashMap<>();
		transitionMap.put(workFlow.getDefaultStatus(), new ArrayList<>());
		for (IssueStatus status : workFlow.getIssueStatus()) {
			transitionMap.putIfAbsent(status, new ArrayList<>());
		}
		for (WorkFlowTransition transition : workFlow.getTransitions()) {
			transitionMap.get(transition.getFrom()).add(transition);
		}
		model.put("transitionMap", transitionMap);
		return new ModelAndView("administration/project_workflow", model);
	}

	@RequestMapping(value = "workFlows.html", method = RequestMethod.GET)
	public ModelAndView workflows(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("workFlows", workFlowService.getWorkFlows(auth));
		return new ModelAndView("administration/project_workflows", model);
	}
	
}
