package mu.codeoffice.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(Exception.class)
	public String handle(Exception e) {
		System.out.println("wtf");
		return "error/404";
	}
}
