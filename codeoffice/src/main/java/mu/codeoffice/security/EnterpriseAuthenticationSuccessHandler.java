package mu.codeoffice.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mu.codeoffice.entity.Submenu;
import mu.codeoffice.service.EnterpriseUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class EnterpriseAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private EnterpriseUserService enterpriseUserService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		EnterpriseAuthentication auth = (EnterpriseAuthentication) authentication.getPrincipal();
		List<Submenu> submenu = enterpriseUserService.getSubmenuSettings(auth);
		if (submenu.size() > 0) {
			HttpSession session = request.getSession();
			session.setAttribute("SETTING_SUBMENU", submenu);
		}
		
		response.sendRedirect(request.getContextPath() + "/enterprise");
	}

}
