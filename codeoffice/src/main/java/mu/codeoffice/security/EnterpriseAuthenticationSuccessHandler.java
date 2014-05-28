package mu.codeoffice.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mu.codeoffice.service.UserService;
import mu.codeoffice.service.SystemSettingsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class EnterpriseAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SystemSettingsService enterpriseSettingsService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		EnterpriseAuthentication auth = (EnterpriseAuthentication) authentication.getPrincipal();
		auth.getUser().setLogin(new Date());
		userService.update(auth.getUser());
		HttpSession session = request.getSession();
		session.setAttribute("SETTINGS_ANNOUNCEMENT", enterpriseSettingsService.getAnnouncement(auth));
		
		response.sendRedirect(request.getContextPath() + "/dashboard.html");
	}

}
