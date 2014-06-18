package mu.codeoffice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.SessionObject;
import mu.codeoffice.tag.AuthenticationUtils;
import mu.codeoffice.utility.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/administration/")
public class UserManagementController implements GenericController {

	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "userSessions.html", method = RequestMethod.GET)
	public ModelAndView get(@AuthenticationPrincipal EnterpriseAuthentication auth, 
		@RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex,
		@RequestParam(value = "pageSize", required = false, defaultValue = "30") Integer pageSize, 
		@RequestParam(value = "name", required = false) String name, ModelMap model) {
		int offset = (pageIndex == 0 || pageSize == 0) ? 0 :pageIndex * pageSize;
		Map<Long, SessionObject> sessionMap = (Map<Long, SessionObject>) servletContext.getAttribute(auth.getEnterprise().getCode() + "_SESSIONS");
		List<SessionObject> filtered = new ArrayList<>();
		int i = 0;
		for (Map.Entry<Long, SessionObject> entry : sessionMap.entrySet()) {
			boolean valid = (StringUtil.isEmptyString(name) 
					|| (entry.getValue().getAuth().getUser().getFirstName().contains(name) || 
							entry.getValue().getAuth().getUser().getLastName().contains(name)));
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
		if (pageIndex != 0) { model.put("pageIndex", pageIndex); }
		if (pageSize != 30) { model.put("pageSize", pageSize); }
		if (!StringUtil.isEmptyString(name)) { model.put("name", name); }
		return new ModelAndView("administration/um_userSessions", model);
	}
	
	@RequestMapping(value = "userSession/invalidate", method = RequestMethod.POST)
	public String invalidate(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam("user") Long user, RedirectAttributes redirectAttributes) {
		if (AuthenticationUtils.invalidateUser(auth.getEnterprise(), user, servletContext, sessionRegistry, true)) {
			redirectAttributes.addFlashAttribute(TIP, "User has been forced to logout.");
		} else {
			redirectAttributes.addFlashAttribute(ERROR, "User not found.");
		}
		return "redirect:/administration/userSessions.html";
	}
	
}
