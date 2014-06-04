package mu.codeoffice.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.settings.IssueLink;
import mu.codeoffice.entity.settings.IssuePriority;
import mu.codeoffice.entity.settings.IssueResolution;
import mu.codeoffice.entity.settings.IssueStatus;
import mu.codeoffice.entity.settings.IssueType;
import mu.codeoffice.entity.settings.IssueTypeScheme;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.service.IssuePropertyConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	@RequestMapping(value = "typeScheme/clone", method = RequestMethod.GET)
	public String typeSchemeClone(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("scheme") String scheme, RedirectAttributes redirectAttributes) {
		try {
			issuePropertyConfigurationService.cloneIssueTypeScheme(auth, scheme);
			redirectAttributes.addFlashAttribute(TIP, "Issue Type Scheme '" + scheme + "' has been cloned.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
		}
		return "redirect:/administration/typeSchemes.html";
	}

	@RequestMapping(value = "typeScheme/associate.html", method = RequestMethod.GET)
	public ModelAndView typeSchemeAssociateRequest(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("scheme") String scheme, 
			RedirectAttributes redirectAttributes, ModelMap model) {
		IssueTypeScheme issueTypeScheme = issuePropertyConfigurationService.getIssueTypeScheme(auth, scheme);
		if (issueTypeScheme == null) {
			redirectAttributes.addFlashAttribute(ERROR, "Issue Type Scheme doesn't exist.");
			return new ModelAndView("redirect:/administration/typeSchemes.html");
		}
		model.put("issueTypeScheme", issueTypeScheme);
		return new ModelAndView("administration/typeScheme/associate");
	}

	@RequestMapping(value = "typeScheme/associate", method = RequestMethod.POST)
	public String typeSchemeAssociate(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("scheme") String scheme, RedirectAttributes redirectAttributes) {
		try {
			issuePropertyConfigurationService.deleteIssueTypeScheme(auth, scheme);
			redirectAttributes.addFlashAttribute(TIP, "Projects has been added.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
		}
		return "redirect:/administration/typeSchemes.html";
	}

	@RequestMapping(value = "typeScheme/delete", method = RequestMethod.POST)
	public String typeSchemeDelete(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("scheme") String scheme, RedirectAttributes redirectAttributes) {
		try {
			issuePropertyConfigurationService.deleteIssueTypeScheme(auth, scheme);
			redirectAttributes.addFlashAttribute(TIP, "Issue Type Scheme has been deleted.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
		}
		return "redirect:/administration/typeSchemes.html";
	}

	@RequestMapping(value = "type/delete", method = RequestMethod.POST)
	public String typeDelete(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("type") String type, RedirectAttributes redirectAttributes) {
		try {
			issuePropertyConfigurationService.deleteIssueType(auth, type, true);
			redirectAttributes.addFlashAttribute(TIP, "Issue Type has been deleted.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
		}
		return "redirect:/administration/types.html";
	}

	@RequestMapping(value = "subtask/delete", method = RequestMethod.POST)
	public String subtaskDelete(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("type") String type, RedirectAttributes redirectAttributes) {
		try {
			issuePropertyConfigurationService.deleteIssueType(auth, type, false);
			redirectAttributes.addFlashAttribute(TIP, "Sub task type has been deleted.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
		}
		return "redirect:/administration/subtasks.html";
	}

	@RequestMapping(value = "status/delete", method = RequestMethod.POST)
	public String statusDelete(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("status") String status, RedirectAttributes redirectAttributes) {
		try {
			issuePropertyConfigurationService.deleteIssueStatus(auth, status);
			redirectAttributes.addFlashAttribute(TIP, "Issue Status has been deleted.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
		}
		return "redirect:/administration/status.html";
	}

	@RequestMapping(value = "priority/delete", method = RequestMethod.POST)
	public String priorityDelete(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("priority") String priority, RedirectAttributes redirectAttributes) {
		try {
			issuePropertyConfigurationService.deleteIssuePriority(auth, priority);
			redirectAttributes.addFlashAttribute(TIP, "Issue Type has been deleted.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
		}
		return "redirect:/administration/priorities.html";
	}

	@RequestMapping(value = "resolution/delete", method = RequestMethod.POST)
	public String resolutionDelete(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("resolution") String resolution, RedirectAttributes redirectAttributes) {
		try {
			issuePropertyConfigurationService.deleteIssueResolution(auth, resolution);
			redirectAttributes.addFlashAttribute(TIP, "Issue Resolution has been deleted.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
		}
		return "redirect:/administration/resolutions.html";
	}

	@RequestMapping(value = "link/delete", method = RequestMethod.POST)
	public String linkCDelete(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("link") String link, RedirectAttributes redirectAttributes) {
		try {
			issuePropertyConfigurationService.deleteIssueLink(auth, link);
			redirectAttributes.addFlashAttribute(TIP, "Issue Link has been deleted.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
		}
		return "redirect:/administration/links.html";
	}

	@RequestMapping(value = "typeScheme/create.html", method = RequestMethod.GET)
	public ModelAndView typeSchemeCreateRequest(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("issueTypeScheme", new IssueTypeScheme());
		model.put("issueTypes", issuePropertyConfigurationService.getIssueTypes(auth));
		return new ModelAndView("administration/issue_typeScheme_form", model);
	}
	
	@RequestMapping(value = "typeScheme/create", method = RequestMethod.POST)
	public String typeSchemeCreate(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@ModelAttribute("issueTypeScheme") @Valid IssueTypeScheme issueTypeScheme, BindingResult result, 
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("formErrors", initErrorMessages(result.getAllErrors(), messageSource));
		} else {
			try {
				issuePropertyConfigurationService.create(auth, issueTypeScheme);
				redirectAttributes.addFlashAttribute(TIP, "Issue Type Scheme has been created.");
				return "redirect:/administration/typeSchemes.html";
			} catch (InformationException e) {
				redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
			}
		}
		return "redirect:/administration/typeScheme/create.html";
	}
	
	@RequestMapping(value = "type/create", method = RequestMethod.POST)
	public String typeCreate(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@ModelAttribute("issueType") @Valid IssueType issueType, BindingResult result, 
			RedirectAttributes redirectAttributes) {
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
			RedirectAttributes redirectAttributes) {
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
		return "redirect:/administration/subtasks.html";
	}

	@RequestMapping(value = "status/create", method = RequestMethod.POST)
	public String statusCreate(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@ModelAttribute("issueStatus") @Valid IssueStatus issueStatus, BindingResult result, 
			RedirectAttributes redirectAttributes) {
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
			RedirectAttributes redirectAttributes) {
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
			RedirectAttributes redirectAttributes) {
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
			RedirectAttributes redirectAttributes) {
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

	@RequestMapping(value = "typeScheme/edit.html", method = RequestMethod.GET)
	public ModelAndView typeSchemeEditRequest(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("scheme") String scheme, RedirectAttributes redirectAttributes, ModelMap model) {
		IssueTypeScheme typeScheme = issuePropertyConfigurationService.getIssueTypeScheme(auth, scheme);
		model.put("issueTypeScheme", typeScheme);
		List<IssueType> issueTypes = issuePropertyConfigurationService.getIssueTypes(auth)
				.stream()
				.filter(t -> !typeScheme.getIssueTypes().contains(t))
				.collect(Collectors.toList()); 
		model.put("issueTypes", issueTypes);
		return new ModelAndView("administration/issue_typeScheme_form", model);
	}

	@RequestMapping(value = "type/edit.html", method = RequestMethod.GET)
	public ModelAndView typeEditRequest(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("type") String type, RedirectAttributes redirectAttributes, ModelMap model) {
		IssueType issueType = issuePropertyConfigurationService.getIssueType(auth, type, true);
		if (issueType == null) {
			redirectAttributes.addFlashAttribute(ERROR, "Issue Type no found.");
			return new ModelAndView("redirect:/administration/types.html");
		}
		model.put("issueType", issueType);
		model.put("icons", IssueType.ICONS);
		return new ModelAndView("administration/issue_type_form", model);
	}

	@RequestMapping(value = "subtask/edit.html", method = RequestMethod.GET)
	public ModelAndView subtaskEditRequest(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("type") String type, RedirectAttributes redirectAttributes, ModelMap model) {
		IssueType issueType = issuePropertyConfigurationService.getIssueType(auth, type, false);
		if (issueType == null) {
			redirectAttributes.addFlashAttribute(ERROR, "Sub task no found.");
			return new ModelAndView("redirect:/administration/subtasks.html");
		}
		model.put("issueType", issueType);
		model.put("icons", IssueType.ICONS);
		return new ModelAndView("administration/issue_type_form", model);
	}

	@RequestMapping(value = "link/edit.html", method = RequestMethod.GET)
	public ModelAndView issueLinkEditRequest(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("link") String link, RedirectAttributes redirectAttributes, ModelMap model) {
		IssueLink issueLink = issuePropertyConfigurationService.getIssueLink(auth, link);
		if (issueLink == null) {
			redirectAttributes.addFlashAttribute(ERROR, "Issue Link no found.");
			return new ModelAndView("redirect:/administration/link.html");
		}
		model.put("issueLink", issueLink);
		return new ModelAndView("administration/issue_link_form", model);
	}
	
	@RequestMapping(value = "status/edit.html", method = RequestMethod.GET)
	public ModelAndView statusEditRequest(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam("status") String status, RedirectAttributes redirectAttributes, ModelMap model) {
		IssueStatus issueStatus = issuePropertyConfigurationService.getIssueStatus(auth, status);
		if (issueStatus == null) {
			redirectAttributes.addFlashAttribute(ERROR, "Issue Status no found.");
			return new ModelAndView("redirect:/administration/status.html");
		}
		model.put("issueStatus", issueStatus);
		model.put("icons", IssueStatus.ICONS);
		return new ModelAndView("administration/issue_status_form", model);
	}

	@RequestMapping(value = "resolution/edit.html", method = RequestMethod.GET)
	public ModelAndView resolutionEditRequest(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam("resolution") String resolution, RedirectAttributes redirectAttributes, ModelMap model) {
		IssueResolution issueResolution = issuePropertyConfigurationService.getIssueResolution(auth, resolution);
		if (issueResolution == null) {
			redirectAttributes.addFlashAttribute(ERROR, "Issue Resolution no found.");
			return new ModelAndView("redirect:/administration/resolutions.html");
		}
		model.put("issueResolution", issueResolution);
		return new ModelAndView("administration/issue_resolution_form", model);
	}
	
	@RequestMapping(value = "priority/edit.html", method = RequestMethod.GET)
	public ModelAndView priorityEditRequest(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam("priority") String priority, RedirectAttributes redirectAttributes, ModelMap model) {
		IssuePriority issuePriority = issuePropertyConfigurationService.getIssuePriority(auth, priority);
		if (issuePriority == null) {
			redirectAttributes.addFlashAttribute(ERROR, "Issue Priority no found.");
			return new ModelAndView("redirect:/administration/priorities.html");
		}
		model.put("issuePriority", issuePriority);
		model.put("icons", IssuePriority.ICONS);
		return new ModelAndView("administration/issue_priority_form", model);
	}

	@RequestMapping(value = "typeScheme/edit", method = RequestMethod.POST)
	public String typeSchemeEdit(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("scheme") String scheme,
			@ModelAttribute("issueTypeScheme") @Valid IssueTypeScheme issueTypeScheme, BindingResult result, 
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("formErrors", initErrorMessages(result.getAllErrors(), messageSource));
		} else {
			try {
				issuePropertyConfigurationService.update(auth, issueTypeScheme);
				redirectAttributes.addFlashAttribute(TIP, "Issue Type Scheme has been updated.");
				return "redirect:/administration/typeSchemes.html";
			} catch (InformationException e) {
				redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
			}
		}
		return "redirect:/administration/typeScheme/edit.html?scheme=" + scheme;
	}

	@RequestMapping(value = "type/edit", method = RequestMethod.POST)
	public String typeEdit(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("type") String type,
			@ModelAttribute("issueType") @Valid IssueType issueType, BindingResult result, 
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("formErrors", initErrorMessages(result.getAllErrors(), messageSource));
		} else {
			try {
				issueType.setStandard(true);
				issuePropertyConfigurationService.update(auth, issueType, true);
				redirectAttributes.addFlashAttribute(TIP, "Issue Type has been updated.");
				return "redirect:/administration/types.html";
			} catch (InformationException e) {
				redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
			}
		}
		return "redirect:/administration/type/edit.html?type=" + type;
	}

	@RequestMapping(value = "subtask/edit", method = RequestMethod.POST)
	public String subtaskEdit(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("type") String type,
			@ModelAttribute("issueType") @Valid IssueType issueType, BindingResult result, 
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("formErrors", initErrorMessages(result.getAllErrors(), messageSource));
		} else {
			try {
				issueType.setStandard(false);
				issuePropertyConfigurationService.update(auth, issueType, false);
				redirectAttributes.addFlashAttribute(TIP, "Sub task has been updated.");
				return "redirect:/administration/subtasks.html";
			} catch (InformationException e) {
				redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
			}
		}
		return "redirect:/administration/subtask/edit.html?type=" + type;
	}

	@RequestMapping(value = "link/edit", method = RequestMethod.POST)
	public String issueLinkEdit(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("link") String link,
			@ModelAttribute("issueLink") @Valid IssueLink issueLink, BindingResult result, 
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("formErrors", initErrorMessages(result.getAllErrors(), messageSource));
		} else {
			try {
				issuePropertyConfigurationService.update(auth, issueLink);
				redirectAttributes.addFlashAttribute(TIP, "Issue Link has been updated.");
				return "redirect:/administration/links.html";
			} catch (InformationException e) {
				redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
			}
		}
		return "redirect:/administration/link/edit.html?link=" + link;
	}
	

	@RequestMapping(value = "status/edit", method = RequestMethod.POST)
	public String statusEdit(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam("status") String status,
			@ModelAttribute("issueStatus") @Valid IssueStatus issueStatus, BindingResult result, 
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("formErrors", initErrorMessages(result.getAllErrors(), messageSource));
		} else {
			try {
				issuePropertyConfigurationService.update(auth, issueStatus);
				redirectAttributes.addFlashAttribute(TIP, "Issue Status has been updated.");
				return "redirect:/administration/status.html";
			} catch (InformationException e) {
				redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
			}
		}
		return "redirect:/administration/status/edit.html?status=" + status;
	}

	@RequestMapping(value = "resolution/edit", method = RequestMethod.POST)
	public String resolutionEdit(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam("resolution") String resolution,
			@ModelAttribute("issueResolution") @Valid IssueResolution issueResolution, BindingResult result, 
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("formErrors", initErrorMessages(result.getAllErrors(), messageSource));
		} else {
			try {
				issuePropertyConfigurationService.update(auth, issueResolution);
				redirectAttributes.addFlashAttribute(TIP, "Issue Resolution has been updated.");
				return "redirect:/administration/resolutions.html";
			} catch (InformationException e) {
				redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
			}
		}
		return "redirect:/administration/resolution/edit.html?resolution=" + resolution;
	}
	
	@RequestMapping(value = "priority/edit", method = RequestMethod.POST)
	public String priorityEdit(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam("priority") String priority,
			@ModelAttribute("issuePriority") @Valid IssuePriority issuePriority, BindingResult result, 
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("formErrors", initErrorMessages(result.getAllErrors(), messageSource));
		} else {
			try {
				issuePropertyConfigurationService.update(auth, issuePriority);
				redirectAttributes.addFlashAttribute(TIP, "Issue Priority has been updated.");
				return "redirect:/administration/priorities.html";
			} catch (InformationException e) {
				redirectAttributes.addFlashAttribute(WARNING, e.getMessage());
			}
		}
		return "redirect:/administration/priority/edit.html?priority=" + priority;
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
