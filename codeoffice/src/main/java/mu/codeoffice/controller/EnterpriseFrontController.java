package mu.codeoffice.controller;

import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.service.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EnterpriseFrontController {

	@Autowired
	private TestService testService;
	
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public ModelAndView defaultRequest(@AuthenticationPrincipal EnterpriseAuthentication auth) {
		if (auth == null) {
			return new ModelAndView("redirect:/login.html");
		} else {
			return new ModelAndView("redirect:/dashboard.html");
		}
	}

	@RequestMapping(value = "/dashboard.html", method = RequestMethod.GET)
	public ModelAndView home(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		if (auth == null) {
			return new ModelAndView("redirect:/login.html");
		}
		return new ModelAndView("dashboard", model);
	}
	
	@RequestMapping(value = "/login.html", method = RequestMethod.GET)
	public String loginRequest(@AuthenticationPrincipal EnterpriseAuthentication auth) {
		if (auth == null) {
			return "login";
		}
		return "redirect:/dashboard.html";
	}
	
}
