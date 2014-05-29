package mu.codeoffice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.User;
import mu.codeoffice.entity.UserGroup;
import mu.codeoffice.entity.settings.AdvancedGlobalSettings;
import mu.codeoffice.entity.settings.Announcement;
import mu.codeoffice.entity.settings.GlobalSettings;
import mu.codeoffice.entity.settings.InternationalizationSettings;
import mu.codeoffice.enums.CommentVisibility;
import mu.codeoffice.enums.EmailVisibility;
import mu.codeoffice.json.UserGroupJSON;
import mu.codeoffice.json.UserJSON;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;
import mu.codeoffice.security.GlobalPermission;
import mu.codeoffice.security.Permission;
import mu.codeoffice.service.SystemSettingsService;
import mu.codeoffice.utility.GlobalPermissionEditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/administration/")
public class SystemAdministrationController implements PermissionRequired {

	@Autowired
	private SystemSettingsService systemSettingsService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ServletContext servletContext;
	
	@Override
	public void authorize(EnterpriseAuthentication auth, Object object,
			Permission... permissions) throws AuthenticationException {
		for (Permission permission : permissions) {
			if (!permission.isAuthorized(auth.getUser().getGlobalPermissionValue())) {
				throw new EnterpriseAuthenticationException(
						messageSource.getMessage("permission.denied_require_permission", new Object[]{ permission.getKey() }, LocaleContextHolder.getLocale()));
			}
		}
	}
	
	@RequestMapping(value = "system.html", method = RequestMethod.GET)
	public ModelAndView home(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		authorize(auth, null, GlobalPermission.ADMIN);
		return new ModelAndView("administration/system_home");
	}

