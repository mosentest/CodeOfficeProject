package mu.codeoffice.controller;

import java.util.List;
import java.util.stream.Collectors;

import mu.codeoffice.json.UserJSON;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.service.UserGroupService;
import mu.codeoffice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ajax/")
public class CommonAjaxController implements GenericController {

	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "search/users", method = RequestMethod.GET)
	public @ResponseBody List<UserJSON> search(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("query") String query) {
		return userService.basicSearch(auth, query).getContent()
				.stream()
				.map(user -> user.toJSONObject())
				.collect(Collectors.toList());
	}
	
}
