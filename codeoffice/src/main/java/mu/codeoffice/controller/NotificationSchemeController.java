package mu.codeoffice.controller;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.settings.NotificationScheme;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.service.NotificationSchemeService;
import mu.codeoffice.service.ProjectRoleService;
import mu.codeoffice.service.UserGroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class NotificationSchemeController implements GenericController {

	@Autowired
	private NotificationSchemeService notificationSchemeService;
	
	@Autowired
	private ProjectRoleService projectRoleService;
	
	@Autowired
	private UserGroupService userGroupService;

	@RequestMapping(value = "administration/notificationSchemes.html", method = RequestMethod.GET)
	public ModelAndView get(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("notificationSchemes", notificationSchemeService.getNotificationSchemes(auth, true));
		model.put("notificationScheme", new NotificationScheme());
		return new ModelAndView("administration/project_notificationSchemes");
	}
	
	@RequestMapping(value = "administration/notificationScheme.html", method = RequestMethod.GET)
	public ModelAndView get(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam("scheme") String scheme, RedirectAttributes redirectAttributes, ModelMap model) {
		try {
			model.put("notificationScheme", notificationSchemeService.getNotificationScheme(auth, scheme, true));
			model.put("userGroups", userGroupService.getGroups(auth, false));
			model.put("projectRoles", projectRoleService.getProjectRoles(auth, false));
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
			return new ModelAndView("redirect:/administration/notificationSchemes.html");
		}
		return new ModelAndView("administration/project_notificationScheme");
	}
	
}
