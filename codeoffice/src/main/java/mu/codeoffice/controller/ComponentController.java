package mu.codeoffice.controller;

import static mu.codeoffice.utility.MessageUtil.addErrorMessage;
import static mu.codeoffice.utility.MessageUtil.addNoticeMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.dto.ComponentDTO;
import mu.codeoffice.entity.Component;
import mu.codeoffice.entity.Project;
import mu.codeoffice.enums.ProjectPermission;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.service.ComponentService;

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
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/enterprise/")
public class ComponentController extends ProjectPermissionRequired {
	
	private static final int LIST_ITEMS = 15;
	
	@Autowired
	private ComponentService componentService;
	
	@RequestMapping(value = "pro_{project}/m_{component}/edit", method = RequestMethod.GET) 
	public ModelAndView editRequest(@PathVariable("project") String projectCode, @PathVariable("component") String componentCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws AuthenticationException {
		authorize(auth, projectCode, ProjectPermission.VERSION_COMPONENT_MANAGE);
		Component component = componentService.getProjectComponent(projectCode, componentCode, auth);
		model.put("project", projectService.getProjectInfo(projectCode, auth));
		model.put("component", component);
		return new ModelAndView("enterprise/project/component_form", model);
	}

	@RequestMapping(value = "pro_{project}/m_{component}/edit", method = RequestMethod.POST) 
	public ModelAndView edit(@ModelAttribute Component component, @PathVariable("project") String projectCode, @PathVariable("component") String componentCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws AuthenticationException {
		authorize(auth, projectCode, ProjectPermission.VERSION_COMPONENT_MANAGE);
		return null;
	}

	@RequestMapping(value = "pro_{project}/m_{component}/create", method = RequestMethod.GET) 
	public ModelAndView createRequest(@PathVariable("project") String projectCode, @PathVariable("component") String componentCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws AuthenticationException {
		authorize(auth, projectCode, ProjectPermission.VERSION_COMPONENT_MANAGE);
		model.put("component", new Component());
		model.put("project", projectService.getProjectInfo(projectCode, auth));
		return new ModelAndView("enterprise/project/component_form", model);
	}

	@RequestMapping(value = "pro_{project}/m_{component}/create", method = RequestMethod.POST) 
	public ModelAndView create(@ModelAttribute Component component, @PathVariable("project") String projectCode, @PathVariable("component") String componentCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws AuthenticationException {
		authorize(auth, projectCode, ProjectPermission.VERSION_COMPONENT_MANAGE);
		return null;
	}

	@RequestMapping(value = "pro_{project}/m_{component}/delete", method = RequestMethod.GET) 
	public ModelAndView delete(@PathVariable("project") String projectCode, @PathVariable("component") String componentCode, 
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

	@RequestMapping(value = "pro_{project}/m_merge", method = RequestMethod.POST) 
	public ModelAndView mergeRequest(@ModelAttribute("mergeComponent") ComponentDTO<Component> mergeComponent, @PathVariable("project") String projectCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) throws AuthenticationException {
		for (String code : mergeComponent.getComponentCode()) {
			System.out.println(code);
		}
		model.put("project", projectService.getProjectInfo(projectCode, auth));
		authorize(auth, projectCode, ProjectPermission.VERSION_COMPONENT_MANAGE);
		return new ModelAndView("enterprise/project/component_merge", model);
	}

	@RequestMapping(value = "pro_{project}/m_{component}/merge", method = RequestMethod.POST) 
	public ModelAndView merge(@PathVariable("project") String projectCode, @PathVariable("component") String componentCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) throws AuthenticationException {
		authorize(auth, projectCode, ProjectPermission.VERSION_COMPONENT_MANAGE);
		addNoticeMessage(session, "Components: has been merged.");
		return new ModelAndView("redirect:enterprise/pro_" + projectCode + "/components");
	}
	
	@RequestMapping(value = "pro_{project}/m_{component}", method = RequestMethod.GET) 
	public ModelAndView summary(@PathVariable("project") String projectCode, @PathVariable("component") String componentCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws AuthenticationException {
		authorize(auth, projectCode, ProjectPermission.VERSION_COMPONENT);
		Project project = projectService.getProjectInfo(projectCode, auth);
		Component component = componentService.getProjectComponent(project, componentCode);
		model.put("project", project);
		model.put("component", component);
		model.put("monthlySummary", componentService.getComponentMonthlySummary(project, component));
		return new ModelAndView("enterprise/project/component_summary");
	}
	
	@RequestMapping(value = "pro_{project}/m_{component}/summary", method = RequestMethod.GET) 
	public ModelAndView casesummary(@PathVariable("project") String projectCode, @PathVariable("component") String componentCode, 
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
	
	@RequestMapping(value = "pro_{project}/m_{component}/case", method = RequestMethod.GET) 
	public ModelAndView cases(@PathVariable("project") String projectCode, @PathVariable("component") String componentCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws AuthenticationException {
		authorize(auth, projectCode, ProjectPermission.VERSION_COMPONENT);
		Project project = projectService.getProjectInfo(projectCode, auth);
		Component component = componentService.getProjectComponent(project, componentCode);
		model.put("project", project);
		model.put("component", component);
		model.put("relatedCase", componentService.getRelatedCase(project, component, 0, LIST_ITEMS, true, "code"));
		return new ModelAndView("enterprise/project/component_cases");
	}
	
}
