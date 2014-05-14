package mu.codeoffice.controller;

import java.util.List;

import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.ProjectActivity;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;
import mu.codeoffice.service.CaseService;
import mu.codeoffice.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
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

	private static final int LIST_ITEMS = 15;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private CaseService caseService;
	
	@RequestMapping(value = "project", method = RequestMethod.GET)
	public ModelAndView main(ModelMap model) {
		return new ModelAndView("enterprise/project/projecthome", model);
	}
	@RequestMapping(value = "pro_{code}", method = RequestMethod.GET)
	public ModelAndView project(@PathVariable("code") String code, @AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) 
			throws EnterpriseAuthenticationException {
		Project project = projectService.getProjectInfo(code, auth);
		model.put("project", project);
		model.put("caseStream", caseService.getProjectCaseStream(auth, project.getId(), 0, LIST_ITEMS));
		model.put("activityStream", null);
		model.put("caseInProgress", caseService.getCaseInProgress(auth, project.getId(), 0, LIST_ITEMS));
		model.put("assignedCase", caseService.getAssignedCase(auth, project.getId(), 0, LIST_ITEMS));
		model.put("assigneeStatus", caseService.getAssigneeStatus(project.getId()));
		model.put("statusMap", projectService.getCaseStatusSummary(project));
		return new ModelAndView("enterprise/project/project_home", model);
	}
	
	@RequestMapping(value = "pro_{code}/summary", method = RequestMethod.GET)
	public ModelAndView summary(@PathVariable("code") String code, @AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) 
			throws EnterpriseAuthenticationException {
		Project project = projectService.getProjectInfo(code, auth);
		model.put("project", project);
		model.put("monthlySummary", projectService.getProjectMonthlySummary(project));
		model.put("weeklySummary", projectService.getProjectWeeklySummary(project));
		model.put("unreleasedVersions", projectService.getUnreleasedVersions(project));
		model.put("activityStream", null);
		model.put("moreActivity", true);
		return new ModelAndView("enterprise/project/project_summary", model);
	}
	
	@RequestMapping(value = "pro_{code}/versions", method = RequestMethod.GET)
	public ModelAndView versions(@PathVariable("code") String code, @AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) 
			throws EnterpriseAuthenticationException {
		Project project = projectService.getProjectInfo(code, auth);
		model.put("project", project);
		model.put("versions", projectService.getProjectVersions(project.getId()));
		return new ModelAndView("enterprise/project/project_versions", model);
	}

	@RequestMapping(value = "pro_{code}/roadmap", method = RequestMethod.GET)
	public ModelAndView roadmap(@PathVariable("code") String code, @AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) 
			throws EnterpriseAuthenticationException {
		Project project = projectService.getProjectInfo(code, auth);
		model.put("project", project);
		model.put("roadMap", projectService.getRoadMap(project));
		return new ModelAndView("enterprise/project/project_roadmap", model);
	}

	@RequestMapping(value = "pro_{code}/casesummary", method = RequestMethod.GET)
	public ModelAndView caseSummary(@PathVariable("code") String code, @AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) 
			throws EnterpriseAuthenticationException {
		Project project = projectService.getProjectInfo(code, auth);
		model.put("project", project);
		model.put("statusMap", projectService.getCaseStatusSummary(project));
		model.put("priorityMap", projectService.getCasePrioritySummary(project));
		model.put("assigneeMap", projectService.getAssigneeSummary(project));
		model.put("labelMap", projectService.getLabelSummary(project));
		model.put("componentMap", projectService.getComponentSummary(project));
		model.put("versionMap", projectService.getVersionSummary(project));
		model.put("totalCase", project.getNoCase());
		return new ModelAndView("enterprise/project/project_casesummary", model);
	}

	@RequestMapping(value = "pro_{code}/case", method = RequestMethod.GET)
	public ModelAndView cases(@PathVariable("code") String code, @AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) 
			throws EnterpriseAuthenticationException {
			Project project = projectService.getProjectInfo(code, auth);
			model.put("project", project);
			model.put("casePage", caseService.getCasePage(auth, project.getId(), 
					null, null, null, null, null, null, null, null, null, 0, LIST_ITEMS, "code", true));
		return new ModelAndView("enterprise/project/project_cases", model);
	}

	@RequestMapping(value = "pro_{code}/case/{page}", method = RequestMethod.GET)
	public ModelAndView casesPage(@PathVariable("page") int page, @PathVariable("code") String code,
		@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws EnterpriseAuthenticationException {
			Project project = projectService.getProjectInfo(code, auth);
			model.put("project", project);
			if (page <= 0) {
				page = 1;
			}
			model.put("casePage", caseService.getCasePage(auth, project.getId(), 
					null, null, null, null, null, null, null, null, null, (page - 1), LIST_ITEMS, "code", true));
			return new ModelAndView("enterprise/project/project_cases", model);
		}

	@RequestMapping(value = "pro_{code}/rolegroup", method = RequestMethod.GET)
	public ModelAndView rolegroup(@PathVariable("code") String code, @AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) 
			throws EnterpriseAuthenticationException {
		Project project = projectService.getProjectInfo(code, auth);
		model.put("project", project);
		model.put("roleGroups", projectService.getProjectRoleGroups(project.getId()));
		return new ModelAndView("enterprise/project/project_rolegroup", model);
	}

	@RequestMapping(value = "pro_{code}/components", method = RequestMethod.GET)
	public ModelAndView components(@PathVariable("code") String code, @AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) 
			throws EnterpriseAuthenticationException {
		Project project = projectService.getProjectInfo(code, auth);
		model.put("project", project);
		model.put("components", projectService.getProjectComponents(project.getId()));
		return new ModelAndView("enterprise/project/project_components", model);
	}

	@RequestMapping(value = "pro_{code}/labels", method = RequestMethod.GET)
	public ModelAndView labels(@PathVariable("code") String code, @AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) 
			throws EnterpriseAuthenticationException {
		Project project = projectService.getProjectInfo(code, auth);
		model.put("project", project);
		model.put("labels", projectService.getProjectLabels(project.getId()));
		return new ModelAndView("enterprise/project/project_labels", model);
	}

	@RequestMapping(value = "pro_{code}/notes", method = RequestMethod.GET)
	public ModelAndView notes(@PathVariable("code") String code, @AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) 
			throws EnterpriseAuthenticationException {
		Project project = projectService.getProjectInfo(code, auth);
		model.put("project", project);
		model.put("notes", projectService.getProjectNote(project.getId()));
		return new ModelAndView("enterprise/project/project_notes", model);
	}

	@RequestMapping(value = "pro_{code}/changelog", method = RequestMethod.GET)
	public ModelAndView changelog(@PathVariable("code") String code, @AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) 
			throws EnterpriseAuthenticationException {
		Project project = projectService.getProjectInfo(code, auth);
		model.put("project", project);
		return new ModelAndView("enterprise/project/project_changelog", model);
	}

	@RequestMapping(value = "pro_{code}/source", method = RequestMethod.GET)
	public ModelAndView source(@PathVariable("code") String code, @AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) 
			throws EnterpriseAuthenticationException {
		Project project = projectService.getProjectInfo(code, auth);
		model.put("project", project);
		return new ModelAndView("enterprise/project/project_source", model);
	}

	@RequestMapping(value = "pro_{code}/reviews", method = RequestMethod.GET)
	public ModelAndView reviews(@PathVariable("code") String code, @AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) 
			throws EnterpriseAuthenticationException {
		Project project = projectService.getProjectInfo(code, auth);
		model.put("project", project);
		return new ModelAndView("enterprise/project/project_reviews", model);
	}
	
	public List<ProjectActivity> loadActivity() {
		return null;
	}
	
	@RequestMapping(value = "project/new", method = RequestMethod.GET) 
	public ModelAndView projectRequest(ModelMap model) {
		return new ModelAndView("enterprise/project/project_form", model);
	}
	
}
