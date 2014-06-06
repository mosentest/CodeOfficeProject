package mu.codeoffice.controller;

import javax.validation.Valid;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.settings.ProjectTemplate;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.service.ProjectPermissionSchemeService;
import mu.codeoffice.service.ProjectTemplateService;
import mu.codeoffice.service.WorkFlowService;

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
@RequestMapping("/")
public class ProjectTemplateController implements GenericController {

	@Autowired
	private ProjectTemplateService projectTemplateService;
	
	@Autowired
	private WorkFlowService workFlowService;
	
	@Autowired
	private ProjectPermissionSchemeService projectPermissionSchemeService;
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value = "administration/projectTemplates.html", method = RequestMethod.GET)
	public ModelAndView get(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("projectTemplates", projectTemplateService.getProjectTemplates(auth));
		model.put("workFlows", workFlowService.getWorkFlows(auth, false));
		model.put("projectPermissionSchemes", projectPermissionSchemeService.getProjectPermissionSchemes(auth, false));
		model.put("projectTemplate", new ProjectTemplate());
		return new ModelAndView("administration/project_projectTemplates");
	}
	
	@RequestMapping(value = "administration/projectTemplate/edit.html", method = RequestMethod.GET)
	public ModelAndView edit(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("template") String template, 
			RedirectAttributes redirectAttributes, ModelMap model) {
		ProjectTemplate projectTemplate = projectTemplateService.getProjectTemplate(auth, template);
		if (projectTemplate == null) {
			redirectAttributes.addFlashAttribute(ERROR, "Project Template not found.");
			return new ModelAndView("redirect:/administration/projectTemplates.html");
		}
		model.put("projectTemplate", projectTemplate);
		model.put("workFlows", workFlowService.getWorkFlows(auth, false));
		model.put("projectPermissionSchemes", projectPermissionSchemeService.getProjectPermissionSchemes(auth, false));
		return new ModelAndView("administration/project_projectTemplate_form");
	}
	
	@RequestMapping(value = "administration/projectTemplate/edit", method = RequestMethod.POST)
	public String edit(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("template") String template,
			@ModelAttribute("projectTemplate") @Valid ProjectTemplate projectTemplate, BindingResult result, 
			RedirectAttributes redirectAttributes, ModelMap model) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("formErrors", initErrorMessages(result.getAllErrors(), messageSource));
		} else {
			try {
				projectTemplateService.update(auth, projectTemplate);
				redirectAttributes.addFlashAttribute(TIP, "Project Template has been updated.");
				return "redirect:/administration/projectTemplates.html";
			} catch (InformationException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
			}
		}
		return "redirect:/administration/projectTemplate/edit.html?template=" + template;
	}
	
	@RequestMapping(value = "administration/projectTemplate/create", method = RequestMethod.POST)
	public String create(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@ModelAttribute("projectTemplate") @Valid ProjectTemplate projectTemplate, BindingResult result, 
			RedirectAttributes redirectAttributes, ModelMap model) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("formErrors", initErrorMessages(result.getAllErrors(), messageSource));
		} else {
			try {
				projectTemplateService.create(auth, projectTemplate);
				redirectAttributes.addFlashAttribute(TIP, "Project Template has been create.");
			} catch (InformationException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
			}
		}
		return "redirect:/administration/projectTemplates.html";
	}
	
	@RequestMapping(value = "administration/projectTemplate/delete", method = RequestMethod.POST)
	public String delete(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("template") String template, 
			RedirectAttributes redirectAttributes, ModelMap model) {
		try {
			projectTemplateService.delete(auth, template);
			redirectAttributes.addFlashAttribute(TIP, "Project Template has been deleted.");			
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/projectTemplates.html";
	}
	
}
