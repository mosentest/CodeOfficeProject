package mu.codeoffice.controller;

import static mu.codeoffice.utility.MessageUtil.addErrorMessage;

import java.util.List;

import javax.servlet.http.HttpSession;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.dto.RoleGroupDTO;
import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.ProjectRole;
import mu.codeoffice.enums.ProjectPermission;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;
import mu.codeoffice.service.EnterpriseUserService;
import mu.codeoffice.service.RoleGroupService;

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
public class RoleGroupController extends ProjectPermissionRequired {
	
	@Autowired
	private RoleGroupService roleGroupService;
	
	@Autowired
	private EnterpriseUserService enterpriseUserService;

	@RequestMapping(value = "pro_{projectCode}/invite", method = RequestMethod.GET) 
	public ModelAndView invite(@PathVariable("projectCode") String projectCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) 
					throws AuthenticationException, InformationException {
		authorize(auth, projectCode, ProjectPermission.PROJECT_MANAGE);
		List<ProjectRole> roles = roleGroupService.getAvailableRoles(auth, projectCode);
		if (roles.size() == 0) {
			addErrorMessage(session, "No more roles to choose.");
			return new ModelAndView("redirect:/enterprise/pro_" + projectCode + "/rolegroups");
		}
		model.put("project", projectService.getProjectInfo(projectCode, auth));
		model.put("roles", roles);
		model.put("users", enterpriseUserService.getAvailableUserForProject(auth, projectCode));
		model.put("roleGroup", new RoleGroupDTO());
		return new ModelAndView("enterprise/project/project_role_manage", model);
	}
	
	@RequestMapping(value = "pro_{projectCode}/role_{roleId}", method = RequestMethod.GET) 
	public ModelAndView roleRequest(@PathVariable("projectCode") String projectCode, @PathVariable("roleId") Long roleId,
			@ModelAttribute("roleGroup") RoleGroupDTO roleGroupDTO,
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) 
					throws AuthenticationException, InformationException {
		authorize(auth, projectCode, ProjectPermission.PROJECT_MANAGE);
		return new ModelAndView("enterprise/project/project_role_manage", model);
	}
	
	@RequestMapping(value = "pro_{projectCode}/role_{roleId}", method = RequestMethod.POST) 
	public ModelAndView role(@PathVariable("projectCode") String projectCode, @PathVariable("roleId") Long roleId,
			@RequestParam(value = "role", required = false) ProjectRole role,
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) 
					throws AuthenticationException, InformationException {
		authorize(auth, projectCode, ProjectPermission.PROJECT_MANAGE);
		return new ModelAndView("enterprise/project/project_role_manage", model);
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
