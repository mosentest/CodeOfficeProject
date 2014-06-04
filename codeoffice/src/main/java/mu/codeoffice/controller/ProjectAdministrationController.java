package mu.codeoffice.controller;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.dto.ProjectRoleDTO;
import mu.codeoffice.entity.settings.ProjectPermissionScheme;
import mu.codeoffice.entity.settings.ProjectRole;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.ProjectPermission;
import mu.codeoffice.service.ProjectAdministrationService;
import mu.codeoffice.service.ProjectRoleService;
import mu.codeoffice.service.UserGroupService;
import mu.codeoffice.service.UserService;
import mu.codeoffice.utility.ProjectPermissionEditor;
import mu.codeoffice.utility.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/administration/")
public class ProjectAdministrationController implements GenericController {

	@Autowired
	private ProjectAdministrationService projectAdministrationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserGroupService userGroupService;
	
	@Autowired
	private ProjectRoleService projectRoleService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ServletContext servletContext;

	@RequestMapping(value = "project.html", method = RequestMethod.GET)
	public ModelAndView home(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/project_home", model);
	}

	@RequestMapping(value = "permissionSchemes.html", method = RequestMethod.GET)
	public ModelAndView permissionSchemes(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("permissionSchemes", projectAdministrationService.getProjectPermissionSchemes(auth));
		model.put("permissionScheme", new ProjectPermissionScheme());
		return new ModelAndView("administration/project_permissionSchemes", model);
	}

	@RequestMapping(value = "permissionScheme.html", method = RequestMethod.GET)
	public ModelAndView permissionScheme(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam("scheme") String scheme, RedirectAttributes redirectAttributes, ModelMap model) {
		ProjectPermissionScheme permissionScheme = projectAdministrationService.getProjectPermissionScheme(auth, scheme);
		if (permissionScheme == null) {
			redirectAttributes.addFlashAttribute(ERROR, "Project Permission Scheme doesn't exist.");
			return new ModelAndView("redirect:administration/projectPermissionSchemes.html");
		}
		model.put("permissionScheme", permissionScheme);
		model.put("userGroups", userGroupService.getGroups(auth));
		model.put("projectRoles", projectRoleService.getProjectRoles(auth, false));
		return new ModelAndView("administration/project_permissionScheme", model);
	}

