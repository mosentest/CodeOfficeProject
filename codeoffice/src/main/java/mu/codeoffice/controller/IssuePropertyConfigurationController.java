package mu.codeoffice.controller;

import mu.codeoffice.entity.settings.IssueLinkType;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.service.IssuePropertyConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/administration/")
public class IssuePropertyConfigurationController {

	@Autowired
	private IssuePropertyConfigurationService issuePropertyConfigurationService;
	
	@RequestMapping(value = "issueTypes.html", method = RequestMethod.GET)
	public ModelAndView issueTypeView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("issueTypes", issuePropertyConfigurationService.getIssueTypes(auth));
		return new ModelAndView("administration/issue_issueTypes", model);
	}

	@RequestMapping(value = "issueTypeSchemes.html", method = RequestMethod.GET)
	public ModelAndView issueTypeSchemeView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("issueTypeSchemes", issuePropertyConfigurationService.getIssueTypeSchemes(auth));
		return new ModelAndView("administration/issue_issueTypeSchemes", model);
	}

	@RequestMapping(value = "subtasks.html", method = RequestMethod.GET)
	public ModelAndView subtaskView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("issueTypes", issuePropertyConfigurationService.getSubTaskTypes(auth));
		return new ModelAndView("administration/issue_subtasks", model);
	}

	@RequestMapping(value = "issueLinks.html", method = RequestMethod.GET)
	public ModelAndView issueLinkingView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("issueLinks", issuePropertyConfigurationService.getIssueLinkTypes(auth));
		model.put("issueLink", new IssueLinkType());
		return new ModelAndView("administration/issue_issueLinks", model);
	}
	

	@RequestMapping(value = "status.html", method = RequestMethod.GET)
	public ModelAndView statusView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/issue_status", model);
	}

	@RequestMapping(value = "resolution.html", method = RequestMethod.GET)
	public ModelAndView resolutionView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/issue_resolution", model);
	}
	
	@RequestMapping(value = "priority.html", method = RequestMethod.GET)
	public ModelAndView priorityView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		return new ModelAndView("administration/issue_priority", model);
	}
	
}
