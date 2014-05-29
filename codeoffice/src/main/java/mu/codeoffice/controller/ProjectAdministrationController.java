package mu.codeoffice.controller;

import javax.servlet.ServletContext;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.settings.ProjectPermissionScheme;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;
import mu.codeoffice.security.GlobalPermission;
import mu.codeoffice.security.Permission;
import mu.codeoffice.service.ProjectAdministrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/administration/")
public class ProjectAdministrationController implements PermissionRequired {

	@Autowired
	private ProjectAdministrationService projectAdministrationService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ServletContext servletContext;
	
	@Override
	public void authorize(EnterpriseAuthentication auth, Object object,
			Permission... permissions) throws AuthenticationException {
		for (Permission permission : permissions) {
			if (!permission.isAuthorized(auth.getUser().getGlobalPermissionValue())) {
				throw new EnterpriseAuthenticationException(
						messageSource.getMessage("permission.denied_require_permission", new Object[]{ permission.getKey() }, LocaleContextHolder.getLocale()));
			}
		}
	}

	@RequestMapping(value = "project.html", method = RequestMethod.GET)
	public ModelAndView home(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		authorize(auth, null, GlobalPermission.ADMIN);
		return new ModelAndView("administration/project_home", model);
	}

	@RequestMapping(value = "permissionSchemes.html", method = RequestMethod.GET)
	public ModelAndView permissionSchemes(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		authorize(auth, null, GlobalPermission.ADMIN);
		model.put("permissionSchemes", projectAdministrationService.getProjectPermissionSchemes(auth));
		model.put("permissionScheme", new ProjectPermissionScheme());
		return new ModelAndView("administration/project_permissionSchemes", model);
	}

	@RequestMapping(value = "permissionScheme/{schemeName}.html", method = RequestMethod.GET)
	public ModelAndView permissionScheme(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@PathVariable("schemeName") String schemeName, ModelMap model) {
		authorize(auth, null, GlobalPermission.ADMIN);
		model.put("permissionScheme", projectAdministrationService.getProjectPermissionScheme(auth, schemeName));
		return new ModelAndView("administration/project_permissionScheme", model);
	}

	@RequestMapping(value = "permissionScheme/{schemeName}/clone", method = RequestMethod.GET)
	public String clonePermissionSchemes(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@PathVariable("schemeName") String schemeName, RedirectAttributes redirectAttributes) {
		authorize(auth, null, GlobalPermission.ADMIN);
		try {
			String name = projectAdministrationService.clonePermissionScheme(auth, schemeName);
			redirectAttributes.addFlashAttribute(TIP, "Permission Scheme has been cloned with name '" + name +  "'.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
		}
		return "redirect:/administration/permissionSchemes.html";
	}

	@RequestMapping(value = "permissionScheme/create", method = RequestMethod.POST)
	public String createPermissionSchemes(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@ModelAttribute("permissionScheme") ProjectPermissionScheme permissionScheme, RedirectAttributes redirectAttributes) {
		authorize(auth, null, GlobalPermission.ADMIN);
		try {
			projectAdministrationService.createPermissionScheme(auth, permissionScheme);
			redirectAttributes.addFlashAttribute(TIP, "Permission Scheme '" + permissionScheme.getName() + "' has been create.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
		}
		return "redirect:/administration/permissionSchemes.html";
	}

	@RequestMapping(value = "permissionScheme/{schemeName}/delete", method = RequestMethod.POST)
	public String deletePermissionSchemes(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@PathVariable("schemeName") String schemeName, RedirectAttributes redirectAttributes) {
		authorize(auth, null, GlobalPermission.ADMIN);
		try {
			projectAdministrationService.deletePermissionScheme(auth, schemeName);
			redirectAttributes.addFlashAttribute(TIP, "Permission Scheme '" + schemeName + "' has been updated.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
		}
		return "redirect:/administration/permissionSchemes.html";
	}

	@RequestMapping(value = "projectRoles.html", method = RequestMethod.GET)
	public ModelAndView projectRoles(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		authorize(auth, null, GlobalPermission.ADMIN);
		model.put("projectRoles", projectAdministrationService.getProjectRoles(auth));
		return new ModelAndView("administration/project_projectRole", model);
	}

	@RequestMapping(value = "projects.html", method = RequestMethod.GET)
	public ModelAndView projects(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		authorize(auth, null, GlobalPermission.ADMIN);
		return new ModelAndView("administration/project_projects", model);
	}
	
	@RequestMapping(value = "workflow.html", method = RequestMethod.GET)
	public ModelAndView workflowView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		authorize(auth, null, GlobalPermission.ADMIN);
		return new ModelAndView("administration/project_workflow", model);
	}
}
