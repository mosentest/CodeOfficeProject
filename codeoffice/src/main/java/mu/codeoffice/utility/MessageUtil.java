package mu.codeoffice.utility;

import java.util.Arrays;

import javax.servlet.http.HttpSession;

public class MessageUtil {

	private static final String ERROR_MESSAGE = "ERROR_MESSAGE";

	private static final String WARN_MESSAGE = "WARN_MESSAGE";

	private static final String NOTICE_MESSAGE = "NOTICE_MESSAGE";
	
	public static void addErrorMessage(HttpSession session, String...message) {
		addMessage(session, ERROR_MESSAGE, message);
	}
	
	public static void addWarnMessage(HttpSession session, String...message) {
		addMessage(session, WARN_MESSAGE, message);
	}
	
	public static void addNoticeMessage(HttpSession session, String...message) {
		addMessage(session, NOTICE_MESSAGE, message);
	}
	
	private static void addMessage(HttpSession session, String key, String...message) {
		if (message != null) {
			session.setAttribute(key, Arrays.asList(message));
		}
	}
	
}
