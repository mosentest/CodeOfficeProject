package mu.codeoffice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.dto.UserGroupDTO;
import mu.codeoffice.entity.UserGroup;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.SessionObject;
import mu.codeoffice.service.UserGroupService;
import mu.codeoffice.service.UserManagementService;
import mu.codeoffice.service.UserService;
import mu.codeoffice.utility.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/administration/")
public class UserManagementAdminitrationController implements GenericController {

	@Autowired
	private UserManagementService userManagementService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserGroupService userGroupService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ServletContext servletContext;
	
	private static final int DEFAULT_LIST_SIZE = 20;
	
	private static final int[] LIST_SIZE = {
		20, 30, 50, 60, 80
	};
	
	@RequestMapping(value = "userManagement.html", method = RequestMethod.GET)
	public ModelAndView home(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/um_home");
	}

	@RequestMapping(value = "users.html", method = RequestMethod.GET)
	public ModelAndView usersView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model, 
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex, 
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
			@RequestParam(value = "groupFilter", required = false) Long group,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "account", required = false) String account, 
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "descending", required = false) boolean descending)
			throws AuthenticationException {
		model.put("userPage", userService.groupSearch(auth, group, account, name,
				pageIndex, pageSize, sort, !descending, true));
		model.put("supportedListSize", LIST_SIZE);
		model.put("groups", userGroupService.getGroups(auth, false));
		if (group != null) { model.put("groupFilter", group); }
		if (!StringUtil.isEmptyString(account)) { model.put("account", account); }
		if (!StringUtil.isEmptyString(name)) { model.put("name", name); }
		if (!StringUtil.isEmptyString(sort)) { model.put("sort", sort); }
		if (descending) { model.put("descending", descending); }
		if (pageIndex != 0) { model.put("pageIndex", pageIndex); }
		if (pageSize != DEFAULT_LIST_SIZE) { model.put("pageSize", pageSize); }
		return new ModelAndView("administration/um_users", model);
	}

	@RequestMapping(value = "userGroups.html", method = RequestMethod.GET)
	public ModelAndView userGroupView(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex, 
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "descending", required = false) boolean descending,
			ModelMap model)
			throws AuthenticationException {
		model.put("userGroupPage", userGroupService.getGroups(auth, name, pageIndex, pageSize, sort, !descending, true));
		model.put("supportedListSize", LIST_SIZE);
		model.put("userGroup", new UserGroup());
		if (!StringUtil.isEmptyString(name)) { model.put("name", name); }
		if (!StringUtil.isEmptyString(sort)) { model.put("sort", sort); }
		if (descending) { model.put("descending", descending); }
		if (pageIndex != 0) { model.put("pageIndex", pageIndex); }
		if (pageSize != DEFAULT_LIST_SIZE) { model.put("pageSize", pageSize); }
		return new ModelAndView("administration/um_userGroups", model);
	}

	@RequestMapping(value = "userGroup.html", method = RequestMethod.GET)
	public ModelAndView userGroup(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("group") String group,
			@RequestParam(value = "query", required = false) String query, 
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex, 
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "descending", required = false) boolean descending,
			RedirectAttributes redirectAttributes, ModelMap model)
			throws AuthenticationException {
		UserGroup userGroup = userManagementService.getUserGroup(auth, group);
		if (userGroup == null) {
			redirectAttributes.addFlashAttribute(ERROR, "User Group doesn't exist.");
			return new ModelAndView("redirect:/administration/userGroups.html");
		}
		model.put("userGroup", userGroup);
		model.put("userPage", userService.groupSearch(auth, userGroup.getId(), query, query, pageIndex, 20, sort, !descending, true));
		if (!StringUtil.isEmptyString(query)) { model.put("name", query); }
		if (!StringUtil.isEmptyString(sort)) { model.put("sort", sort); }
		if (descending) { model.put("descending", descending); }
		if (pageIndex != 0) { model.put("pageIndex", pageIndex); }
		return new ModelAndView("administration/um_userGroup", model);
	}

	@RequestMapping(value = "userGroup/create", method = RequestMethod.POST)
	public String userGroupCreate(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@ModelAttribute("usergroup") UserGroup userGroup, 
			RedirectAttributes redirectAttributes)
			throws AuthenticationException {
		try {
			userManagementService.createUserGroup(auth, userGroup);
			redirectAttributes.addFlashAttribute(TIP, "New User Group Added.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/userGroups.html";
	}

	@RequestMapping(value = "userGroup/delete", method = RequestMethod.POST)
	public String userGroupDelete(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam("group") String group,
			RedirectAttributes redirectAttributes)
			throws AuthenticationException {
		try {
			userManagementService.deleteUserGroup(auth, group);
			redirectAttributes.addFlashAttribute(TIP, "User Group " + group + " has been deleted.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/userGroups.html";
	}

	@RequestMapping(value = "userGroup/manage.html", method = RequestMethod.GET)
	public ModelAndView userGroupMemberRequest(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam("group") String group,
			@RequestParam(value = "query", required = false) String query, 
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex, 
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "descending", required = false) boolean descending,
			RedirectAttributes redirectAttributes, ModelMap model)
			throws AuthenticationException {
		UserGroup userGroup = userManagementService.getUserGroup(auth, group);
		if (userGroup == null) {
			redirectAttributes.addFlashAttribute(ERROR, "User Group doesn't exist.");
			return new ModelAndView("redirect:/administration/userGroups.html");
		}
		model.put("userGroup", new UserGroupDTO().toDTO(userGroup));
		model.put("userPage", userService.groupSearch(auth, userGroup.getId(), query, query, pageIndex, DEFAULT_LIST_SIZE, sort, !descending, true));
		if (!StringUtil.isEmptyString(query)) { model.put("name", query); }
		if (!StringUtil.isEmptyString(sort)) { model.put("sort", sort); }
		if (descending) { model.put("descending", descending); }
		if (pageIndex != 0) { model.put("pageIndex", pageIndex); }
		return new ModelAndView("administration/um_userGroup_member", model);
	}

	@RequestMapping(value = "userGroup/manage", method = RequestMethod.POST)
	public String userGroupMember(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam("group") String group,
			@ModelAttribute("userGroupDTO") UserGroupDTO userGroupDTO,
			RedirectAttributes redirectAttributes, ModelMap model)
			throws AuthenticationException {
		try {
			userManagementService.update(auth, group, userGroupDTO);
			redirectAttributes.addFlashAttribute(TIP, "User Group has been updated.");
			return "redirect:/administration/userGroups.html";
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(TIP, e.getMessage());
			return "redirect:/administration/userGroup/manage.html?group=" + group;
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
