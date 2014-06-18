package mu.codeoffice.controller;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {
	
	private static final Logger logger = Logger.getLogger(ExceptionAdvice.class);

	@ExceptionHandler(Exception.class)
	public String handle(Exception e) {
		logger.debug(e.getClass() + ":" + e.getMessage());
		return "redirect:/notfound.html";
	}

	@ExceptionHandler(AccessDeniedException.class)
	public String handle(AccessDeniedException e) {
		logger.debug(e.getClass() + ":" + e.getMessage());
		return "redirect:/accessdenied.html";
	}
	
}
