package mu.codeoffice.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.settings.Submenu;
import mu.codeoffice.service.EnterpriseSettingsService;
import mu.codeoffice.service.EnterpriseUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class EnterpriseAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private EnterpriseUserService enterpriseUserService;
	
	@Autowired
	private EnterpriseSettingsService enterpriseSettingsService;
	
	@Autowired
	private ServletContext servletContext;
	
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
		final String settingHeader = "ENT_SETTINGS_" + auth.getEnterprise().getId();
		if (servletContext.getAttribute(settingHeader) == null) {
			try {
				servletContext.setAttribute(settingHeader, new Object());
				servletContext.setAttribute(settingHeader + "_ACCOUNCEMENT" , enterpriseSettingsService.getAnnouncementBanner(auth));
				servletContext.setAttribute(settingHeader + "_ATTACHMENT", enterpriseSettingsService.getAttachmentSettings(auth));
				servletContext.setAttribute(settingHeader + "_GENERAL_PROJECT", enterpriseSettingsService.getGeneralProjectSettings(auth));
				servletContext.setAttribute(settingHeader + "_GLOBAL_ADVANCED", enterpriseSettingsService.getGlobalAdvancedSettings(auth));
				servletContext.setAttribute(settingHeader + "_GLOBAL_PERMISSION", enterpriseSettingsService.getGlobalPermission(auth));
				servletContext.setAttribute(settingHeader + "_GLOBAL", enterpriseSettingsService.getGlobalSettings(auth));
				servletContext.setAttribute(settingHeader + "_INTERNATIONALIZATION", enterpriseSettingsService.getInternationalizationSettings(auth));
				servletContext.setAttribute(settingHeader + "_PROJECT_PERMISSION", enterpriseSettingsService.getProjectPermission(auth));
				servletContext.setAttribute(settingHeader + "_TIME_TRACKING", enterpriseSettingsService.getTimeTrackingSettings(auth));
			} catch (AuthenticationException e) {
				servletContext.removeAttribute(settingHeader);
			} catch (InformationException e) {
				servletContext.removeAttribute(settingHeader);
			}
		}
		
		response.sendRedirect(request.getContextPath() + "/");
	}

}
