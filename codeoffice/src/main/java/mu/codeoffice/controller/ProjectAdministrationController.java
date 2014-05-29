package mu.codeoffice.controller;

import javax.servlet.ServletContext;

import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;
import mu.codeoffice.security.GlobalPermission;
import mu.codeoffice.security.Permission;
import mu.codeoffice.service.SystemSettingsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/administration/")
public class ProjectAdministrationController implements PermissionRequired {

	@Autowired
	private SystemSettingsService systemAdministrationService;
	
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
		return new ModelAndView("administration/project_permissionScheme", model);
	}

	@RequestMapping(value = "projectRoles.html", method = RequestMethod.GET)
	public ModelAndView projectRoles(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		authorize(auth, null, GlobalPermission.ADMIN);
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
