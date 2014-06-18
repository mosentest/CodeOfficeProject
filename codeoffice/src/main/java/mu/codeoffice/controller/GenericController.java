package mu.codeoffice.controller;

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
	
	default public String[] initErrorMessages(List<ObjectError> errors, MessageSource messageSource, Object... args) {
		String[] array = new String[errors.size()];
		int index = 0;
		for (ObjectError error : errors) {
			array[index++] = messageSource.getMessage(error.getCodes()[0], args, LocaleContextHolder.getLocale());
		}
		return array;
	}
	
}
