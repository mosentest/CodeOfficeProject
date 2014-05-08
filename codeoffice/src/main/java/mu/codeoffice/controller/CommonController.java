package mu.codeoffice.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import mu.codeoffice.common.ServiceResponse;
import mu.codeoffice.dto.UserDTO;
import mu.codeoffice.entity.User;
import mu.codeoffice.handler.ListHandler;
import mu.codeoffice.handler.SessionHandler;
import mu.codeoffice.service.CommonService;
import mu.codeoffice.service.UserService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller("commonController")
@RequestMapping("/")
public class CommonController {
	
	private CommonService commonService;
	private UserService userService;
	
	private static final Logger logger = Logger.getLogger(CommonController.class);
	
	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String test(HttpSession session) {
		return "test";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession session) {
		return "home";
	}
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String loginRequest(HttpSession session) {
		if (SessionHandler.getUser(session) != null) {
			return "";
		}
		return "login";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam(value = "account", required = true) String account, @RequestParam(value = "password", required = false) String password,
			HttpSession session) {
		if (SessionHandler.getUser(session) != null) {
			return new ModelAndView("redirect:/");
		}
		User user = null;
		user = commonService.login(account, password);
		if (user == null) {
			return new ModelAndView("login", "error", "Incorrect account/password");
		}
		session.setAttribute("currentUser", user);
		logger.debug(user.getAccount() + " has logged in.");
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		if (SessionHandler.getUser(session) == null)
			return "redirect:/";
		ServiceResponse response = commonService.logout(SessionHandler.getUid(session));
		if (response == ServiceResponse.SUCCESS) {
			User user = SessionHandler.getUser(session);
			logger.debug(user.getAccount() + " has logged out.");
			session.removeAttribute("currentUser");
		}
		return "redirect:/";
	}
	
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public ModelAndView registerRequest(HttpSession session) {
		if (SessionHandler.getUser(session) != null)
			return new ModelAndView("redirect:/");
		
		Map<String, Object> models = new HashMap<String, Object>();
		models.put("user", new UserDTO());
		models.put("securityQuestions", ListHandler.getSecurityQuestions());
		
		return new ModelAndView("register", models);
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public ModelAndView register(@ModelAttribute("user") @Valid UserDTO user, BindingResult errors, HttpSession session) {
		if (errors.hasErrors()) {
			return new ModelAndView("register", "user", user);
		}

		ServiceResponse response = userService.register(null);
		if (response == ServiceResponse.ERR_1) {
			errors.rejectValue("account", "AccountExist", "Account already exists");
		} else if (response == ServiceResponse.ERR_2) {
			errors.rejectValue("username", "UsernameExist", "Username already exists");
		} else if (response == ServiceResponse.ERROR) {
			errors.rejectValue("account", "ERROR_REGISTER", "Could not complete registration");
		} else {
			return new ModelAndView("login");
		}
		return new ModelAndView("register", "user", user);
	}
	
	@RequestMapping(value = "captcha", method = RequestMethod.GET)
	public String captcha() {
		return "";
	}

	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public UserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
