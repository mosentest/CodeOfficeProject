package mu.codeoffice.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;
import mu.codeoffice.security.Permission;
import mu.codeoffice.service.EnterpriseSettingsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/settings/")
public class EnterpriseSettingsController implements PermissionRequired {

	@Autowired
	private EnterpriseSettingsService enterpriseSettingsService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ServletContext servletContext;
	
	@Override
	public void authorize(EnterpriseAuthentication auth, Object object,
			Permission... permissions) throws AuthenticationException {
		for (Permission permission : permissions) {
			if (!permission.isAuthorized(auth.getEnterpriseUser().getGlobalPermissionValue())) {
				throw new EnterpriseAuthenticationException(
						messageSource.getMessage("permission.denied_require_permission", new Object[]{ permission.getKey() }, LocaleContextHolder.getLocale()));
			}
		}
	}
	
	@RequestMapping(value = "home.html", method = RequestMethod.GET)
	public ModelAndView home(@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) {
		return new ModelAndView("settings/home", model);
	}

	@RequestMapping(value = "global.html", method = RequestMethod.GET)
	public ModelAndView globalView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("settings/global", model);
	}

	@RequestMapping(value = "globaladvanced.html", method = RequestMethod.GET)
	public ModelAndView globalAdvancedView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("settings/globaladvanced", model);
	}

	@RequestMapping(value = "internationalization.html", method = RequestMethod.GET)
	public ModelAndView internationalizationView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("settings/internationalization", model);
	}

	@RequestMapping(value = "announcement.html", method = RequestMethod.GET)
	public ModelAndView announcementView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("settings/announcement", model);
	}
	
	@RequestMapping(value = "generalproject.html", method = RequestMethod.GET)
	public ModelAndView generalProjectView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("settings/generalproject", model);
	}
	
	@RequestMapping(value = "issuelink.html", method = RequestMethod.GET)
	public ModelAndView issueLinkView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("settings/issuelink", model);
	}
	
	@RequestMapping(value = "subtask.html", method = RequestMethod.GET)
	public ModelAndView subtaskView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("settings/subtask", model);
	}
	
	@RequestMapping(value = "attachment.html", method = RequestMethod.GET)
	public ModelAndView attachmentView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("settings/attachment", model);
	}
	
	@RequestMapping(value = "timetracking.html", method = RequestMethod.GET)
	public ModelAndView timetrackingView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("settings/timetracking", model);
	}
	
	@RequestMapping(value = "globalpermission.html", method = RequestMethod.GET)
	public ModelAndView globalPermissionView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("settings/globalpermission", model);
	}
	
	@RequestMapping(value = "projectpermission.html", method = RequestMethod.GET)
	public ModelAndView projectPermissionView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("settings/projectpermission", model);
	}
	
	@RequestMapping(value = "user.html", method = RequestMethod.GET)
	public ModelAndView userView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("settings/user", model);
	}
	
	@RequestMapping(value = "usergroup.html", method = RequestMethod.GET)
	public ModelAndView usergroupView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("settings/usergroup", model);
	}
	
	@RequestMapping(value = "usersession.html", method = RequestMethod.GET)
	public ModelAndView usersessionView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("settings/usersession", model);
	}

}
