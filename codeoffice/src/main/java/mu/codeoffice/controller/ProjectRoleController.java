package mu.codeoffice.controller;

import javax.validation.Valid;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.dto.ProjectRoleDTO;
import mu.codeoffice.entity.settings.ProjectRole;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.service.ProjectRoleService;
import mu.codeoffice.service.UserGroupService;
import mu.codeoffice.service.UserService;
import mu.codeoffice.tag.Function;
import mu.codeoffice.utility.StringUtil;

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
public class ProjectRoleController implements GenericController {

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserGroupService userGroupService;
	
	@Autowired
	private ProjectRoleService projectRoleService;
	
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "administration/projectRole/create", method = RequestMethod.POST)
	public String create(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@ModelAttribute("projectRole") @Valid ProjectRole projectRole, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("formErrors", initErrorMessages(result.getAllErrors(), messageSource));
		} else {
			try {
				projectRoleService.create(auth, projectRole);
				redirectAttributes.addFlashAttribute(TIP, "Project Role '" + projectRole.getName() + "' has been deleted.");
			} catch (InformationException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
			}
		}
		return "redirect:/administration/projectRoles.html";
	}

	@RequestMapping(value = "administration/projectRole/delete", method = RequestMethod.POST)
	public String deleteProjectRole(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("role") String role, RedirectAttributes redirectAttributes) {
		try {
			role = Function.unmaskURL(role);
			projectRoleService.delete(auth, role);
			redirectAttributes.addFlashAttribute(TIP, "Project Role '" + role + "' has been deleted.");
		} catch (InformationException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
		}
		return "redirect:/administration/projectRoles.html";
	}
	
	@RequestMapping(value = "administration/projectRole/edit", method = RequestMethod.POST)
	public String projectRoleEdit(@AuthenticationPrincipal EnterpriseAuthentication auth,
			@RequestParam("role") String role,
			@ModelAttribute("projectRole") @Valid ProjectRoleDTO projectRoleDTO, BindingResult result, 
			RedirectAttributes redirectAttributes) {
		role = Function.unmaskURL(role);
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("formErrors", initErrorMessages(result.getAllErrors(), messageSource));
		} else {
			try {
				projectRoleService.edit(auth, projectRoleDTO);
				redirectAttributes.addFlashAttribute(TIP, "Project Role has been updated");
				return "redirect:/administration/projectRole/edit.html?role=" + projectRoleDTO.getName();
			} catch (InformationException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
			}
		}
		return "redirect:/administration/projectRole/edit.html?role=" + role;
		
	}

	@RequestMapping(value = "administration/projectRoles.html", method = RequestMethod.GET)
	public ModelAndView projectRoles(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("projectRoles", projectRoleService.getProjectRoles(auth, true));
		model.put("projectRole", new ProjectRole());
		return new ModelAndView("administration/project_projectRoles", model);
	}

	@RequestMapping(value = "administration/projectRole/edit.html", method = RequestMethod.GET)
	public ModelAndView projectRole(@AuthenticationPrincipal EnterpriseAuthentication auth, 
			@RequestParam(value = "role", required = true) String role, 
			@RequestParam(value = "query", required = false) String query, 
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex, 
			@RequestParam(value = "sort", required = false) String sort, 
			@RequestParam(value = "descending", required = false) boolean descending, 
			RedirectAttributes redirectAttributes, ModelMap model) {
		role = Function.unmaskURL(role);
		ProjectRole projectRole = projectRoleService.getProjectRole(auth, role, true);
		if (projectRole == null) {
			redirectAttributes.addFlashAttribute(ERROR, "Project role doesn't exist.");
			return new ModelAndView("redirect:/administration/projectRoles.html");
		}
		model.put("projectRole", new ProjectRoleDTO().toDTO(projectRole));
		model.put("userPage", userService.roleSearch(auth, projectRole.getId(), query, pageIndex, 20, sort, !descending, false));
		if (!StringUtil.isEmptyString(query)) { model.put("query", query); }
		if (!StringUtil.isEmptyString(sort)) { model.put("sort", sort); }
		if (descending) { model.put("descending", descending); }
		if (pageIndex != 0) { model.put("pageIndex", pageIndex); }
		return new ModelAndView("administration/project_projectRole_form", model);
	}
}
