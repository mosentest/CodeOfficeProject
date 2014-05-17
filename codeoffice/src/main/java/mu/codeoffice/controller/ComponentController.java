package mu.codeoffice.controller;

import static mu.codeoffice.utility.MessageUtil.addErrorMessage;
import static mu.codeoffice.utility.MessageUtil.addNoticeMessage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.dto.ComponentDTO;
import mu.codeoffice.entity.Component;
import mu.codeoffice.entity.Project;
import mu.codeoffice.enums.ProjectPermission;
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
		authorize(auth, projectCode, ProjectPermission.VERSION_COMPONENT_MANAGE);
		Component component = componentService.getProjectComponent(projectCode, componentCode, auth);
		Project project = projectService.getProjectInfo(projectCode, auth);
		if (project == null) {
			addErrorMessage(session, "Project not found.");
			return new ModelAndView("redirect:/enterprise/category");
		}
		model.put("project", project);
		loadUserGroups(auth, project.getId(), model);
		model.put("component", component);
		model.put("edit", true);
		return new ModelAndView("enterprise/project/component_form", model);
	}

	@RequestMapping(value = "pro_{projectCode}/m_{componentCode}/edit", method = RequestMethod.POST) 
	public ModelAndView edit(@ModelAttribute Component component, @PathVariable("projectCode") String projectCode, @PathVariable("componentCode") String componentCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session,ModelMap model) throws AuthenticationException {
		authorize(auth, projectCode, ProjectPermission.VERSION_COMPONENT_MANAGE);
		try {
			componentService.update(auth, componentCode, component, projectCode);
		} catch (AuthenticationException e) {
			throw e;
		} catch (InformationException e) {
			addErrorMessage(session, e.getMessage());
			Project project = projectService.getProjectInfo(projectCode, auth);
			model.put("project", project);
			loadUserGroups(auth, project.getId(), model);
			model.put("component", component);
			model.put("edit", true);
			return new ModelAndView("enterprise/project/component_form");
		}
		addNoticeMessage(session, "Component has been updated.");
		return new ModelAndView("redirect:/enterprise/pro_" + projectCode + "/m_" + component.getCode());
	}

	@RequestMapping(value = "pro_{projectCode}/component/create", method = RequestMethod.GET) 
	public ModelAndView createRequest(@PathVariable("projectCode") String projectCode,
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) throws AuthenticationException {
		authorize(auth, projectCode, ProjectPermission.VERSION_COMPONENT_MANAGE);
		model.put("component", new Component());
		model.put("edit", false);
		Project project = projectService.getProjectInfo(projectCode, auth);
		if (project == null) {
			addErrorMessage(session, "Project not found.");
			return new ModelAndView("redirect:/enterprise/category");
		}
		model.put("project", project);
		loadUserGroups(auth, project.getId(), model);
		return new ModelAndView("enterprise/project/component_form", model);
	}

	@RequestMapping(value = "pro_{projectCode}/component/create", method = RequestMethod.POST) 
	public ModelAndView create(@ModelAttribute Component component, @PathVariable("projectCode") String projectCode,
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) throws AuthenticationException {
		authorize(auth, projectCode, ProjectPermission.VERSION_COMPONENT_MANAGE);
		try {
			componentService.create(auth, component, projectCode);
		} catch (AuthenticationException e) {
			throw e;
		} catch (InformationException e) {
			addErrorMessage(session, e.getMessage());
			Project project = projectService.getProjectInfo(projectCode, auth);
			model.put("project", project);
			loadUserGroups(auth, project.getId(), model);
			return new ModelAndView("enterprise/project/component_form");
		}
		addNoticeMessage(session, "Component has been created.");
		logger.debug("Redirecting to: " + "redirect:/enterprise/pro_" + projectCode + "/m_" + component.getCode());
		return new ModelAndView("redirect:/enterprise/pro_" + projectCode + "/components");
	}

	@RequestMapping(value = "pro_{projectCode}/m_{componentCode}/delete", method = RequestMethod.GET) 
	public ModelAndView delete(@PathVariable("projectCode") String projectCode, @PathVariable("componentCode") String componentCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, 
			HttpServletRequest request, HttpSession session, ModelMap model) throws AuthenticationException {
		authorize(auth, projectCode, ProjectPermission.VERSION_COMPONENT_MANAGE);
		try {
			componentService.delete(projectCode, componentCode, auth);
		} catch (AuthenticationException e) {
			throw e;
		} catch (InformationException e) {
			addErrorMessage(session, e.getMessage());
			return new ModelAndView("redirect:" + request.getHeader("Referer"));
		}
		addNoticeMessage(session, "Component has been deleted");
		return new ModelAndView("redirect:" + request.getHeader("Referer"));
	}

	@RequestMapping(value = "pro_{projectCode}/m_merge", method = RequestMethod.POST) 
	public ModelAndView mergeRequest(@RequestParam(value = "targetComponent", required = false) String targetComponent, 
			@ModelAttribute("mergeComponent") ComponentDTO<Component> mergeComponent, @PathVariable("projectCode") String projectCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) throws AuthenticationException {
		authorize(auth, projectCode, ProjectPermission.VERSION_COMPONENT_MANAGE);
		if (!projectCode.equals(mergeComponent.getProject())) {
			addErrorMessage(session, "Project not available");
			return new ModelAndView("redirect:/enterprise/pro_" + projectCode + "/components");
		}
		List<Component> components = null;
		if (mergeComponent.getComponentCode() == null || mergeComponent.getComponentCode().length == 0) {
			components = componentService.getProjectComponents(auth, projectCode);
		} else {
			components = componentService.getComponents(auth, mergeComponent);
		}
		if (components == null || components.size() == 0) {
			addErrorMessage(session, "No components available to merge.");
			return new ModelAndView("redirect:/enterprise/pro_" + projectCode + "/components");
		}
		model.put("components", components);
		if (targetComponent != null) {
			model.put("targetComponent", componentService.getProjectComponent(projectCode, targetComponent, auth));
		}
		Project project = projectService.getProjectInfo(projectCode, auth);
		model.put("project", project);
		model.put("mergeComponent", mergeComponent);
		return new ModelAndView("enterprise/project/component_merge", model);
	}

	@RequestMapping(value = "pro_{projectCode}/m_{componentCode}/merge", method = RequestMethod.POST) 
	public ModelAndView merge(@PathVariable("projectCode") String projectCode, @PathVariable("componentCode") String componentCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) throws AuthenticationException {
		authorize(auth, projectCode, ProjectPermission.VERSION_COMPONENT_MANAGE);
		addNoticeMessage(session, "Components: has been merged.");
		return new ModelAndView("redirect:/enterprise/pro_" + projectCode + "/components");
	}
	
	@RequestMapping(value = "pro_{projectCode}/m_{componentCode}/info", method = RequestMethod.GET)
	public @ResponseBody Component getComponentInfo(@PathVariable("projectCode") String projectCode, @PathVariable("componentCode") String componentCode,
			@AuthenticationPrincipal EnterpriseAuthentication auth) throws AuthenticationException {
		authorize(auth, projectCode, ProjectPermission.VERSION_COMPONENT);
		Component component = componentService.getProjectComponent(projectCode, componentCode, auth);
		return component;
	}
	
	@RequestMapping(value = "pro_{projectCode}/m_{componentCode}", method = RequestMethod.GET) 
	public ModelAndView summary(@PathVariable("projectCode") String projectCode, @PathVariable("componentCode") String componentCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws AuthenticationException {
		authorize(auth, projectCode, ProjectPermission.VERSION_COMPONENT);
		Project project = projectService.getProjectInfo(projectCode, auth);
		Component component = componentService.getProjectComponent(project, componentCode);
		model.put("project", project);
		model.put("component", component);
		model.put("VC_MANAGER_AUTH", hasAuthority(auth, projectCode, ProjectPermission.VERSION_COMPONENT_MANAGE));
		model.put("monthlySummary", componentService.getComponentMonthlySummary(project, component));
		return new ModelAndView("enterprise/project/component_summary");
	}
	
	@RequestMapping(value = "pro_{projectCode}/m_{componentCode}/summary", method = RequestMethod.GET) 
	public ModelAndView casesummary(@PathVariable("projectCode") String projectCode, @PathVariable("componentCode") String componentCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws AuthenticationException {
		authorize(auth, projectCode, ProjectPermission.VERSION_COMPONENT);
		Project project = projectService.getProjectInfo(projectCode, auth);
		Component component = componentService.getProjectComponent(project, componentCode);
		model.put("project", project);
		model.put("component", component);
		model.put("statusMap", componentService.getCaseStatusSummary(project, component));
		model.put("priorityMap", componentService.getCasePrioritySummary(project, component));
		model.put("assigneeMap", componentService.getAssigneeSummary(project, component));
		model.put("labelMap", componentService.getLabelSummary(project, component));
		model.put("versionMap", componentService.getVersionSummary(project, component));
		model.put("totalCase", component.getNoCase());
		return new ModelAndView("enterprise/project/component_casesummary");
	}
	
	@RequestMapping(value = "pro_{projectCode}/m_{componentCode}/case", method = RequestMethod.GET) 
	public ModelAndView cases(@PathVariable("projectCode") String projectCode, @PathVariable("componentCode") String componentCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws AuthenticationException {
		authorize(auth, projectCode, ProjectPermission.VERSION_COMPONENT);
		Project project = projectService.getProjectInfo(projectCode, auth);
		Component component = componentService.getProjectComponent(project, componentCode);
		model.put("project", project);
		model.put("component", component);
		model.put("relatedCase", componentService.getRelatedCase(project, component, 0, LIST_ITEMS, true, "code"));
		return new ModelAndView("enterprise/project/component_cases");
	}
	
	private void loadUserGroups(EnterpriseAuthentication auth, Long project, ModelMap model) {
		model.put("leadGroup", componentService.getAuthorizedUsers(auth, project, ProjectPermission.VERSION_COMPONENT_MANAGE));
		model.put("userGroup", componentService.getAuthorizedUsers(auth, project, ProjectPermission.CASE));
	}
	
}
