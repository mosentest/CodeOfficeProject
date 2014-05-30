package mu.codeoffice.security;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

@Component
public class SessionTimeOutListener implements ApplicationListener<SessionDestroyedEvent> {

	@Autowired
	private ServletContext servletContext;

	@Override
	public void onApplicationEvent(SessionDestroyedEvent event) {
		List<SecurityContext> securityContexts = event.getSecurityContexts();
		EnterpriseAuthentication auth = null;
		for (SecurityContext securityContext : securityContexts) {
			auth = (EnterpriseAuthentication) securityContext.getAuthentication().getPrincipal();
			break;
		}
		@SuppressWarnings("unchecked")
		Map<Long, SessionObject> sessionMap = (Map<Long, SessionObject>) servletContext.getAttribute(auth.getEnterprise().getCode() + "_SESSIONS");
		synchronized (sessionMap) {
			sessionMap.remove(auth.getUser().getId());
		}
		
	}

}