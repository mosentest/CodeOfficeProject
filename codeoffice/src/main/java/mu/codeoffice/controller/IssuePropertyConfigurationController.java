package mu.codeoffice.controller;

import mu.codeoffice.entity.settings.IssueLinkType;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.service.IssuePropertyConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/administration/")
public class IssuePropertyConfigurationController {

	@Autowired
	private IssuePropertyConfigurationService issuePropertyConfigurationService;

	@RequestMapping(value = "type/{typeName}.html", method = RequestMethod.GET)
	public ModelAndView typeEditRequest(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@PathVariable("typeName") String typeName, ModelMap model) {
		model.put("issueType", issuePropertyConfigurationService.getIssueType(auth, typeName));
		return new ModelAndView("administration/issue_type", model);
	}

	@RequestMapping(value = "link/{linkName}.html", method = RequestMethod.GET)
	public ModelAndView issueLinkEditRequest(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@PathVariable("linkName") String linkName, ModelMap model) {
		model.put("issueLink", issuePropertyConfigurationService.getIssueLinkType(auth, linkName));
		return new ModelAndView("administration/issue_link", model);
	}
	

	@RequestMapping(value = "status/{statusName}.html", method = RequestMethod.GET)
	public ModelAndView statusEditRequest(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@PathVariable("statusName") String statusName, ModelMap model) {
		model.put("issueStatus", issuePropertyConfigurationService.getIssueStatus(auth, statusName));
		return new ModelAndView("administration/issue_status_single", model);
	}

	@RequestMapping(value = "resolution/{resolutionName}.html", method = RequestMethod.GET)
	public ModelAndView resolutionEditRequest(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@PathVariable("resolutionName") String resolutionName, ModelMap model) {
		model.put("issueResolution", issuePropertyConfigurationService.getIssueResolution(auth, resolutionName));
		return new ModelAndView("administration/issue_resolution", model);
	}
	
	@RequestMapping(value = "priority/{priorityName}.html", method = RequestMethod.GET)
	public ModelAndView priorityEditRequest(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@PathVariable("priorityName") String priorityName, ModelMap model) {
		model.put("issuePriority", issuePropertyConfigurationService.getIssuePriority(auth, priorityName));
		return new ModelAndView("administration/issue_priority", model);
	}
	
	@RequestMapping(value = "types.html", method = RequestMethod.GET)
	public ModelAndView typeView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("issueTypes", issuePropertyConfigurationService.getIssueTypes(auth));
		return new ModelAndView("administration/issue_types", model);
	}

	@RequestMapping(value = "typeSchemes.html", method = RequestMethod.GET)
	public ModelAndView typeSchemeView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("issueTypeSchemes", issuePropertyConfigurationService.getIssueTypeSchemes(auth));
		return new ModelAndView("administration/issue_typeSchemes", model);
	}

	@RequestMapping(value = "subtasks.html", method = RequestMethod.GET)
	public ModelAndView subtaskView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("issueTypes", issuePropertyConfigurationService.getSubTaskTypes(auth));
		return new ModelAndView("administration/issue_subtasks", model);
	}

	@RequestMapping(value = "links.html", method = RequestMethod.GET)
	public ModelAndView linksView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("issueLinks", issuePropertyConfigurationService.getIssueLinkTypes(auth));
		model.put("issueLink", new IssueLinkType());
		return new ModelAndView("administration/issue_links", model);
	}
	

	@RequestMapping(value = "status.html", method = RequestMethod.GET)
	public ModelAndView statusView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("issueStatus", issuePropertyConfigurationService.getIssueStatus(auth));
		return new ModelAndView("administration/issue_status", model);
	}

	@RequestMapping(value = "resolutions.html", method = RequestMethod.GET)
	public ModelAndView resolutionView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("issueResolutions", issuePropertyConfigurationService.getIssueResolutions(auth));
		return new ModelAndView("administration/issue_resolutions", model);
	}
	
	@RequestMapping(value = "priorities.html", method = RequestMethod.GET)
	public ModelAndView priorityView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("issuePriorities", issuePropertyConfigurationService.getIssuePriorities(auth));
		return new ModelAndView("administration/issue_priorities", model);
	}
	
}