	@RequestMapping(value = "global.html", method = RequestMethod.GET)
	public ModelAndView globalView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model)
		throws AuthenticationException {
		authorize(auth, null, GlobalPermission.ADMIN);
		model.put("globalSettings", systemSettingsService.getGlobalSettings(auth));
		return new ModelAndView("administration/system_global", model);
	}

	@RequestMapping(value = "global/edit.html", method = RequestMethod.GET)
	public ModelAndView globalEditRequest(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) 
		throws AuthenticationException {
		authorize(auth, null, GlobalPermission.ADMIN);
		model.put("globalSettings", systemSettingsService.getGlobalSettings(auth));
		model.put("commentVisibilities", CommentVisibility.values());
		model.put("emailVisibilities", EmailVisibility.values());
		return new ModelAndView("administration/system_global_form", model);
	}

	@RequestMapping(value = "global/edit", method = RequestMethod.POST)
	public String globalEdit(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@ModelAttribute("globalSettings") GlobalSettings globalSettings,
			RedirectAttributes redirectAttributes, ModelMap model) throws AuthenticationException {
		authorize(auth, null, GlobalPermission.ADMIN);
		try {
			systemSettingsService.update(auth, globalSettings);
			redirectAttributes.addFlashAttribute(TIP, "Global settings has been updated");
			return "redirect:/administration/global.html";
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
			return "redirect:/administration/global/edit.html";
		}
	}

	@RequestMapping(value = "advancedGlobal.html", method = RequestMethod.GET)
	public ModelAndView globalAdvancedView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		authorize(auth, null, GlobalPermission.ADMIN);
		model.put("advancedGlobalSettings", systemSettingsService.getAdvancedGlobalSettings(auth));
		return new ModelAndView("administration/system_advancedGlobal", model);
	}

	@RequestMapping(value = "advancedGlobal/edit.html", method = RequestMethod.GET)
	public ModelAndView advancedGlobalEditRequest(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		authorize(auth, null, GlobalPermission.ADMIN);
		model.put("advancedGlobalSettings", systemSettingsService.getAdvancedGlobalSettings(auth));
		return new ModelAndView("administration/system_advancedGlobal_form", model);
	}

	@RequestMapping(value = "advancedGlobal/edit", method = RequestMethod.POST)
	public String advancedGlobalEdit(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@ModelAttribute("advancedGlobalSettings") AdvancedGlobalSettings advancedGlobalSettings,
			RedirectAttributes redirectAttributes, ModelMap model) {
		try {
			systemSettingsService.update(auth, advancedGlobalSettings);
			redirectAttributes.addFlashAttribute(TIP, "Advanced global settings has been updated");
			return "redirect:/administration/advancedGlobal.html";
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
			return "redirect:/administration/advancedGlobal/edit.html";
		}
	}

	@RequestMapping(value = "internationalization.html", method = RequestMethod.GET)
	public ModelAndView internationalizationView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		authorize(auth, null, GlobalPermission.ADMIN);
		model.put("internationalizationSettings", systemSettingsService.getInternationalizationSettings(auth));
		return new ModelAndView("administration/system_internationalization", model);
	}

	@RequestMapping(value = "internationalization/edit.html", method = RequestMethod.GET)
	public ModelAndView internationalizationEditRequest(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		authorize(auth, null, GlobalPermission.ADMIN);
		model.put("internationalizationSettings", systemSettingsService.getInternationalizationSettings(auth));
		model.put("supportedLocale", InternationalizationSettings.SUPPORTED_LOCALE);
		model.put("supportedTimeZone", InternationalizationSettings.SUPPORTED_TIMEZONE);
		return new ModelAndView("administration/system_internationalization_form", model);
	}

	@RequestMapping(value = "internationalization/edit", method = RequestMethod.POST)
	public String internationalizationEdit(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@ModelAttribute("internationalizationSettings") InternationalizationSettings internationalizationSettings,
			RedirectAttributes redirectAttributes, ModelMap model) {
		authorize(auth, null, GlobalPermission.ADMIN);
		try {
			systemSettingsService.update(auth, internationalizationSettings);
			redirectAttributes.addFlashAttribute(TIP, "Internationalization settings has been updated");
			return "redirect:/administration/internationalization.html";
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
			return "redirect:/administration/internationalization/edit.html";
		}
	}

	@RequestMapping(value = "announcement.html", method = RequestMethod.GET)
	public ModelAndView announcementView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		authorize(auth, null, GlobalPermission.ADMIN);
		model.put("announcementSettings", systemSettingsService.getAnnouncement(auth));
		return new ModelAndView("administration/system_announcement", model);
	}

	@RequestMapping(value = "announcement/edit.html", method = RequestMethod.GET)
	public ModelAndView announcementEditRequest(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		authorize(auth, null, GlobalPermission.ADMIN);
		model.put("announcementSettings", systemSettingsService.getAnnouncement(auth));
		return new ModelAndView("administration/system_announcement_form", model);
	}

	@RequestMapping(value = "announcement/edit", method = RequestMethod.POST)
	public String announcementEdit(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@ModelAttribute("announcementSettings") Announcement announcement, 
			RedirectAttributes redirectAttributes, ModelMap model) {
		try {
			systemSettingsService.update(auth, announcement);
			redirectAttributes.addFlashAttribute(TIP, "Announcement has been updated");
			return "redirect:/administration/announcement.html";
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
			return "redirect:/administration/announcement/edit.html";
		}
	}
	
	@RequestMapping(value = "globalPermission.html", method = RequestMethod.GET)
	public ModelAndView globalPermissionView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		authorize(auth, null, GlobalPermission.SYSTEM_ADMIN);
		model.put("globalPermissionSettings", systemSettingsService.getGlobalPermissionSettings(auth));
		return new ModelAndView("administration/system_globalPermission", model);
	}
	
	@RequestMapping(value = "globalPermission/{globalPermission}/removeGroup", method = RequestMethod.GET)
	public String globalPermissionRemoveGroup(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@PathVariable("globalPermission") GlobalPermission globalPermission,
			@RequestParam("userGroupName") String userGroupName, 
			RedirectAttributes redirectAttributes, ModelMap model) {
		authorize(auth, null, GlobalPermission.SYSTEM_ADMIN);
		try {
			systemSettingsService.removeGroup(auth, globalPermission, userGroupName);
			redirectAttributes.addFlashAttribute(TIP, "Group '" + userGroupName + "' has been removed");
			return "redirect:/administration/globalPermission.html";
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
			return "redirect:/administration/globalPermission.html";
		}
	}
	
	@RequestMapping(value = "globalPermission/{globalPermission}/addGroup", method = RequestMethod.GET)
	public String globalPermissionAddGroup(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@PathVariable("globalPermission") GlobalPermission globalPermission,
			@RequestParam("userGroupName") String userGroupName, 
			RedirectAttributes redirectAttributes, ModelMap model) {
		authorize(auth, null, GlobalPermission.SYSTEM_ADMIN);
		try {
			systemSettingsService.addGroup(auth, globalPermission, userGroupName);
			redirectAttributes.addFlashAttribute(TIP, "Group '" + userGroupName + "' has been updated");
			return "redirect:/administration/globalPermission.html";
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
			return "redirect:/administration/globalPermission.html";
		}
	}
	
	@RequestMapping(value = "globalPermission/{globalPermission}/addUser", method = RequestMethod.GET)
	public String globalPermissionAddUser(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("id") Long id,  @PathVariable("globalPermission") GlobalPermission globalPermission,
			RedirectAttributes redirectAttributes, ModelMap model) {
		authorize(auth, null, GlobalPermission.SYSTEM_ADMIN);
		try {
			systemSettingsService.addUser(auth, globalPermission, id);
			redirectAttributes.addFlashAttribute(TIP, "Permission '" + globalPermission + "' has been updated");
			return "redirect:/administration/globalPermission.html";
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
			return "redirect:/administration/globalPermission.html";
		}
	}
		
	@RequestMapping(value = "globalPermission/{globalPermission}/removeUser", method = RequestMethod.GET)
	public String globalPermissionRemoveUser(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("id") Long id,  @PathVariable("globalPermission") GlobalPermission globalPermission,
			RedirectAttributes redirectAttributes, ModelMap model) {
		authorize(auth, null, GlobalPermission.SYSTEM_ADMIN);
		try {
			systemSettingsService.removeUser(auth, globalPermission, id);
			redirectAttributes.addFlashAttribute(TIP, "Permission '" + globalPermission + "' has been updated");
			return "redirect:/administration/globalPermission.html";
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
			return "redirect:/administration/globalPermission.html";
		}
	}
	
	@RequestMapping(value = "globalPermission/{globalPermission}/reset", method = RequestMethod.GET)
	public String globalPermissionReset(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@PathVariable("globalPermission") GlobalPermission globalPermission,
			RedirectAttributes redirectAttributes, ModelMap model) {
		authorize(auth, null, GlobalPermission.SYSTEM_ADMIN);
		try {
			systemSettingsService.reset(auth, globalPermission);
			redirectAttributes.addFlashAttribute(TIP, "Permission '" + globalPermission + "' has been reset.");
			return "redirect:/administration/globalPermission.html";
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
			return "redirect:/administration/globalPermission.html";
		}
	}
	
	@RequestMapping(value = "globalPermission/{globalPermission}/availableGroups", method = RequestMethod.GET)
	public @ResponseBody List<UserGroupJSON> getGlobalAvailableUserGroups(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@PathVariable("globalPermission") GlobalPermission globalPermission, ModelMap model) {
		authorize(auth, null, GlobalPermission.SYSTEM_ADMIN);
		List<UserGroup> userGroups = systemSettingsService.getGlobalAvailableUserGroups(auth, globalPermission);
		List<UserGroupJSON> jsonList = new ArrayList<>();
		for (UserGroup group : userGroups) {
			jsonList.add(group.toJSONObject());
		}
		return jsonList;
	}
	
	@RequestMapping(value = "globalPermission/{globalPermission}/availableUsers", method = RequestMethod.GET)
	public @ResponseBody List<UserJSON> getGlobalAvailableUsers(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@PathVariable("globalPermission") GlobalPermission globalPermission, 
			@RequestParam("search") String search, ModelMap model) {
		authorize(auth, null, GlobalPermission.SYSTEM_ADMIN);
		Page<User> users = systemSettingsService.getGlobalAvailableUsers(auth, globalPermission, search);
		List<UserJSON> jsonList = new ArrayList<>();
		for (User user : users.getContent()) {
			jsonList.add(user.toJSONObject());
		}
		return jsonList;
	}
	
	@RequestMapping(value = "sharedObjects.html", method = RequestMethod.GET)
	public ModelAndView shareObjectsView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/system_sharedObjects", model);
	}
	
	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(GlobalPermission.class, new GlobalPermissionEditor());
	}

}
