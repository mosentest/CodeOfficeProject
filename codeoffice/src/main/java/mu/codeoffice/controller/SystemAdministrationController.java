package mu.codeoffice.controller;

import javax.servlet.ServletContext;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.settings.GlobalSettings;
import mu.codeoffice.enums.CommentVisibility;
import mu.codeoffice.enums.EmailVisibility;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;
import mu.codeoffice.security.GlobalPermission;
import mu.codeoffice.security.Permission;
import mu.codeoffice.service.SystemSettingsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
			if (!permission.isAuthorized(auth.getEnterpriseUser().getGlobalPermissionValue())) {
				throw new EnterpriseAuthenticationException(
						messageSource.getMessage("permission.denied_require_permission", new Object[]{ permission.getKey() }, LocaleContextHolder.getLocale()));
			}
		}
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
		model.put("globalSettings", new GlobalSettings());
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
		return new ModelAndView("administration/system_advancedGlobal", model);
	}

	@RequestMapping(value = "internationalization.html", method = RequestMethod.GET)
	public ModelAndView internationalizationView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/system_internationalization", model);
	}

	@RequestMapping(value = "announcement.html", method = RequestMethod.GET)
	public ModelAndView announcementView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/system_announcement", model);
	}
	
	@RequestMapping(value = "globalPermission.html", method = RequestMethod.GET)
	public ModelAndView globalPermissionView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/system_globalpermission", model);
	}
	
	@RequestMapping(value = "projectPermission.html", method = RequestMethod.GET)
	public ModelAndView projectPermissionView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/system_projectPermission", model);
	}
	
	@RequestMapping(value = "sharedObjects.html", method = RequestMethod.GET)
	public ModelAndView shareObjectsView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/system_sharedObjects", model);
	}

}
