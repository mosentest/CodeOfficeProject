package mu.codeoffice.controller;

import javax.validation.Valid;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.settings.ProjectPermissionScheme;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.ProjectPermission;
import mu.codeoffice.service.ProjectPermissionSchemeService;
import mu.codeoffice.service.ProjectRoleService;
import mu.codeoffice.service.UserGroupService;
import mu.codeoffice.service.UserService;
import mu.codeoffice.tag.Function;
import mu.codeoffice.utility.ProjectPermissionEditor;

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
@RequestMapping("/")
public class ProjectPermissionSchemeController implements GenericController {

	@Autowired
	private ProjectPermissionSchemeService projectPermissionSchemeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserGroupService userGroupService;
	
	@Autowired
	private ProjectRoleService projectRoleService;
	
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "administration/permissionSchemes.html", method = RequestMethod.GET)
	public ModelAndView permissionSchemes(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("permissionSchemes", projectPermissionSchemeService.getProjectPermissionSchemes(auth, true));
		model.put("permissionScheme", new ProjectPermissionScheme());
		return new ModelAndView("administration/project_permissionSchemes", model);
	}

	@RequestMapping(value = "administration/permissionScheme.html", method = RequestMethod.GET)
	public ModelAndView permissionScheme(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam("scheme") String scheme, RedirectAttributes redirectAttributes, ModelMap model) {
		scheme = Function.unmaskURL(scheme);
		ProjectPermissionScheme permissionScheme = projectPermissionSchemeService.getProjectPermissionScheme(auth, scheme);
		if (permissionScheme == null) {
			redirectAttributes.addFlashAttribute(ERROR, "Project Permission Scheme doesn't exist.");
			return new ModelAndView("redirect:administration/projectPermissionSchemes.html");
		}
		model.put("permissionScheme", permissionScheme);
		model.put("userGroups", userGroupService.getGroups(auth, false));
		model.put("projectRoles", projectRoleService.getProjectRoles(auth, false));
		return new ModelAndView("administration/project_permissionScheme", model);
	}

	@RequestMapping(value = "administration/permissionScheme/clone", method = RequestMethod.GET)
	public String clonePermissionSchemes(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam("scheme") String scheme, RedirectAttributes redirectAttributes) {
		try {
			scheme = Function.unmaskURL(scheme);
			String name = projectPermissionSchemeService.clone(auth, scheme);
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

	@RequestMapping(value = "administration/permissionScheme/associate", method = RequestMethod.POST)
	public String associate(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("scheme") String scheme, 
			@RequestParam("permission") ProjectPermission permission, 
			@RequestParam(value = "groups", required = false) Long[] groups,
			@RequestParam(value = "roles", required = false) Long[] roles,
			@RequestParam(value = "users", required = false) Long[] users,
			RedirectAttributes redirectAttributes) {
		try {
			scheme = Function.unmaskURL(scheme);
			projectPermissionSchemeService.associate(auth, scheme, permission, groups, roles, users);
			redirectAttributes.addFlashAttribute(TIP, "Project Permission has been updated.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/permissionScheme.html?scheme=" + scheme;
	}

	@RequestMapping(value = "administration/permissionScheme/removeGroup", method = RequestMethod.GET)
	public String removeGroup(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("scheme") String scheme, 
			@RequestParam("permission") ProjectPermission permission, 
			@RequestParam("group") Long group,
			RedirectAttributes redirectAttributes) {
		try {
			scheme = Function.unmaskURL(scheme);
			projectPermissionSchemeService.removeGroup(auth, scheme, permission, group);
			redirectAttributes.addFlashAttribute(TIP, "Project Permission Scheme has been updated.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/permissionScheme.html?scheme=" + scheme;
	}

	@RequestMapping(value = "administration/permissionScheme/removeRole", method = RequestMethod.GET)
	public String removeRole(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("scheme") String scheme, 
			@RequestParam("permission") ProjectPermission permission, 
			@RequestParam("role") Long role,
			RedirectAttributes redirectAttributes) {
		try {
			scheme = Function.unmaskURL(scheme);
			projectPermissionSchemeService.removeRole(auth, scheme, permission, role);
			redirectAttributes.addFlashAttribute(TIP, "Project Permission Scheme has been updated.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/permissionScheme.html?scheme=" + scheme;
	}

	@RequestMapping(value = "administration/permissionScheme/removeUser", method = RequestMethod.GET)
	public String removeUser(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("scheme") String scheme, 
			@RequestParam("permission") ProjectPermission permission, 
			@RequestParam("user") Long user,
			RedirectAttributes redirectAttributes) {
		try {
			scheme = Function.unmaskURL(scheme);
			projectPermissionSchemeService.removeUser(auth, scheme, permission, user);
			redirectAttributes.addFlashAttribute(TIP, "Project Permission Scheme has been updated.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/permissionScheme.html?scheme=" + scheme;
	}

	@RequestMapping(value = "administration/permissionScheme/resetAll", method = RequestMethod.GET)
	public String resetAll(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("scheme") String scheme, 
			RedirectAttributes redirectAttributes) {
		try {
			scheme = Function.unmaskURL(scheme);
			projectPermissionSchemeService.resetAll(auth, scheme);
			redirectAttributes.addFlashAttribute(TIP, "Project Permission Scheme has been reset.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/permissionScheme.html?scheme=" + scheme;
	}

	@RequestMapping(value = "administration/permissionScheme/reset", method = RequestMethod.GET)
	public String reset(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("scheme") String scheme, 
			@RequestParam("permission") ProjectPermission permission, 
			RedirectAttributes redirectAttributes) {
		try {
			scheme = Function.unmaskURL(scheme);
			projectPermissionSchemeService.reset(auth, scheme, permission);
			redirectAttributes.addFlashAttribute(TIP, "Project Permission Scheme has been updated.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/permissionScheme.html?scheme=" + scheme;
	}
	
	@RequestMapping(value = "administration/permissionScheme/create", method = RequestMethod.POST)
	public String create(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@ModelAttribute("permissionScheme") @Valid ProjectPermissionScheme permissionScheme, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("formErrors", initErrorMessages(result.getAllErrors(), messageSource));
		} else {
			try {
				projectPermissionSchemeService.create(auth, permissionScheme);
				redirectAttributes.addFlashAttribute(TIP, "Permission Scheme '" + permissionScheme.getName() + "' has been create.");
			} catch (InformationException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
			}
		}
		return "redirect:/administration/permissionSchemes.html";
	}

	@RequestMapping(value = "administration/permissionScheme/delete", method = RequestMethod.POST)
	public String deletePermissionScheme(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("scheme") String scheme, RedirectAttributes redirectAttributes) {
		try {
			scheme = Function.unmaskURL(scheme);
			projectPermissionSchemeService.deletePermissionScheme(auth, scheme);
			redirectAttributes.addFlashAttribute(TIP, "Permission Scheme '" + scheme + "' has been updated.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/permissionSchemes.html";
	}
	
	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(ProjectPermission.class, new ProjectPermissionEditor());
	}
	
}
