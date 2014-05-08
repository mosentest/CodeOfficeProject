package mu.codeoffice.controller;

import javax.servlet.http.HttpSession;

import mu.codeoffice.handler.SessionHandler;
import mu.codeoffice.service.EnterpriseUserService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("enterpriseCommonController")
@RequestMapping("/enterprise/")
public class EnterpriseCommonController {
	
	private static final Logger logger = Logger.getLogger(EnterpriseCommonController.class);
	
	@Autowired
	private EnterpriseUserService enterpriseUserService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home(HttpSession session) {
		return "redirect:enterprise/login";
	}
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String loginRequest(HttpSession session) {
		if (SessionHandler.getUser(session) != null) {
			return "";
		}
		logger.debug("login");
		return "enterprise/login";
	}
	
	@RequestMapping(value = "accessdenied", method = RequestMethod.GET)
	public String accessDenied(ModelMap model) {
		model.addAttribute("error", true);
		return "enterprise/accessDenied";
	}
	
}
