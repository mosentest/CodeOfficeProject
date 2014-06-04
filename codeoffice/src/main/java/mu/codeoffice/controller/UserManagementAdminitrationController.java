package mu.codeoffice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.dto.UserGroupDTO;
import mu.codeoffice.entity.UserGroup;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;
import mu.codeoffice.security.GlobalPermission;
import mu.codeoffice.security.Permission;
import mu.codeoffice.security.SessionObject;
import mu.codeoffice.service.UserManagementService;
import mu.codeoffice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/administration/")
public class UserManagementAdminitrationController implements PermissionRequired {

	@Autowired
	private UserManagementService userManagementService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ServletContext servletContext;
	
	private static final int DEFAULT_LIST_SIZE = 20;
	
	private static final int[] LIST_SIZE = {
		20, 30, 50, 60, 80
	};
	
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
	
	@RequestMapping(value = "userManagement.html", method = RequestMethod.GET)
	public ModelAndView home(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		authorize(auth, null, GlobalPermission.BROWSE_USER);
		return new ModelAndView("administration/um_home");
	}

	@RequestMapping(value = "users.html", method = RequestMethod.GET)
	public ModelAndView usersView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model, 
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex, 
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "groupFilter", required = false) Long group,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "account", required = false) String account, 
			@RequestParam(value = "name", required = false) String name)
			throws AuthenticationException {
		authorize(auth, null, GlobalPermission.BROWSE_USER);
		model.put("userPage", userService.groupSearch(auth, group, account, name,
				pageIndex, DEFAULT_LIST_SIZE, "firstName", true));
		model.put("supportedListSize", LIST_SIZE);
		model.put("groups", userManagementService.getUserGroups(auth));
		if (pageIndex != null) { model.put("pageIndex", pageIndex); }
		if (pageSize != null) { model.put("pageSize", pageSize); }
		if (group != null) { model.put("groupFilter", group); }
		if (account != null) { model.put("account", account); }
		if (name != null) { model.put("name", name); }
		return new ModelAndView("administration/um_users", model);
	}

	@RequestMapping(value = "userGroups.html", method = RequestMethod.GET)
	public ModelAndView userGroupView(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex, 
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "sort", required = false) String sort,
			ModelMap model)
			throws AuthenticationException {
		authorize(auth, null, GlobalPermission.BROWSE_USER);
		model.put("userGroupPage", userManagementService.filterUserGroups(auth, name, pageIndex, pageSize == null ? DEFAULT_LIST_SIZE : pageSize, sort));
		model.put("supportedListSize", LIST_SIZE);
		model.put("userGroup", new UserGroup());
		model.put("groups", userManagementService.getUserGroups(auth));
		if (pageIndex != null) { model.put("pageIndex", pageIndex); }
		if (pageSize != null) { model.put("pageSize", pageSize); }
		if (name != null) { model.put("name", name); }
		return new ModelAndView("administration/um_userGroups", model);
	}

	@RequestMapping(value = "userGroup/{group}.html", method = RequestMethod.GET)
	public ModelAndView userGroup(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@PathVariable("group") String group,
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex, 
			ModelMap model)
			throws AuthenticationException {
		authorize(auth, null, GlobalPermission.BROWSE_USER);
		UserGroup userGroup = userManagementService.getUserGroup(auth, group);
		model.put("userGroup", userGroup);
		model.put("userPage", userService.groupSearch(auth, userGroup.getId(), null, null,
				pageIndex, DEFAULT_LIST_SIZE, "firstName", true));
		if (pageIndex != null) { model.put("pageIndex", pageIndex); }
		return new ModelAndView("administration/um_userGroup", model);
	}

	@RequestMapping(value = "userGroup/create", method = RequestMethod.POST)
	public String userGroupCreate(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@ModelAttribute("usergroup") UserGroup userGroup, 
			RedirectAttributes redirectAttributes)
			throws AuthenticationException {
		authorize(auth, null, GlobalPermission.ADMIN);
		try {
			userManagementService.createUserGroup(auth, userGroup);
			redirectAttributes.addFlashAttribute(TIP, "New User Group Added.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
		}
		return "redirect:/administration/userGroups.html";
	}

	@RequestMapping(value = "userGroup/{userGroupName}/delete", method = RequestMethod.POST)
	public String userGroupDelete(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@PathVariable("userGroupName") String userGroupName,
			RedirectAttributes redirectAttributes)
			throws AuthenticationException {
		authorize(auth, null, GlobalPermission.ADMIN);
		try {
			userManagementService.deleteUserGroup(auth, userGroupName);
			redirectAttributes.addFlashAttribute(TIP, "User Group " + userGroupName + " has been deleted.");
		} catch (AuthenticationException e) {
			throw e;
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
		}
		return "redirect:/administration/userGroups.html";
	}

	@RequestMapping(value = "userGroup/manage/{userGroupName}.html", method = RequestMethod.GET)
	public ModelAndView userGroupMemberRequest(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@PathVariable("userGroupName") String userGroupName,
			RedirectAttributes redirectAttributes, ModelMap model)
			throws AuthenticationException {
		authorize(auth, null, GlobalPermission.SYSTEM_ADMIN);
		UserGroup userGroup = userManagementService.getUserGroup(auth, userGroupName);
		model.put("userGroupDTO", new UserGroupDTO().toDTO(userGroup));
		return new ModelAndView("administration/um_userGroup_member", model);
	}

	@RequestMapping(value = "userGroup/manage/{userGroupName}", method = RequestMethod.POST)
	public String userGroupMember(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@PathVariable("userGroupName") String userGroupName,
			@ModelAttribute("userGroupDTO") UserGroupDTO userGroupDTO,
			RedirectAttributes redirectAttributes, ModelMap model)
			throws AuthenticationException {
		authorize(auth, null, GlobalPermission.SYSTEM_ADMIN);
		try {
			userManagementService.update(auth, userGroupName, userGroupDTO);
			redirectAttributes.addFlashAttribute(TIP, "User Group has been updated.");
			return "redirect:/administration/userGroups.html";
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(TIP, e.getMessage());
			return "redirect:/administration/userGroup/manage" + userGroupName + ".html";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "userSessions.html", method = RequestMethod.GET)
	public ModelAndView userSessionView(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex,
			@RequestParam(value = "pageSize", required = false, defaultValue = "30") Integer pageSize, 
			@RequestParam(value = "name", required = false) String name, 
			ModelMap model)
			throws AuthenticationException {
		authorize(auth, null, GlobalPermission.SYSTEM_ADMIN);
		int offset = (pageIndex == 0 || pageSize == 0) ? 0 :pageIndex * pageSize;
		Map<Long, SessionObject> sessionMap = (Map<Long, SessionObject>) servletContext.getAttribute(auth.getEnterprise().getCode() + "_SESSIONS");
		List<SessionObject> filtered = new ArrayList<>();
		int i = 0;
		for (Map.Entry<Long, SessionObject> entry : sessionMap.entrySet()) {
			boolean valid = (name == null || entry.getValue().getUser().getFullName().contains(name));
			if (valid) {
				if (i >= offset) {
					filtered.add(entry.getValue());
					if (filtered.size() == pageSize) {
						break;
					}
				}
				i++;
			}
		}
		model.put("userSessions", filtered);
		return new ModelAndView("administration/um_userSessions", model);
	}

}
