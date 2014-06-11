package mu.codeoffice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.dto.ComponentDTO;
import mu.codeoffice.entity.Component;
import mu.codeoffice.entity.Project;
import mu.codeoffice.json.ComponentJSON;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.service.ComponentService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/enterprise/")
public class ComponentController extends ProjectPermissionRequired {
	
	private Logger logger = Logger.getLogger(ComponentController.class);
	
	private static final int LIST_ITEMS = 15;
	
	@Autowired
	private ComponentService componentService;
	
	@RequestMapping(value = "pro_{projectCode}/m_{componentCode}/edit", method = RequestMethod.GET) 
	public ModelAndView editRequest(@PathVariable("projectCode") String projectCode, @PathVariable("componentCode") String componentCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) throws AuthenticationException {
		Component component = componentService.getProjectComponent(projectCode, componentCode, auth);
		Project project = projectService.getProjectInfo(projectCode, auth);
		model.put("project", project);
		loadUserGroups(auth, project.getId(), model);
		model.put("component", component);
		model.put("edit", true);
		return new ModelAndView("enterprise/project/component_form", model);
	}

	@RequestMapping(value = "pro_{projectCode}/m_{componentCode}/edit", method = RequestMethod.POST) 
	public ModelAndView edit(@ModelAttribute Component component, @PathVariable("projectCode") String projectCode, @PathVariable("componentCode") String componentCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session,ModelMap model) throws AuthenticationException {
		try {
			componentService.update(auth, componentCode, component, projectCode);
			return new ModelAndView("redirect:/enterprise/pro_" + projectCode + "/m_" + component.getCode());
		} catch (InformationException e) {
			Project project = projectService.getProjectInfo(projectCode, auth);
			model.put("project", project);
			loadUserGroups(auth, project.getId(), model);
			model.put("component", component);
			model.put("edit", true);
			return new ModelAndView("enterprise/project/component_form");
		}
	}

	@RequestMapping(value = "pro_{projectCode}/component/create", method = RequestMethod.GET) 
	public ModelAndView createRequest(@PathVariable("projectCode") String projectCode,
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) throws AuthenticationException {
		model.put("component", new Component());
		model.put("edit", false);
		Project project = projectService.getProjectInfo(projectCode, auth);
		model.put("project", project);
		loadUserGroups(auth, project.getId(), model);
		return new ModelAndView("enterprise/project/component_form", model);
	}

	@RequestMapping(value = "pro_{projectCode}/component/create", method = RequestMethod.POST) 
	public ModelAndView create(@ModelAttribute Component component, @PathVariable("projectCode") String projectCode,
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) throws AuthenticationException {
		try {
			componentService.create(auth, component, projectCode);
			return new ModelAndView("redirect:/enterprise/pro_" + projectCode + "/components");
		} catch (InformationException e) {
			Project project = projectService.getProjectInfo(projectCode, auth);
			model.put("project", project);
			loadUserGroups(auth, project.getId(), model);
			return new ModelAndView("enterprise/project/component_form");
		}
	}

	@RequestMapping(value = "pro_{projectCode}/m_{componentCode}/delete", method = RequestMethod.GET) 
	public ModelAndView delete(@PathVariable("projectCode") String projectCode, @PathVariable("componentCode") String componentCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, 
			HttpServletRequest request, HttpSession session, ModelMap model) throws AuthenticationException {
		try {
			componentService.delete(projectCode, componentCode, auth);
			return new ModelAndView("redirect:" + request.getHeader("Referer"));
		} catch (InformationException e) {
			return new ModelAndView("redirect:" + request.getHeader("Referer"));
		}
	}

	@RequestMapping(value = "pro_{projectCode}/m_merge", method = RequestMethod.POST) 
	public ModelAndView mergeRequest(@RequestParam(value = "targetComponent", required = false) String targetComponent, 
			@ModelAttribute("mergeComponent") ComponentDTO mergeComponent, @PathVariable("projectCode") String projectCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) throws AuthenticationException {
		if (!projectCode.equals(mergeComponent.getProject())) {
			return new ModelAndView("redirect:/enterprise/pro_" + projectCode + "/components");
		}
		List<Component> components = null;
		if (mergeComponent.getComponentCode() == null || mergeComponent.getComponentCode().length == 0) {
			components = componentService.getProjectComponents(auth, projectCode);
		} else {
			components = componentService.getComponents(auth, mergeComponent);
		}
		if (components == null || components.size() <= 1) {
			return new ModelAndView("redirect:/enterprise/pro_" + projectCode + "/components");
		}
		model.put("components", components);
		if (targetComponent != null) {
			model.put("targetComponent", componentService.getProjectComponent(projectCode, targetComponent, auth));
		} else {
			model.put("targetComponent", components.get(0));
		}
		Project project = projectService.getProjectInfo(projectCode, auth);
		model.put("project", project);
		model.put("mergeComponent", mergeComponent);
		loadUserGroups(auth, project.getId(), model);
		return new ModelAndView("enterprise/project/component_merge", model);
	}

