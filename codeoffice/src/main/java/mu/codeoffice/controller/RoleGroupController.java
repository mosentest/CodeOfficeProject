package mu.codeoffice.controller;

import javax.servlet.http.HttpSession;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.RoleGroup;
import mu.codeoffice.enums.ProjectPermission;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/enterprise/")
public class RoleGroupController extends ProjectPermissionRequired {
	
	@RequestMapping(value = "pro_{projectCode}/rolegroup/create", method = RequestMethod.GET) 
	public ModelAndView createRequest(@PathVariable("projectCode") String projectCode, @PathVariable("versionCode") String versionCode, 
			@ModelAttribute("roleGroup") RoleGroup roleGroup,
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) 
					throws AuthenticationException, InformationException {
		authorize(auth, projectCode, ProjectPermission.PROJECT_MANAGE);
		return null;
	}
	
	@RequestMapping(value = "pro_{projectCode}/rolegroup/create", method = RequestMethod.POST) 
	public ModelAndView create(@PathVariable("projectCode") String projectCode, @PathVariable("versionCode") String versionCode, 
			@ModelAttribute("roleGroup") RoleGroup roleGroup,
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) 
					throws AuthenticationException, InformationException {
		authorize(auth, projectCode, ProjectPermission.PROJECT_MANAGE);
		return null;
	}
	
	@RequestMapping(value = "pro_{projectCode}/r_{role}/edit", method = RequestMethod.GET) 
	public ModelAndView editRequest(@PathVariable("projectCode") String projectCode, @PathVariable("versionCode") String versionCode, 
			@ModelAttribute("roleGroup") RoleGroup roleGroup,
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) 
					throws AuthenticationException, InformationException {
		authorize(auth, projectCode, ProjectPermission.PROJECT_MANAGE);
		return null;
	}
	
	@RequestMapping(value = "pro_{projectCode}/r_{role}/edit", method = RequestMethod.POST) 
	public ModelAndView edit(@PathVariable("projectCode") String projectCode, @PathVariable("versionCode") String versionCode, 
			@ModelAttribute("roleGroup") RoleGroup roleGroup,
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) 
					throws AuthenticationException, InformationException {
		authorize(auth, projectCode, ProjectPermission.PROJECT_MANAGE);
		return null;
	}

	@RequestMapping(value = "pro_{projectCode}/roleauth", method = RequestMethod.GET)
	public ModelAndView roleauth(@PathVariable("projectCode") String projectCode, @AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) 
			throws EnterpriseAuthenticationException {
		authorize(auth, projectCode, ProjectPermission.PROJECT);
		Project project = projectService.getProjectInfo(projectCode, auth);
		model.put("project", project);
		model.put("roles", projectService.getProjectRoles(auth, projectCode));
		model.put("permissions", ProjectPermission.values());
		return new ModelAndView("enterprise/project/project_roleauth", model);
	}
	
}
