package mu.codeoffice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.SessionObject;
import mu.codeoffice.service.UserGroupService;
import mu.codeoffice.service.UserService;
import mu.codeoffice.utility.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class UserController implements GenericController {
	
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

	@RequestMapping(value = "administration/users.html", method = RequestMethod.GET)
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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "administration/userSessions.html", method = RequestMethod.GET)
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
