package mu.codeoffice.controller;

import javax.validation.Valid;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.settings.IssueLink;
import mu.codeoffice.entity.settings.IssuePriority;
import mu.codeoffice.entity.settings.IssueResolution;
import mu.codeoffice.entity.settings.IssueStatus;
import mu.codeoffice.entity.settings.IssueType;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.service.IssuePropertyConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/administration/")
public class IssuePropertyConfigurationController implements GenericController {

	@Autowired
	private IssuePropertyConfigurationService issuePropertyConfigurationService;
	
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "type/create", method = RequestMethod.POST)
	public String typeCreate(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@ModelAttribute("issueType") @Valid IssueType issueType, BindingResult result, 
			RedirectAttributes redirectAttributes, ModelMap model) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("formErrors", initErrorMessages(result.getAllErrors(), messageSource));
		} else {
			try {
				issueType.setStandard(true);
				issuePropertyConfigurationService.create(auth, issueType);
				redirectAttributes.addFlashAttribute(TIP, "Issue Type has been created.");
			} catch (InformationException e) {
				redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
			}
		}
		return "redirect:/administration/types.html";
	}

	@RequestMapping(value = "subtask/create", method = RequestMethod.POST)
	public String subtaskCreate(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@ModelAttribute("issueType") @Valid IssueType issueType, BindingResult result, 
			RedirectAttributes redirectAttributes, ModelMap model) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("formErrors", initErrorMessages(result.getAllErrors(), messageSource));
		} else {
			try {
				issueType.setStandard(false);
				issuePropertyConfigurationService.create(auth, issueType);
				redirectAttributes.addFlashAttribute(TIP, "Sub task has been created.");
			} catch (InformationException e) {
				redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
			}
		}
		return "redirect:/administration/types.html";
	}

	@RequestMapping(value = "status/create", method = RequestMethod.POST)
	public String statusCreate(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@ModelAttribute("issueStatus") @Valid IssueStatus issueStatus, BindingResult result, 
			RedirectAttributes redirectAttributes, ModelMap model) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("formErrors", initErrorMessages(result.getAllErrors(), messageSource));
		} else {
			try {
				issuePropertyConfigurationService.create(auth, issueStatus);
				redirectAttributes.addFlashAttribute(TIP, "Issue Status has been created.");
			} catch (InformationException e) {
				redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
			}
		}
		return "redirect:/administration/status.html";
	}

	@RequestMapping(value = "priority/create", method = RequestMethod.POST)
	public String priorityCreate(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@ModelAttribute("issuePriority") @Valid IssuePriority issuePriority, BindingResult result, 
			RedirectAttributes redirectAttributes, ModelMap model) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("formErrors", initErrorMessages(result.getAllErrors(), messageSource));
		} else {
			try {
				issuePropertyConfigurationService.create(auth, issuePriority);
				redirectAttributes.addFlashAttribute(TIP, "Issue Priority has been created.");
			} catch (InformationException e) {
				redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
			}
		}
		return "redirect:/administration/priorities.html";
	}

	@RequestMapping(value = "resolution/create", method = RequestMethod.POST)
	public String resolutionCreate(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@ModelAttribute("issueResolution") @Valid IssueResolution issueResolution, BindingResult result, 
			RedirectAttributes redirectAttributes, ModelMap model) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("formErrors", initErrorMessages(result.getAllErrors(), messageSource));
		} else {
			try {
				issuePropertyConfigurationService.create(auth, issueResolution);
				redirectAttributes.addFlashAttribute(TIP, "Issue Resolution has been created.");
			} catch (InformationException e) {
				redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
			}
		}
		return "redirect:/administration/resolutions.html";
	}

	@RequestMapping(value = "link/create", method = RequestMethod.POST)
	public String linkCreate(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@ModelAttribute("issueLink") @Valid IssueLink issueLink, BindingResult result, 
			RedirectAttributes redirectAttributes, ModelMap model) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("formErrors", initErrorMessages(result.getAllErrors(), messageSource));
		} else {
			try {
				issuePropertyConfigurationService.create(auth, issueLink);
				redirectAttributes.addFlashAttribute(TIP, "Issue Link has been created.");
			} catch (InformationException e) {
				redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
			}
		}
		return "redirect:/administration/links.html";
	}

	@RequestMapping(value = "type/edit.html", method = RequestMethod.GET)
	public ModelAndView typeEditRequest(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@PathVariable("type") String type, ModelMap model) {
		model.put("issueType", issuePropertyConfigurationService.getIssueType(auth, type));
		return new ModelAndView("administration/issue_type_form", model);
	}

	@RequestMapping(value = "link/edit.html", method = RequestMethod.GET)
	public ModelAndView issueLinkEditRequest(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("link") String link, ModelMap model) {
		model.put("issueLink", issuePropertyConfigurationService.getIssueLink(auth, link));
		return new ModelAndView("administration/issue_link_form", model);
	}
	

	@RequestMapping(value = "status/edit.html", method = RequestMethod.GET)
	public ModelAndView statusEditRequest(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam("status") String status, ModelMap model) {
		model.put("issueStatus", issuePropertyConfigurationService.getIssueStatus(auth, status));
		return new ModelAndView("administration/issue_status_form", model);
	}

	@RequestMapping(value = "resolution/edit.html", method = RequestMethod.GET)
	public ModelAndView resolutionEditRequest(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam("resolution") String resolution, ModelMap model) {
		model.put("issueResolution", issuePropertyConfigurationService.getIssueResolution(auth, resolution));
		return new ModelAndView("administration/issue_resolution_form", model);
	}
	
	@RequestMapping(value = "priority/edit.html", method = RequestMethod.GET)
	public ModelAndView priorityEditRequest(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam("priority") String priority, ModelMap model) {
		model.put("issuePriority", issuePropertyConfigurationService.getIssuePriority(auth, priority));
		return new ModelAndView("administration/issue_priority_form", model);
	}
	
	@RequestMapping(value = "types.html", method = RequestMethod.GET)
	public ModelAndView typeView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("issueTypes", issuePropertyConfigurationService.getIssueTypes(auth));
		model.put("issueType", new IssueType());
		model.put("icons", IssueType.ICONS);
		return new ModelAndView("administration/issue_types", model);
	}

	@RequestMapping(value = "subtasks.html", method = RequestMethod.GET)
	public ModelAndView subtaskView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("issueTypes", issuePropertyConfigurationService.getSubTaskTypes(auth));
		model.put("issueType", new IssueType());
		model.put("icons", IssueType.ICONS);
		return new ModelAndView("administration/issue_subtasks", model);
	}

	@RequestMapping(value = "typeSchemes.html", method = RequestMethod.GET)
	public ModelAndView typeSchemeView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("issueTypeSchemes", issuePropertyConfigurationService.getIssueTypeSchemes(auth));
		return new ModelAndView("administration/issue_typeSchemes", model);
	}

	@RequestMapping(value = "links.html", method = RequestMethod.GET)
	public ModelAndView linksView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("issueLinks", issuePropertyConfigurationService.getIssueLinks(auth));
		model.put("issueLink", new IssueLink());
		return new ModelAndView("administration/issue_links", model);
	}
	
	@RequestMapping(value = "status.html", method = RequestMethod.GET)
	public ModelAndView statusView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("issueStatuses", issuePropertyConfigurationService.getIssueStatus(auth));
		model.put("issueStatus", new IssueStatus());
		model.put("icons", IssueStatus.ICONS);
		return new ModelAndView("administration/issue_status", model);
	}

	@RequestMapping(value = "resolutions.html", method = RequestMethod.GET)
	public ModelAndView resolutionView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("issueResolutions", issuePropertyConfigurationService.getIssueResolutions(auth));
		model.put("issueResolution", new IssueResolution());
		return new ModelAndView("administration/issue_resolutions", model);
	}
	
	@RequestMapping(value = "priorities.html", method = RequestMethod.GET)
	public ModelAndView priorityView(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("issuePriorities", issuePropertyConfigurationService.getIssuePriorities(auth));
		model.put("issuePriority", new IssuePriority());
		model.put("icons", IssuePriority.ICONS);
		return new ModelAndView("administration/issue_priorities", model);
	}
	
}
