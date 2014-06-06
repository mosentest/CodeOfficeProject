package mu.codeoffice.controller;

import mu.codeoffice.security.EnterpriseAuthentication;

import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/administration/")
public class AdministrationController implements GenericController {
	
	
	@RequestMapping(value = "system.html", method = RequestMethod.GET)
	public ModelAndView system(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/system_home", model);
	}
	
	@RequestMapping(value = "add-ons.html", method = RequestMethod.GET)
	public ModelAndView addons(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/addon_home", model);
	}
	
	@RequestMapping(value = "userManagement.html", method = RequestMethod.GET)
	public ModelAndView um(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/um_home", model);
	}
	
	@RequestMapping(value = "project.html", method = RequestMethod.GET)
	public ModelAndView project(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/project_home", model);
	}
	
	@RequestMapping(value = "issue.html", method = RequestMethod.GET)
	public ModelAndView issue(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/issue_home", model);
	}
}
