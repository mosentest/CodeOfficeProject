package mu.codeoffice.controller;

import java.util.Arrays;

import mu.codeoffice.service.ProjectService;

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
@RequestMapping("/enterprise/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView main(ModelMap model) {
		return new ModelAndView("enterprise/project/projecthome", model);
	}
	
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public ModelAndView projectCategories(ModelMap model, @AuthenticationPrincipal User user) {
		model.put("projectCategories", projectService.getProjectCategories(user));
		model.put("projectCategoryNames", projectService.getProjectCategoryNames(user));
		return new ModelAndView("enterprise/project/projectcategory", model);
	}
	
	@RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
	public ModelAndView projectCategory(@PathVariable("id") Long id, ModelMap model, @AuthenticationPrincipal User user) {
		model.put("projectCategories", Arrays.asList(projectService.getProjectCategory(id, user)));
		model.put("projectCategoryNames", projectService.getProjectCategoryNames(user));
		model.put("projectCategoryId", id);
		return new ModelAndView("enterprise/project/projectcategory", model);
	}

	@RequestMapping(value = "/newcategory", method = RequestMethod.GET) 
	public ModelAndView categoryReqeust(ModelMap model) {
		return new ModelAndView("enterprise/project/categoryform", model);
	}
	
	@RequestMapping(value = "/newproject", method = RequestMethod.GET) 
	public ModelAndView projectRequest(ModelMap model) {
		return new ModelAndView("enterprise/project/projectform", model);
	}
	
}
