package mu.codeoffice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.ObjectError;

public interface GenericController {

	public static final String INFO = "INFO";
	public static final String TIP = "TIP";
	public static final String WARNING = "WARNING";
	public static final String ERROR = "ERROR";
	public static final String ALERT = "ALERT";
	
	default public List<String> initErrorMessages(List<ObjectError> errors, MessageSource messageSource) {
		List<String> list = new ArrayList<>();
		for (ObjectError error : errors) {
			list.add(messageSource.getMessage(error.getCodes()[0], null, LocaleContextHolder.getLocale()));
		}
		return list;
	}
	
}
