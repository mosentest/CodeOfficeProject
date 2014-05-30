package mu.codeoffice.security;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mu.codeoffice.entity.settings.AdvancedGlobalSettings;
import mu.codeoffice.entity.settings.Announcement;
import mu.codeoffice.entity.settings.GlobalSettings;
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
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		EnterpriseAuthentication auth = (EnterpriseAuthentication) authentication.getPrincipal();
		auth.getUser().setLogin(new Date());
		userService.update(auth.getUser());
		HttpSession session = request.getSession();
		Announcement announcement = systemSettingsService.getAnnouncement(auth);
		GlobalSettings globalSettings = systemSettingsService.getGlobalSettings(auth);
		AdvancedGlobalSettings advancedGlobalSettings = systemSettingsService.getAdvancedGlobalSettings(auth);
		session.setAttribute(announcement.getSessionAttrKey(), announcement);
		session.setAttribute(globalSettings.getSessionAttrKey(), globalSettings);
		session.setAttribute(advancedGlobalSettings.getSessionAttrKey(), advancedGlobalSettings);
		if (servletContext.getAttribute(auth.getEnterprise().getCode() + "_ONLINE") == null) {
			servletContext.setAttribute(announcement.getSessionAttrKey(), announcement);
			servletContext.setAttribute(globalSettings.getSessionAttrKey(), globalSettings);
			servletContext.setAttribute(advancedGlobalSettings.getSessionAttrKey(), advancedGlobalSettings);
			servletContext.setAttribute(auth.getEnterprise().getCode() + "_ONLINE", new Boolean(true));
			servletContext.setAttribute(auth.getEnterprise().getCode() + "_SESSIONS", new LinkedHashMap<Long, SessionObject>());
		}
		session.setAttribute("ANNOUNCEMENT", announcement);
		@SuppressWarnings("unchecked")
		Map<Long, SessionObject> sessionMap = (HashMap<Long, SessionObject>) servletContext.getAttribute(auth.getEnterprise().getCode() + "_SESSIONS");
		String ipAddress = request.getHeader("X-FORWARDED-FOR");  
		if (ipAddress == null) {  
			ipAddress = request.getRemoteAddr();  
		}
		SessionObject sessionObject = new SessionObject(request.getSession().getId(), ipAddress, auth.getUser());
		sessionMap.put(auth.getUser().getId(), sessionObject);
		session.setAttribute("SESSION", sessionObject);
		
		response.sendRedirect(request.getContextPath() + "/dashboard.html");
	}

}
