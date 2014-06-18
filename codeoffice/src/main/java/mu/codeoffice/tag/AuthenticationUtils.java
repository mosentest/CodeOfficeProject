package mu.codeoffice.tag;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.security.SessionObject;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;

public class AuthenticationUtils {
	
	@SuppressWarnings("unchecked")
	public synchronized static boolean invalidateUser(Enterprise enterprise, Long user, 
			ServletContext servletContext, SessionRegistry sessionRegistry, boolean invalidate) {
		Map<Long, SessionObject> sessionMap = (Map<Long, SessionObject>) servletContext.getAttribute(enterprise.getCode() + "_SESSIONS");
		SessionObject sessionObject = sessionMap.remove(user);
		if (sessionObject == null) {
			return false;
		}
		sessionObject.getAuth().eraseCredentials();
		sessionObject.getAuth().invalidate();
		List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(
				sessionObject.getAuth(), false);  
		for (SessionInformation sessionInformation : sessionInformations) {  
        	sessionInformation.expireNow();  
            sessionRegistry.removeSessionInformation(sessionInformation.getSessionId()); 
        }
		if (!invalidate) {
			sessionObject.getAuth().setLatestMessage("Your account authority has been updated, please login again.");
		}
		return true;
	}
	
}
