package mu.codeoffice.controller;

import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.service.TestService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EnterpriseFrontController {
	
	private static final Logger logger = Logger.getLogger(EnterpriseFrontController.class);

	@Autowired
	private TestService testService;
	
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public ModelAndView defaultRequest(@AuthenticationPrincipal EnterpriseAuthentication auth) {
		if (auth == null) {
			return new ModelAndView("redirect:/login");
		} else {
			return new ModelAndView("redirect:/home");
		}
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		if (auth == null) {
			return new ModelAndView("redirect:/login");
		}
		return new ModelAndView("home", model);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginRequest() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth instanceof AnonymousAuthenticationToken) {
			return "login";
		}
		return "redirect:/home";
	}
	
	@RequestMapping(value = "/accessdenied", method = RequestMethod.GET)
	public String accessdenied(ModelMap model) {
		model.addAttribute("error", true);
		logger.debug("");
		return "error/403";
	}
	
	@RequestMapping(value = "/timeout", method = RequestMethod.GET)
	public String timeout(ModelMap model) {
		model.addAttribute("error", true);
		return "error/timeout";
	}
	
}