	@RequestMapping(value = "pro_{projectCode}/m_{componentCode}/merge", method = RequestMethod.POST) 
	public ModelAndView merge(@PathVariable("projectCode") String projectCode, @PathVariable("componentCode") String componentCode, 
			@ModelAttribute("mergeComponent") ComponentDTO mergeComponent, @AuthenticationPrincipal EnterpriseAuthentication auth, 
			HttpSession session, ModelMap model) throws AuthenticationException {
		try {
			componentService.merge(auth, projectCode, componentCode, mergeComponent);
			return new ModelAndView("redirect:/enterprise/pro_" + projectCode + "/components");
		} catch (InformationException e) {
			return new ModelAndView("redirect:/enterprise/pro_" + projectCode + "/components");
		}
	}
	
	@RequestMapping(value = "pro_{projectCode}/m_{componentCode}/info", method = RequestMethod.GET)
	public @ResponseBody ComponentJSON getComponentInfo(@PathVariable("projectCode") String projectCode, @PathVariable("componentCode") String componentCode,
			@AuthenticationPrincipal EnterpriseAuthentication auth) throws AuthenticationException {
		Component component = componentService.getProjectComponent(projectCode, componentCode, auth);
		logger.debug("Ajax requesting: " + component.getId());
		return component.toJSONObject();
	}
	
	@RequestMapping(value = "pro_{projectCode}/m_{componentCode}", method = RequestMethod.GET) 
	public ModelAndView summary(@PathVariable("projectCode") String projectCode, @PathVariable("componentCode") String componentCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws AuthenticationException {
		Project project = projectService.getProjectInfo(projectCode, auth);
		Component component = componentService.getProjectComponent(project, componentCode);
		model.put("project", project);
		model.put("component", component);
		model.put("VC_MANAGER_AUTH", null);
		model.put("monthlySummary", componentService.getComponentMonthlySummary(project, component));
		model.put("mergeComponent", new ComponentDTO());
		return new ModelAndView("enterprise/project/component_summary");
	}
	
	@RequestMapping(value = "pro_{projectCode}/m_{componentCode}/summary", method = RequestMethod.GET) 
	public ModelAndView casesummary(@PathVariable("projectCode") String projectCode, @PathVariable("componentCode") String componentCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws AuthenticationException {
		Project project = projectService.getProjectInfo(projectCode, auth);
		Component component = componentService.getProjectComponent(project, componentCode);
		model.put("project", project);
		model.put("component", component);
		model.put("statusMap", componentService.getIssueStatusSummary(project, component));
		model.put("priorityMap", componentService.getCasePrioritySummary(project, component));
		model.put("assigneeMap", componentService.getAssigneeSummary(project, component));
		model.put("labelMap", componentService.getLabelSummary(project, component));
		model.put("versionMap", componentService.getVersionSummary(project, component));
		model.put("totalCase", component.getTotalIssues());
		return new ModelAndView("enterprise/project/component_casesummary");
	}
	
	@RequestMapping(value = "pro_{projectCode}/m_{componentCode}/case", method = RequestMethod.GET) 
	public ModelAndView cases(@PathVariable("projectCode") String projectCode, @PathVariable("componentCode") String componentCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws AuthenticationException {
		Project project = projectService.getProjectInfo(projectCode, auth);
		Component component = componentService.getProjectComponent(project, componentCode);
		model.put("project", project);
		model.put("component", component);
		model.put("relatedCase", componentService.getRelatedCase(project, component, 0, LIST_ITEMS, true, "code"));
		return new ModelAndView("enterprise/project/component_cases");
	}
	
	private void loadUserGroups(EnterpriseAuthentication auth, Long project, ModelMap model) {
		model.put("leadGroup", null);
		model.put("userGroup", null);
	}
	
}
