package mu.codeoffice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class ExceptionAdvice {

	@ExceptionHandler(IllegalStateException.class)
	public String illegalStateException(IllegalStateException e) {
		return "redirect:/enterprise/badRequest";
	}
	
}
