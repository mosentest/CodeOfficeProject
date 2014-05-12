package mu.codeoffice.controller;

import java.util.List;

import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.ProjectActivity;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;
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
	public ModelAndView project(@PathVariable("code") String code, @AuthenticationPrincipal User user, ModelMap model) 
			throws EnterpriseAuthenticationException {
		Project project = projectService.getProjectInfo(code, (EnterpriseAuthentication) user);
		model.put("project", project);
		model.put("monthlySummary", projectService.getProjectMonthlySummary(project));
		model.put("weeklySummary", projectService.getProjectWeeklySummary(project));
		model.put("unreleasedVersions", projectService.getUnreleasedVersions(project));
		model.put("activityStream", null);
		model.put("moreActivity", true);
		return new ModelAndView("enterprise/project/project_summary", model);
	}

	@RequestMapping(value = "project/{code}/versions", method = RequestMethod.GET)
	public ModelAndView versions(@PathVariable("code") String code, @AuthenticationPrincipal User user, ModelMap model) 
			throws EnterpriseAuthenticationException {
		Project project = projectService.getProjectInfo(code, (EnterpriseAuthentication) user);
		model.put("project", project);
		return new ModelAndView("enterprise/project/project_versions", model);
	}

	@RequestMapping(value = "project/{code}/roadmap", method = RequestMethod.GET)
	public ModelAndView roadmap(@PathVariable("code") String code, @AuthenticationPrincipal User user, ModelMap model) 
			throws EnterpriseAuthenticationException {
		Project project = projectService.getProjectInfo(code, (EnterpriseAuthentication) user);
		model.put("project", project);
		return new ModelAndView("enterprise/project/project_roadmap", model);
	}

	@RequestMapping(value = "project/{code}/summary", method = RequestMethod.GET)
	public ModelAndView summary(@PathVariable("code") String code, @AuthenticationPrincipal User user, ModelMap model) 
			throws EnterpriseAuthenticationException {
		Project project = projectService.getProjectInfo(code, (EnterpriseAuthentication) user);
		model.put("project", project);
		return new ModelAndView("enterprise/project/project_casesummary", model);
	}

	@RequestMapping(value = "project/{code}/case", method = RequestMethod.GET)
	public ModelAndView cases(@PathVariable("code") String code, @AuthenticationPrincipal User user, ModelMap model) 
			throws EnterpriseAuthenticationException {
		Project project = projectService.getProjectInfo(code, (EnterpriseAuthentication) user);
		model.put("project", project);
		return new ModelAndView("enterprise/project/project_cases", model);
	}

	@RequestMapping(value = "project/{code}/rolegroup", method = RequestMethod.GET)
	public ModelAndView rolegroup(@PathVariable("code") String code, @AuthenticationPrincipal User user, ModelMap model) 
			throws EnterpriseAuthenticationException {
		Project project = projectService.getProjectInfo(code, (EnterpriseAuthentication) user);
		model.put("project", project);
		return new ModelAndView("enterprise/project/project_rolegroup", model);
	}

	@RequestMapping(value = "project/{code}/components", method = RequestMethod.GET)
	public ModelAndView components(@PathVariable("code") String code, @AuthenticationPrincipal User user, ModelMap model) 
			throws EnterpriseAuthenticationException {
		Project project = projectService.getProjectInfo(code, (EnterpriseAuthentication) user);
		model.put("project", project);
		return new ModelAndView("enterprise/project/project_components", model);
	}

	@RequestMapping(value = "project/{code}/labels", method = RequestMethod.GET)
	public ModelAndView labels(@PathVariable("code") String code, @AuthenticationPrincipal User user, ModelMap model) 
			throws EnterpriseAuthenticationException {
		Project project = projectService.getProjectInfo(code, (EnterpriseAuthentication) user);
		model.put("project", project);
		return new ModelAndView("enterprise/project/project_labels", model);
	}

	@RequestMapping(value = "project/{code}/notes", method = RequestMethod.GET)
	public ModelAndView notes(@PathVariable("code") String code, @AuthenticationPrincipal User user, ModelMap model) 
			throws EnterpriseAuthenticationException {
		Project project = projectService.getProjectInfo(code, (EnterpriseAuthentication) user);
		model.put("project", project);
		return new ModelAndView("enterprise/project/project_notes", model);
	}

	@RequestMapping(value = "project/{code}/changelog", method = RequestMethod.GET)
	public ModelAndView changelog(@PathVariable("code") String code, @AuthenticationPrincipal User user, ModelMap model) 
			throws EnterpriseAuthenticationException {
		Project project = projectService.getProjectInfo(code, (EnterpriseAuthentication) user);
		model.put("project", project);
		return new ModelAndView("enterprise/project/project_changelog", model);
	}

	@RequestMapping(value = "project/{code}/source", method = RequestMethod.GET)
	public ModelAndView source(@PathVariable("code") String code, @AuthenticationPrincipal User user, ModelMap model) 
			throws EnterpriseAuthenticationException {
		Project project = projectService.getProjectInfo(code, (EnterpriseAuthentication) user);
		model.put("project", project);
		return new ModelAndView("enterprise/project/project_source", model);
	}

	@RequestMapping(value = "project/{code}/reviews", method = RequestMethod.GET)
	public ModelAndView reviews(@PathVariable("code") String code, @AuthenticationPrincipal User user, ModelMap model) 
			throws EnterpriseAuthenticationException {
		Project project = projectService.getProjectInfo(code, (EnterpriseAuthentication) user);
		model.put("project", project);
		return new ModelAndView("enterprise/project/project_reviews", model);
	}
	
	public List<ProjectActivity> loadActivity() {
		return null;
	}
	
	@RequestMapping(value = "newproject", method = RequestMethod.GET) 
	public ModelAndView projectRequest(ModelMap model) {
		return new ModelAndView("enterprise/project/project_form", model);
	}
	
}