	@RequestMapping(value = "permissionScheme/clone", method = RequestMethod.GET)
	public String clonePermissionSchemes(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam("scheme") String scheme, RedirectAttributes redirectAttributes) {
		try {
			String name = projectAdministrationService.clone(auth, scheme);
			if (name == null) {
				redirectAttributes.addFlashAttribute(ERROR, "Project Permission Scheme doesn't exist.");
				return "redirect:administration/projectPermissionSchemes.html";
			}
			redirectAttributes.addFlashAttribute(TIP, "Permission Scheme has been cloned with name '" + name +  "'.");
			return "redirect:/administration/permissionScheme.html?scheme=" + name;
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/permissionSchemes.html";
	}

	@RequestMapping(value = "permissionScheme/associate", method = RequestMethod.POST)
	public String associate(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("scheme") String scheme, 
			@RequestParam("permission") ProjectPermission permission, 
			@RequestParam(value = "groups", required = false) Long[] groups,
			@RequestParam(value = "roles", required = false) Long[] roles,
			@RequestParam(value = "users", required = false) Long[] users,
			RedirectAttributes redirectAttributes) {
		try {
			projectAdministrationService.associate(auth, scheme, permission, groups, roles, users);
			redirectAttributes.addFlashAttribute(TIP, "Project Permission has been updated.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/permissionScheme.html?scheme=" + scheme;
	}

	@RequestMapping(value = "permissionScheme/removeGroup", method = RequestMethod.GET)
	public String removeGroup(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("scheme") String scheme, 
			@RequestParam("permission") ProjectPermission permission, 
			@RequestParam("group") Long group,
			RedirectAttributes redirectAttributes) {
		try {
			projectAdministrationService.removeGroup(auth, scheme, permission, group);
			redirectAttributes.addFlashAttribute(TIP, "Project Permission Scheme has been updated.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/permissionScheme.html?scheme=" + scheme;
	}

	@RequestMapping(value = "permissionScheme/removeRole", method = RequestMethod.GET)
	public String removeRole(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("scheme") String scheme, 
			@RequestParam("permission") ProjectPermission permission, 
			@RequestParam("role") Long role,
			RedirectAttributes redirectAttributes) {
		try {
			projectAdministrationService.removeRole(auth, scheme, permission, role);
			redirectAttributes.addFlashAttribute(TIP, "Project Permission Scheme has been updated.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/permissionScheme.html?scheme=" + scheme;
	}

	@RequestMapping(value = "permissionScheme/removeUser", method = RequestMethod.GET)
	public String removeUser(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("scheme") String scheme, 
			@RequestParam("permission") ProjectPermission permission, 
			@RequestParam("user") Long user,
			RedirectAttributes redirectAttributes) {
		try {
			projectAdministrationService.removeUser(auth, scheme, permission, user);
			redirectAttributes.addFlashAttribute(TIP, "Project Permission Scheme has been updated.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/permissionScheme.html?scheme=" + scheme;
	}

	@RequestMapping(value = "permissionScheme/resetAll", method = RequestMethod.GET)
	public String resetAll(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("scheme") String scheme, 
			RedirectAttributes redirectAttributes) {
		try {
			projectAdministrationService.resetAll(auth, scheme);
			redirectAttributes.addFlashAttribute(TIP, "Project Permission Scheme has been reset.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/permissionScheme.html?scheme=" + scheme;
	}

	@RequestMapping(value = "permissionScheme/reset", method = RequestMethod.GET)
	public String reset(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("scheme") String scheme, 
			@RequestParam("permission") ProjectPermission permission, 
			RedirectAttributes redirectAttributes) {
		try {
			projectAdministrationService.reset(auth, scheme, permission);
			redirectAttributes.addFlashAttribute(TIP, "Project Permission Scheme has been updated.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/permissionScheme.html?scheme=" + scheme;
	}
	
	@RequestMapping(value = "permissionScheme/create", method = RequestMethod.POST)
	public String create(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@ModelAttribute("permissionScheme") @Valid ProjectPermissionScheme permissionScheme, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("formErrors", initErrorMessages(result.getAllErrors(), messageSource));
		} else {
			try {
				projectAdministrationService.create(auth, permissionScheme);
				redirectAttributes.addFlashAttribute(TIP, "Permission Scheme '" + permissionScheme.getName() + "' has been create.");
			} catch (InformationException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
			}
		}
		return "redirect:/administration/permissionSchemes.html";
	}

	@RequestMapping(value = "permissionScheme/delete", method = RequestMethod.POST)
	public String deletePermissionScheme(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("scheme") String scheme, RedirectAttributes redirectAttributes) {
		try {
			projectAdministrationService.deletePermissionScheme(auth, scheme);
			redirectAttributes.addFlashAttribute(TIP, "Permission Scheme '" + scheme + "' has been updated.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/permissionSchemes.html";
	}

	@RequestMapping(value = "projectRole/create", method = RequestMethod.POST)
	public String create(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@ModelAttribute("projectRole") @Valid ProjectRole projectRole, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("formErrors", initErrorMessages(result.getAllErrors(), messageSource));
		} else {
			try {
				projectAdministrationService.create(auth, projectRole);
				redirectAttributes.addFlashAttribute(TIP, "Project Role '" + projectRole.getName() + "' has been deleted.");
			} catch (InformationException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
			}
		}
		return "redirect:/administration/projectRoles.html";
	}

	@RequestMapping(value = "projectRole/delete", method = RequestMethod.POST)
	public String deleteProjectRole(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("role") String role, RedirectAttributes redirectAttributes) {
		try {
			projectAdministrationService.deleteProjectRole(auth, role);
			redirectAttributes.addFlashAttribute(TIP, "Project Role '" + role + "' has been deleted.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/projectRoles.html";
	}
	
	@RequestMapping(value = "projectRole/edit", method = RequestMethod.POST)
	public String projectRoleEdit(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("role") String role,
			@ModelAttribute("projectRole") @Valid ProjectRoleDTO projectRoleDTO, BindingResult result, 
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("formErrors", initErrorMessages(result.getAllErrors(), messageSource));
		} else {
			try {
				projectAdministrationService.edit(auth, projectRoleDTO);
				redirectAttributes.addFlashAttribute(TIP, "Project Role has been updated");
				return "redirect:/administration/projectRole/edit.html?role=" + projectRoleDTO.getName();
			} catch (InformationException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
			}
		}
		return "redirect:/administration/projectRole/edit.html?role=" + role;
		
	}

	@RequestMapping(value = "projectRoles.html", method = RequestMethod.GET)
	public ModelAndView projectRoles(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("projectRoles", projectRoleService.getProjectRoles(auth, true));
		model.put("projectRole", new ProjectRole());
		return new ModelAndView("administration/project_projectRoles", model);
	}

	@RequestMapping(value = "projectRole/edit.html", method = RequestMethod.GET)
	public ModelAndView projectRole(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam(value = "role", required = true) String role, 
			@RequestParam(value = "query", required = false) String query, 
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex, 
			RedirectAttributes redirectAttributes, ModelMap model) {
		ProjectRole projectRole = projectRoleService.getProjectRole(auth, role);
		if (projectRole == null) {
			redirectAttributes.addFlashAttribute(ERROR, "Project role doesn't exist.");
			return new ModelAndView("redirect:/administration/projectRoles.html");
		}
		model.put("projectRole", new ProjectRoleDTO().toDTO(projectRole));
		model.put("userPage", projectRoleService.getUsers(auth, projectRole.getId(), pageIndex, query));
		if (!StringUtil.isEmptyString(query)) { model.put("query", query); }
		model.put("pageIndex", pageIndex);
		return new ModelAndView("administration/project_projectRole_form", model);
	}
	
	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(ProjectPermission.class, new ProjectPermissionEditor());
	}
}
