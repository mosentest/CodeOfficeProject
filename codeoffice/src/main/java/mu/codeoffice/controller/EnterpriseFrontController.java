package mu.codeoffice.controller;

import mu.codeoffice.service.TestService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/enterprise")
public class EnterpriseFrontController {
	
	private static final Logger logger = Logger.getLogger(EnterpriseFrontController.class);

	@Autowired
	private TestService testService;
	
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public ModelAndView defaultRequest() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth instanceof AnonymousAuthenticationToken) {
			return new ModelAndView("enterprise/login");
		} else {
			return new ModelAndView("enterprise/home");
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginRequest() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth instanceof AnonymousAuthenticationToken) {
			return "enterprise/login";
		}
		return "redirect:/enterprise";
	}
	
	@RequestMapping(value = "/accessdenied", method = RequestMethod.GET)
	public String accessDenied(ModelMap model) {
		model.addAttribute("error", true);
		logger.debug("");
		return "enterprise/accessDenied";
	}
	
}
