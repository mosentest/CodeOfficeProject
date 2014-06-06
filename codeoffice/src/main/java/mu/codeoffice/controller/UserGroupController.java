package mu.codeoffice.controller;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.dto.UserGroupDTO;
import mu.codeoffice.entity.UserGroup;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.service.UserGroupService;
import mu.codeoffice.service.UserService;
import mu.codeoffice.tag.Function;
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
@RequestMapping("/")
public class UserGroupController implements GenericController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserGroupService userGroupService;
	
	@Autowired
	private MessageSource messageSource;
	
	private static final int DEFAULT_LIST_SIZE = 20;
	
	private static final int[] LIST_SIZE = {
		20, 30, 50, 60, 80
	};

	@RequestMapping(value = "administration/userGroups.html", method = RequestMethod.GET)
	public ModelAndView userGroupView(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex, 
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "descending", required = false) boolean descending,
			ModelMap model) throws AuthenticationException {
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

	@RequestMapping(value = "administration/userGroup.html", method = RequestMethod.GET)
	public ModelAndView userGroup(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("group") String group,
			@RequestParam(value = "query", required = false) String query, 
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex, 
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "descending", required = false) boolean descending,
			RedirectAttributes redirectAttributes, ModelMap model) throws AuthenticationException {
		group = Function.unmaskURL(group);
		UserGroup userGroup = userGroupService.getUserGroup(auth, group);
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

	@RequestMapping(value = "administration/userGroup/create", method = RequestMethod.POST)
	public String userGroupCreate(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@ModelAttribute("usergroup") UserGroup userGroup, 
			RedirectAttributes redirectAttributes) throws AuthenticationException {
		try {
			userGroupService.createUserGroup(auth, userGroup);
			redirectAttributes.addFlashAttribute(TIP, "New User Group Added.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/userGroups.html";
	}

	@RequestMapping(value = "administration/userGroup/delete", method = RequestMethod.POST)
	public String userGroupDelete(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam("group") String group, RedirectAttributes redirectAttributes) throws AuthenticationException {
		try {
			group = Function.unmaskURL(group);
			userGroupService.deleteUserGroup(auth, group);
			redirectAttributes.addFlashAttribute(TIP, "User Group " + group + " has been deleted.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/userGroups.html";
	}

	@RequestMapping(value = "administration/userGroup/manage.html", method = RequestMethod.GET)
	public ModelAndView userGroupMemberRequest(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam("group") String group,
			@RequestParam(value = "query", required = false) String query, 
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex, 
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "descending", required = false) boolean descending,
			RedirectAttributes redirectAttributes, ModelMap model) throws AuthenticationException {
		group = Function.unmaskURL(group);
		UserGroup userGroup = userGroupService.getUserGroup(auth, group);
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

	@RequestMapping(value = "administration/userGroup/manage", method = RequestMethod.POST)
	public String userGroupMember(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam("group") String group,
			@ModelAttribute("userGroupDTO") UserGroupDTO userGroupDTO,
			RedirectAttributes redirectAttributes, ModelMap model) throws AuthenticationException {
		try {
			group = Function.unmaskURL(group);
			userGroupService.update(auth, group, userGroupDTO);
			redirectAttributes.addFlashAttribute(TIP, "User Group has been updated.");
			return "redirect:/administration/userGroups.html";
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(TIP, e.getMessage());
			return "redirect:/administration/userGroup/manage.html?group=" + group;
		}
	}
}
