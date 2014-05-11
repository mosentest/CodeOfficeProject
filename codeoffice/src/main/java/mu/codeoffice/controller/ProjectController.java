package mu.codeoffice.controller;

import java.util.List;

import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.ProjectActivity;
import mu.codeoffice.security.EnterpriseAuthentication;
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
@RequestMapping("/enterprise/")
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value = "project", method = RequestMethod.GET)
	public ModelAndView main(ModelMap model) {
		return new ModelAndView("enterprise/project/project_home", model);
	}
	
	@RequestMapping(value = "project/{code}", method = RequestMethod.GET)
	public ModelAndView project(@PathVariable("code") String code, @AuthenticationPrincipal User user, ModelMap model) {
		Project project = projectService.getProjectInfo(code, (EnterpriseAuthentication) user);
		if (project == null) {
			model.put("error", "You do not have access to this project.");
			return new ModelAndView("enterprise/accessdenied", model);
		}
		model.put("monthlySummary", projectService.getProjectMonthlySummary(project));
		model.put("weeklySummary", projectService.getProjectWeeklySummary(project));
		model.put("unreleasedVersions", projectService.getUnreleasedVersions(project));
		model.put("moreActivity", true);
		return new ModelAndView("enterprise/project/project_summary", model);
	}
	
	public List<ProjectActivity> loadActivity() {
		return null;
	}
	
	@RequestMapping(value = "newproject", method = RequestMethod.GET) 
	public ModelAndView projectRequest(ModelMap model) {
		return new ModelAndView("enterprise/project/project_form", model);
	}
	
}
