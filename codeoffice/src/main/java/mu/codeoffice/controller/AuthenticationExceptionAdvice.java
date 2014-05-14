package mu.codeoffice.controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ControllerAdvice
@EnableWebMvc
public class AuthenticationExceptionAdvice {

	@ExceptionHandler(AuthenticationException.class)
	public ModelAndView handleAuthenticationException(HttpSession session, AuthenticationException exception) {
		ModelAndView model = new ModelAndView("redirect:/enterprise/accessdenied");
		session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
		return model;
	}
	
}
