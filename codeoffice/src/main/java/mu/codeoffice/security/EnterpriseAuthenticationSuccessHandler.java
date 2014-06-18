package mu.codeoffice.security;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mu.codeoffice.service.SystemSettingsService;
import mu.codeoffice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class EnterpriseAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SystemSettingsService systemSettingsService;
	
	@Autowired
	private ServletContext servletContext;

	@SuppressWarnings("unchecked")
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		EnterpriseAuthentication auth = (EnterpriseAuthentication) authentication.getPrincipal();
		auth.getUser().setLogin(new Date());
		userService.update(auth.getUser());
		HttpSession session = request.getSession();
		synchronized(this) {
			Object o = servletContext.getAttribute(auth.getEnterprise().getCode() + "_SESSIONS");
			Map<Long, SessionObject> sessions = null;
			if (o == null) {
				sessions = new LinkedHashMap<>();
				servletContext.setAttribute(auth.getEnterprise().getCode() + "_SESSIONS", sessions);
			} else {
				sessions = (Map<Long, SessionObject>) o;
			}
			String ipAddress = request.getHeader("X-FORWARDED-FOR");  
			if (ipAddress == null) {  
				ipAddress = request.getRemoteAddr();  
			}
			SessionObject sessionObject = new SessionObject(request.getSession().getId(), ipAddress, auth);
			sessions.put(auth.getUser().getId(), sessionObject);
			session.setAttribute("SESSION", sessionObject);
		}
		
		response.sendRedirect(request.getContextPath() + "/dashboard.html");
	}

}
