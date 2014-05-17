package mu.codeoffice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DefaultMappingController {

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public String redirect() {
		return "redirect:/enterprise";
	}
	
	@RequestMapping(value = "enterprise/badRequest", method = RequestMethod.GET)
	public String badRequest() {
		return "enterprise/badRequest";
	}
}
