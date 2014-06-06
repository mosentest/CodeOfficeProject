package mu.codeoffice.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mu.codeoffice.entity.settings.IssueStatus;
import mu.codeoffice.entity.settings.WorkFlow;
import mu.codeoffice.entity.settings.WorkFlowTransition;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.ProjectPermission;
import mu.codeoffice.service.IssuePropertyConfigurationService;
import mu.codeoffice.service.WorkFlowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class WorkFlowController implements GenericController {

	@Autowired
	private WorkFlowService workFlowService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IssuePropertyConfigurationService issuePropertyConfigurationService;

	@RequestMapping(value = "administration/workFlow.html", method = RequestMethod.GET)
	public ModelAndView workflow(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam("workflow") String workflow, RedirectAttributes redirectAttributes, ModelMap model) {
		WorkFlow workFlow = workFlowService.getWorkFlow(auth, workflow);
		if (workFlow == null) {
			redirectAttributes.addFlashAttribute(ERROR, messageSource.getMessage("entity.workFlow.notfound", new Object[] {workflow}, LocaleContextHolder.getLocale()));
			return new ModelAndView("redirect:/administration/workFlows.html");
		}
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
		model.put("issueStatus", issuePropertyConfigurationService.getIssueStatus(auth));
		model.put("projectPermissions", ProjectPermission.values());
		model.put("workFlowTransition", new WorkFlowTransition());
		return new ModelAndView("administration/project_workflow", model);
	}

	@RequestMapping(value = "administration/workFlows.html", method = RequestMethod.GET)
	public ModelAndView workflows(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("workFlows", workFlowService.getWorkFlows(auth, true));
		return new ModelAndView("administration/project_workflows", model);
	}
	
}
