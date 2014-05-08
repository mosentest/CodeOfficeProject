package mu.codeoffice.handler;

import javax.servlet.http.HttpSession;

import mu.codeoffice.entity.EnterpriseUser;

public class SessionHandler {

	public static EnterpriseUser getUser(HttpSession session) {
		Object attribute = session.getAttribute("currentUser");
		return attribute == null ? null : (EnterpriseUser) attribute;
	}
	
	public static Long getId(HttpSession session) {
		EnterpriseUser user = getUser(session);
		if (user == null) {
			return 0l;
		}
		return user.getId();
	}
	
	public static String getCurrentNavigation(HttpSession session) {
		Object attribute = session.getAttribute("currentNavigation");
		return attribute == null ? null : (String) attribute;
	}
	
}
