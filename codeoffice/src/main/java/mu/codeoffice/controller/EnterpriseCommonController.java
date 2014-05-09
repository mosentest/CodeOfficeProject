package mu.codeoffice.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
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
public class EnterpriseCommonController {
	
	private static final Logger logger = Logger.getLogger(EnterpriseCommonController.class);

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
		return "enterprise/accessDenied";
	}
	
}
