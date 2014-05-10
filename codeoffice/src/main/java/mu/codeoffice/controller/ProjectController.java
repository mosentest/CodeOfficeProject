package mu.codeoffice.controller;

import mu.codeoffice.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
	public ModelAndView projectCategories(ModelMap model) {
		return new ModelAndView("enterprise/project/projectcategories", model);
	}

	@RequestMapping(value = "/active", method = RequestMethod.GET) 
	public ModelAndView activeProjects(ModelMap model) {
		return new ModelAndView("enterprise/project/projectlist", model);
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
