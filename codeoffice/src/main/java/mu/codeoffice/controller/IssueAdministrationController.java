package mu.codeoffice.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.service.SystemSettingsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/administration/")
public class IssueAdministrationController {

	@Autowired
	private SystemSettingsService systemAdministrationService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ServletContext servletContext;
	
	@RequestMapping(value = "issue.html", method = RequestMethod.GET)
	public ModelAndView home(@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) {
		return new ModelAndView("administration/issue_home", model);
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
}
