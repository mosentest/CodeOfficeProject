package mu.codeoffice.handler;

import javax.servlet.http.HttpSession;

import mu.codeoffice.entity.User;

public class SessionHandler {

	public static User getUser(HttpSession session) {
		Object attribute = session.getAttribute("currentUser");
		return attribute == null ? null : (User) attribute;
	}
	
	public static long getUid(HttpSession session) {
		User user = getUser(session);
		if (user == null) {
			return 0l;
		}
		return user.getId();
	}
	
	public static String getCurrentPage(HttpSession session) {
		Object attribute = session.getAttribute("currentPage");
		return attribute == null ? null : (String) attribute;
	}
	
}
