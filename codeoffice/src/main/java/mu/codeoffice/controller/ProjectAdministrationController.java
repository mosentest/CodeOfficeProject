package mu.codeoffice.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import mu.codeoffice.entity.settings.GlobalSettings;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;
import mu.codeoffice.security.Permission;
import mu.codeoffice.service.SystemAdministrationService;

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
	private SystemAdministrationService systemAdministrationService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ServletContext servletContext;
	
	@Override
	public void authorize(EnterpriseAuthentication auth, Object object,
			Permission... permissions) throws AuthenticationException {
		for (Permission permission : permissions) {
			if (!permission.isAuthorized(auth.getEnterpriseUser().getGlobalPermissionValue())) {
				throw new EnterpriseAuthenticationException(
						messageSource.getMessage("permission.denied_require_permission", new Object[]{ permission.getKey() }, LocaleContextHolder.getLocale()));
			}
		}
	}
	
	@RequestMapping(value = "workflow.html", method = RequestMethod.GET)
	public ModelAndView workflowView(@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) {
		return new ModelAndView("administration/project_workflow", model);
	}

	@RequestMapping(value = "workflowScheme.html", method = RequestMethod.GET)
	public ModelAndView workflowSchemeView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("globalSettings", new GlobalSettings());
		return new ModelAndView("administration/project_workflow_scheme", model);
	}
}
