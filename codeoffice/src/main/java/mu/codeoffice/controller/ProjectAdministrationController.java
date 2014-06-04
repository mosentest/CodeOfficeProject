package mu.codeoffice.controller;

import javax.servlet.ServletContext;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.settings.ProjectPermissionScheme;
import mu.codeoffice.entity.settings.ProjectRole;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.service.ProjectAdministrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
	
	@RequestMapping(value = "permissionScheme/create", method = RequestMethod.POST)
	public String createPermissionSchemes(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@ModelAttribute("permissionScheme") ProjectPermissionScheme permissionScheme, RedirectAttributes redirectAttributes) {
		try {
			projectAdministrationService.create(auth, permissionScheme);
			redirectAttributes.addFlashAttribute(TIP, "Permission Scheme '" + permissionScheme.getName() + "' has been create.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/permissionSchemes.html";
	}

	@RequestMapping(value = "permissionScheme/delete", method = RequestMethod.POST)
	public String deletePermissionSchemes(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("scheme") String scheme, RedirectAttributes redirectAttributes) {
		try {
			projectAdministrationService.deletePermissionScheme(auth, scheme);
			redirectAttributes.addFlashAttribute(TIP, "Permission Scheme '" + scheme + "' has been updated.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/permissionSchemes.html";
	}
	
	@RequestMapping(value = "projectRole/edit.html", method = RequestMethod.GET)
	public ModelAndView projectRoleEditRequest(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("role") String role, RedirectAttributes redirectAttributes, ModelMap model) {
		ProjectRole projectRole = projectAdministrationService.getProjectRoles(auth, role);
		if (projectRole == null) {
			redirectAttributes.addAttribute(ERROR, "Project role doesn't exist.");
			return new ModelAndView("redirect/administration/projectRoles.html");
		}
		model.put("projectRole", projectRole);
		return new ModelAndView("administration/project_projectRole_form", model);
	}

	@RequestMapping(value = "projectRoles.html", method = RequestMethod.GET)
	public ModelAndView projectRoles(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("projectRoles", projectAdministrationService.getProjectRoles(auth));
		model.put("projectRole", new ProjectRole());
		return new ModelAndView("administration/project_projectRoles", model);
	}

	@RequestMapping(value = "projects.html", method = RequestMethod.GET)
	public ModelAndView projects(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/project_projects", model);
	}
}
