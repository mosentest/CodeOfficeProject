package mu.codeoffice.controller;

import static mu.codeoffice.utility.MessageUtil.addErrorMessage;
import static mu.codeoffice.utility.MessageUtil.addNoticeMessage;

import java.util.Arrays;

import javax.servlet.http.HttpSession;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.ProjectCategory;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.service.ProjectCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/enterprise/")
public class ProjectCategoryController {

	@Autowired
	private ProjectCategoryService projectCategoryService;
	
	@RequestMapping(value = "category", method = RequestMethod.GET)
	public ModelAndView projectCategories(ModelMap model, @AuthenticationPrincipal EnterpriseAuthentication auth) {
		loadInformations(auth, model);
		return new ModelAndView("enterprise/project/projectcategory", model);
	}
	
	@RequestMapping(value = "category/{id}", method = RequestMethod.GET)
	public ModelAndView projectCategory(@PathVariable("id") Long id, @AuthenticationPrincipal EnterpriseAuthentication auth,
			HttpSession session, ModelMap model) {
		ProjectCategory category = projectCategoryService.getProjectCategory(id, auth);
		if (category == null) {
			addErrorMessage(session, "Category not found.");
			return new ModelAndView("redirect:/enterprise/category");
		}
		loadInformation(auth, model, id);
		return new ModelAndView("enterprise/project/projectcategory", model);
	}

	@RequestMapping(value = "category/create", method = RequestMethod.GET) 
	public ModelAndView categoryReqeust(@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		model.put("projectCategory", new ProjectCategory());
		model.put("edit", false);
		model.put("projectCategoryNames", projectCategoryService.getProjectCategoryNames(auth));
		return new ModelAndView("enterprise/project/projectcategory_form", model);
	}

	@RequestMapping(value = "category/create", method = RequestMethod.POST) 
	public ModelAndView categoryCreate(@ModelAttribute ProjectCategory projectCategory, @AuthenticationPrincipal EnterpriseAuthentication auth,
			HttpSession session, ModelMap model) {
		projectCategory.setId(null);
		try {
			projectCategory = projectCategoryService.update(projectCategory, auth);
			addNoticeMessage(session, "Category has been created.");
			return new ModelAndView("redirect:/enterprise/category");
		} catch (AuthenticationException e) {
			throw e;
		} catch (InformationException e) {
			model.put("projectCategoryNames", projectCategoryService.getProjectCategoryNames(auth));
			addErrorMessage(session, "Could not create category.", e.getMessage());
			return new ModelAndView("enterprise/project/projectcategory_form", model);
		}
	}

	@RequestMapping(value = "category/{id}/edit", method = RequestMethod.GET) 
	public ModelAndView categoryEditRequest(@PathVariable("id") Long id, @AuthenticationPrincipal EnterpriseAuthentication auth,
			HttpSession session, ModelMap model) {
		model.put("edit", true);
		ProjectCategory category = projectCategoryService.getProjectCategory(id, auth);
		if (category == null) {
			addNoticeMessage(session, "Category not found.");
			return new ModelAndView("redirect:/enterprise/category");
		} else {
			model.put("projectCategoryNames", projectCategoryService.getProjectCategoryNames(auth));
			model.put("projectCategory", category);
			model.put("projectCategoryId", category.getId());
			return new ModelAndView("enterprise/project/projectcategory_form", model);
		}
	}

	@RequestMapping(value = "category/{id}/edit", method = RequestMethod.POST) 
	public ModelAndView categoryEdit(@PathVariable("id") Long id, @ModelAttribute ProjectCategory category,
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) {
		model.put("edit", true);
		if (projectCategoryService.getProjectCategory(id, auth) == null) {
			addNoticeMessage(session, "Category not found.");
			return new ModelAndView("redirect:/enterprise/category");
		}
		try {
			category.setId(id);
			category = projectCategoryService.update(category, auth);
			loadInformation(auth, model, category);
			addNoticeMessage(session, "Category has been updated.");
			return new ModelAndView("redirect:/enterprise/category");
		} catch (InformationException e) {
			model.put("projectCategoryNames", projectCategoryService.getProjectCategoryNames(auth));
			model.put("projectCategory", category);
			model.put("projectCategoryId", id);			
			addErrorMessage(session, "Could not update category.", e.getMessage());
			return new ModelAndView("enterprise/project/projectcategory_form", model);
		}
	}

	@RequestMapping(value = "category/{id}/delete", method = RequestMethod.GET) 
	public ModelAndView categoryRemove(@PathVariable("id") Long id, @AuthenticationPrincipal EnterpriseAuthentication auth, 
			HttpSession session, ModelMap model) {
		try {
			projectCategoryService.remove(id, auth);
			addNoticeMessage(session, "Category has been removed.");
			return new ModelAndView("redirect:/enterprise/category");
		} catch (AuthenticationException e) {
			throw e;
		} catch (InformationException e) {
			loadInformations(auth, model);
			model.put("errorMessage", e.getMessage());
			return new ModelAndView("enterprise/project/projectcategory", model);
		}
	}
	
	private void loadInformations(EnterpriseAuthentication auth, ModelMap model) {
		model.put("projectCategoryNames", projectCategoryService.getProjectCategoryNames(auth));
		model.put("projectCategories", projectCategoryService.getProjectCategories(auth));
	}
	
	private void loadInformation(EnterpriseAuthentication auth, ModelMap model, Long id) {
		loadInformation(auth, model, projectCategoryService.getProjectCategory(id, auth));
	}
	
	private void loadInformation(EnterpriseAuthentication auth, ModelMap model, ProjectCategory category) {
		model.put("projectCategoryNames", projectCategoryService.getProjectCategoryNames(auth));
		model.put("projectCategories", Arrays.asList(category));
		model.put("projectCategoryId", category.getId());
	}
	
}
