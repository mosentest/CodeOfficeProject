package mu.codeoffice.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import mu.codeoffice.entity.settings.GlobalSettings;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;
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
public class IssueAdministrationController implements PermissionRequired {

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
	
	@RequestMapping(value = "issueType.html", method = RequestMethod.GET)
	public ModelAndView issueTypeView(@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) {
		return new ModelAndView("administration/issue_issueType", model);
	}

	@RequestMapping(value = "issueTypeScheme.html", method = RequestMethod.GET)
	public ModelAndView issueTypeSchemeView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("globalSettings", new GlobalSettings());
		return new ModelAndView("administration/issue_issueTypeScheme", model);
	}

	@RequestMapping(value = "subtask.html", method = RequestMethod.GET)
	public ModelAndView subtaskView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/issue_subtask", model);
	}

	@RequestMapping(value = "screen.html", method = RequestMethod.GET)
	public ModelAndView screenView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/issue_screen", model);
	}

	@RequestMapping(value = "screenScheme.html", method = RequestMethod.GET)
	public ModelAndView screenSchemeView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/issue_screenScheme", model);
	}
	
	@RequestMapping(value = "issueTypeScreenScheme.html", method = RequestMethod.GET)
	public ModelAndView issueTypeScreenSchemeView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/issue_issuetypeScreenScheme", model);
	}

	@RequestMapping(value = "customField.html", method = RequestMethod.GET)
	public ModelAndView customFieldView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/issue_customField", model);
	}

	@RequestMapping(value = "fieldConfiguration.html", method = RequestMethod.GET)
	public ModelAndView fieldConfigurationView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/issue_fieldConfiguration", model);
	}
	
	@RequestMapping(value = "fieldConfigurationScheme.html", method = RequestMethod.GET)
	public ModelAndView fieldConfigurationSchemeView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/issue_fieldConfigurationScheme", model);
	}

	@RequestMapping(value = "status.html", method = RequestMethod.GET)
	public ModelAndView statusView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/issue_status", model);
	}

	@RequestMapping(value = "resolution.html", method = RequestMethod.GET)
	public ModelAndView resolutionView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/issue_resolution", model);
	}
	
	@RequestMapping(value = "priority.html", method = RequestMethod.GET)
	public ModelAndView priorityView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/issue_priority", model);
	}
}
