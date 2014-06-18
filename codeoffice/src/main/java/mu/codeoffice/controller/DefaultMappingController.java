package mu.codeoffice.controller;

import javax.servlet.http.HttpSession;

import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.service.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DefaultMappingController {
	
	@Autowired
	private TestService testService;

	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public String redirect() {
		return "redirect:/dashboard.html";
	}
	
	@RequestMapping(value = "/notfound.html", method = RequestMethod.GET)
	public String notFound() {
		return "error/404";
	}
	
	@RequestMapping(value = "/badrequest.html", method = RequestMethod.GET)
	public String badRequest() {
		return "error/400";
	}
	
	@RequestMapping(value = "/accessdenied.html", method = RequestMethod.GET)
	public String accessdenied(@AuthenticationPrincipal EnterpriseAuthentication auth,
			HttpSession session, ModelMap model) {
		if (auth.isExpired()) {
			return "redirect:logout.html";
		}
		model.addAttribute("error", true);
		return "error/403";
	}
	
	@RequestMapping(value = "/timeout.html", method = RequestMethod.GET)
	public String timeout(ModelMap model) {
		model.addAttribute("error", true);
		return "error/timeout";
	}
}
