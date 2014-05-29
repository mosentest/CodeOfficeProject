package mu.codeoffice.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {
	
	private static final Logger logger = Logger.getLogger(ExceptionAdvice.class);

	@ExceptionHandler(Exception.class)
	public String handle(Exception e) {
		logger.debug(e.getMessage());
		return "error/404";
	}
}
