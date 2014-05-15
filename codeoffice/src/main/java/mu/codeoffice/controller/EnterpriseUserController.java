package mu.codeoffice.controller;

import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.service.EnterpriseUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/enterprise/")
public class EnterpriseUserController {
	
	@Autowired
	private EnterpriseUserService enterpriseUserSerivce;

	@RequestMapping(value = "user/{firstName}_{lastName}/info", method = RequestMethod.GET)
	public ModelAndView userinfo(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("enterprise/user_settings", model);
	}
	
	@RequestMapping(value = "user", method = RequestMethod.GET)
	public ModelAndView info(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("enterprise/user_settings", model);
	}
	
	@RequestMapping(value = "user/settings", method = RequestMethod.GET)
	public ModelAndView settings(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("enterprise/user_settings", model);
	}
}
