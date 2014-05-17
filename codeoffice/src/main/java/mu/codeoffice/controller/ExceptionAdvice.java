package mu.codeoffice.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(IllegalStateException.class)
	public String illegalStateException(IllegalStateException e) {
		return "redirect:/enterprise/badRequest";
	}
	
}
