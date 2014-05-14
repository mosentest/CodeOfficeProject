package mu.codeoffice.controller;

import mu.codeoffice.entity.Component;
import mu.codeoffice.entity.Project;
import mu.codeoffice.enums.ProjectRoleType;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;
import mu.codeoffice.service.ComponentService;
import mu.codeoffice.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/enterprise/")
public class ComponentController {
	
	private static final int LIST_ITEMS = 15;

	private static final ProjectRoleType[] VIEW_AUTH = {
		ProjectRoleType.VERSION_COMPONENT
	};
	
	private static final ProjectRoleType[] MANAGE_AUTH = {
		ProjectRoleType.VERSION_COMPONENT_MANAGE
	};
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ComponentService componentService;
	
	@RequestMapping(value = "pro_{project}/m_{component}", method = RequestMethod.GET) 
	public ModelAndView summary(@PathVariable("project") String projectCode, @PathVariable("component") String componentCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws EnterpriseAuthenticationException {
		authorizeView(auth, projectCode);
		Project project = projectService.getProjectInfo(projectCode, auth);
		Component component = componentService.getProjectComponentInfo(project, componentCode);
		model.put("project", project);
		model.put("component", component);
		model.put("monthlySummary", componentService.getComponentMonthlySummary(project, component));
		return new ModelAndView("enterprise/project/component_summary");
	}
	
	@RequestMapping(value = "pro_{project}/m_{component}/summary", method = RequestMethod.GET) 
	public ModelAndView casesummary(@PathVariable("project") String projectCode, @PathVariable("component") String componentCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws EnterpriseAuthenticationException {
		authorizeView(auth, projectCode);
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
			@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws EnterpriseAuthenticationException {
		authorizeView(auth, projectCode);
		Project project = projectService.getProjectInfo(projectCode, auth);
		Component component = componentService.getProjectComponent(project, componentCode);
		model.put("project", project);
		model.put("component", component);
		model.put("relatedCase", componentService.getRelatedCase(project, component, 0, LIST_ITEMS, true, "code"));
		return new ModelAndView("enterprise/project/component_cases");
	}
	
	public ModelAndView versionRequest(String projectCode, @AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		authorizeManage(auth, projectCode);
		authorizeView(auth, projectCode);
		return null;
	}
	
	private void authorizeManage(EnterpriseAuthentication auth, String projectCode) throws EnterpriseAuthenticationException {
		if (!auth.hasProjectAuthority() && !auth.projectAuthenticate(projectService.getProjectAuthority(auth.getEnterpriseUser(), projectCode), MANAGE_AUTH)) {
			throw new EnterpriseAuthenticationException("You are not authorized.");
		}
	}
	
	private void authorizeView(EnterpriseAuthentication auth, String projectCode) throws EnterpriseAuthenticationException {
		if (!auth.hasProjectAuthority() && !auth.projectAuthenticate(projectService.getProjectAuthority(auth.getEnterpriseUser(), projectCode), VIEW_AUTH)) {
			throw new EnterpriseAuthenticationException("You are not authorized.");
		}
	}
}
