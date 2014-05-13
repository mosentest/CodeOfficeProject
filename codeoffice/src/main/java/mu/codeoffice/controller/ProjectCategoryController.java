package mu.codeoffice.controller;

import java.util.Arrays;

import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.service.ProjectCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
	public ModelAndView projectCategories(ModelMap model, @AuthenticationPrincipal User user) {
		model.put("projectCategories", projectCategoryService.getProjectCategories((EnterpriseAuthentication)user));
		model.put("projectCategoryNames", projectCategoryService.getProjectCategoryNames((EnterpriseAuthentication)user));
		return new ModelAndView("enterprise/project/projectcategory", model);
	}
	
	@RequestMapping(value = "category/{id}", method = RequestMethod.GET)
	public ModelAndView projectCategory(@PathVariable("id") Long id, ModelMap model, @AuthenticationPrincipal User user) {
		model.put("projectCategories", Arrays.asList(projectCategoryService.getProjectCategory(id, (EnterpriseAuthentication) user)));
		model.put("projectCategoryNames", projectCategoryService.getProjectCategoryNames((EnterpriseAuthentication) user));
		model.put("projectCategoryId", id);
		return new ModelAndView("enterprise/project/projectcategory", model);
	}

	@RequestMapping(value = "category/new", method = RequestMethod.GET) 
	public ModelAndView categoryReqeust(ModelMap model) {
		return new ModelAndView("enterprise/project/category_form", model);
	}
}
