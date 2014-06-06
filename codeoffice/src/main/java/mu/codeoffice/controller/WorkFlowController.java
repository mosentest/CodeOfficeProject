package mu.codeoffice.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.settings.IssueStatus;
import mu.codeoffice.entity.settings.WorkFlow;
import mu.codeoffice.entity.settings.WorkFlowTransition;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.ProjectPermission;
import mu.codeoffice.service.IssuePropertyConfigurationService;
import mu.codeoffice.service.WorkFlowService;
import mu.codeoffice.tag.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	@RequestMapping(value = "administration/workFlow/create", method = RequestMethod.POST)
	public String create(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@ModelAttribute("workFlow") @Valid WorkFlow workFlow, BindingResult result, 
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("formErrors", initErrorMessages(result.getAllErrors(), messageSource));
		} else {
			try {
				workFlowService.create(auth, workFlow);
				redirectAttributes.addFlashAttribute(TIP, "Work Flow has been created.");
				return "redirect:/administration/workFlow.html?workFlow=" + workFlow.getName();
			} catch (InformationException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
			}
		}
		return "redirect:/administration/workFlows.html";
	}

	@RequestMapping(value = "administration/workFlow/edit.html", method = RequestMethod.GET)
	public ModelAndView edit(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("workFlow") String workFlow, RedirectAttributes redirectAttributes, ModelMap model) {
		workFlow = Function.unmaskURL(workFlow);
		WorkFlow flow = workFlowService.getWorkFlow(auth, workFlow);
		if (flow == null) {
			redirectAttributes.addFlashAttribute(ERROR, "Work Flow doesn't exist.");
			return new ModelAndView("redirect:/administration/workFlows.html");
		}
		model.put("workFlow", flow);
		model.put("issueStatus", issuePropertyConfigurationService.getIssueStatus(auth));
		return new ModelAndView("administration/project_workflow_edit", model);
	}

	@RequestMapping(value = "administration/workFlow/edit", method = RequestMethod.POST)
	public String edit(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@ModelAttribute("workFlow") @Valid WorkFlow workFlow, BindingResult result, 
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("formErrors", initErrorMessages(result.getAllErrors(), messageSource));
		} else {
			try {
				workFlowService.edit(auth, workFlow);
				redirectAttributes.addFlashAttribute(TIP, "Work Flow has been updated.");
			} catch (InformationException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
			}
		}
		return "redirect:/administration/workFlows.html";
	}

	@RequestMapping(value = "administration/workFlow/clone", method = RequestMethod.GET)
	public String clone(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("workFlow") String workFlow, RedirectAttributes redirectAttributes) {
		workFlow = Function.unmaskURL(workFlow);
		try {
			workFlowService.clone(auth, workFlow);
			redirectAttributes.addFlashAttribute(TIP, "Work Flow has been cloned.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/workFlows.html";
	}

	@RequestMapping(value = "administration/workFlow/delete", method = RequestMethod.POST)
	public String delete(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("workFlow") String workFlow, RedirectAttributes redirectAttributes) {
		workFlow = Function.unmaskURL(workFlow);
		try {
			workFlowService.delete(auth, workFlow);
			redirectAttributes.addFlashAttribute(TIP, "Work Flow has been deleted.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/workFlows.html";
	}

	@RequestMapping(value = "administration/workFlowTransition/create", method = RequestMethod.POST)
	public String addTransition(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("workFlow") String workFlow, 
			@ModelAttribute("workFlowTransition") @Valid WorkFlowTransition workFlowTransition, BindingResult result, 
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("formErrors", initErrorMessages(result.getAllErrors(), messageSource));
		} else {
			try {
				workFlowService.addTransition(auth, workFlowTransition);
				redirectAttributes.addFlashAttribute(TIP, "Work Flow Transition has been created.");
				return "redirect:/administration/workFlow.html?workFlow=" + workFlowTransition.getWorkFlow().getName();
			} catch (InformationException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
			}
		}
		return "redirect:/administration/workFlow.html?workFlow=" + workFlow;
	}

	@RequestMapping(value = "administration/workFlowTransition/delete", method = RequestMethod.POST)
	public String deleteTransition(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("workFlow") String workFlow,
			@RequestParam("transition") Long transition, RedirectAttributes redirectAttributes) {
		workFlow = Function.unmaskURL(workFlow);
		try {
			workFlowService.deleteTransition(auth, workFlow, transition);
			redirectAttributes.addFlashAttribute(TIP, "Work Flow Transition has been deleted.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/workFlow.html?workFlow=" + workFlow;
	}

	@RequestMapping(value = "administration/workFlow.html", method = RequestMethod.GET)
	public ModelAndView get(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam("workflow") String workflow, RedirectAttributes redirectAttributes, ModelMap model) {
		workflow = Function.unmaskURL(workflow);
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
	public ModelAndView get(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("workFlows", workFlowService.getWorkFlows(auth, true));
		return new ModelAndView("administration/project_workflows", model);
	}
	
}
