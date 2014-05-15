package mu.codeoffice.controller;

import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.Version;
import mu.codeoffice.enums.ProjectPermission;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;
import mu.codeoffice.service.ProjectService;
import mu.codeoffice.service.VersionService;

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
public class VersionController {
	
	private static final int LIST_ITEMS = 15;

	private static final ProjectPermission[] VIEW_AUTH = {
		ProjectPermission.VERSION_COMPONENT
	};
	
	private static final ProjectPermission[] MANAGE_AUTH = {
		ProjectPermission.VERSION_COMPONENT_MANAGE
	};

	@Autowired
	private VersionService versionService;
	
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value = "pro_{project}/v_{version}", method = RequestMethod.GET) 
	public ModelAndView summary(@PathVariable("project") String projectCode, @PathVariable("version") String versionCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws EnterpriseAuthenticationException {
		authorizeView(auth, projectCode);
		Project project = projectService.getProjectInfo(projectCode, auth);
		Version version = versionService.getProjectVersion(project, versionCode);
		model.put("project", project);
		model.put("version", version);
		model.put("monthlySummary", versionService.getVersionMonthlySummary(project, version));
		return new ModelAndView("enterprise/project/version_summary");
	}
	
	@RequestMapping(value = "pro_{project}/v_{version}/related", method = RequestMethod.GET) 
	public ModelAndView related(@PathVariable("project") String projectCode, @PathVariable("version") String versionCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws EnterpriseAuthenticationException {
		authorizeView(auth, projectCode);
		Project project = projectService.getProjectInfo(projectCode, auth);
		Version version = versionService.getProjectVersion(project, versionCode);
		model.put("project", project);
		model.put("version", version);
		model.put("statusMap", versionService.getCaseStatusSummary(project, version.getId(), null));
		model.put("priorityMap", versionService.getCasePrioritySummary(project, version.getId(), null));
		model.put("assigneeMap", versionService.getAssigneeSummary(project, version.getId(), null));
		model.put("labelMap", versionService.getLabelSummary(project, version.getId(), null));
		model.put("componentMap", versionService.getComponentSummary(project, version.getId(), null));
		model.put("totalCase", version.getNoRelated());
		return new ModelAndView("enterprise/project/version_relatedsummary");
	}
	
	@RequestMapping(value = "pro_{project}/v_{version}/release", method = RequestMethod.GET) 
	public ModelAndView release(@PathVariable("project") String projectCode, @PathVariable("version") String versionCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws EnterpriseAuthenticationException {
		authorizeView(auth, projectCode);
		Project project = projectService.getProjectInfo(projectCode, auth);
		Version version = versionService.getProjectVersion(project, versionCode);
		model.put("project", project);
		model.put("version", version);
		model.put("statusMap", versionService.getCaseStatusSummary(project, null, version.getId()));
		model.put("priorityMap", versionService.getCasePrioritySummary(project, null, version.getId()));
		model.put("assigneeMap", versionService.getAssigneeSummary(project, null, version.getId()));
		model.put("labelMap", versionService.getLabelSummary(project, null, version.getId()));
		model.put("componentMap", versionService.getComponentSummary(project, null, version.getId()));
		model.put("totalCase", version.getNoRelease());
		return new ModelAndView("enterprise/project/version_releasesummary");
	}
	
	@RequestMapping(value = "pro_{project}/v_{version}/releasenote", method = RequestMethod.GET) 
	public ModelAndView releasenote(@PathVariable("project") String projectCode, @PathVariable("version") String versionCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws EnterpriseAuthenticationException {
		authorizeView(auth, projectCode);
		Project project = projectService.getProjectInfo(projectCode, auth);
		Version version = versionService.getProjectVersion(project, versionCode);
		model.put("project", project);
		model.put("version", version);
		model.put("releaseNote", versionService.getReleaseNote(project.getId(), version.getId()));
		return new ModelAndView("enterprise/project/version_releasenote");
	}
	
	@RequestMapping(value = "pro_{project}/v_{version}/case", method = RequestMethod.GET) 
	public ModelAndView cases(@PathVariable("project") String projectCode, @PathVariable("version") String versionCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws EnterpriseAuthenticationException {
		authorizeView(auth, projectCode);
		Project project = projectService.getProjectInfo(projectCode, auth);
		Version version = versionService.getProjectVersion(project, versionCode);
		model.put("project", project);
		model.put("version", version);
		model.put("relatedCase", versionService.getRelatedCase(project.getId(), version.getId(), 0, LIST_ITEMS, true, "code"));
		model.put("releaseCase", versionService.getReleaseCase(project.getId(), version.getId(), 0, LIST_ITEMS, true, "code"));
		return new ModelAndView("enterprise/project/version_cases");
	}
	
	public ModelAndView versionRequest(String projectCode, @AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) {
		authorizeManage(auth, projectCode);
		return null;
	}
	
	private void authorizeManage(EnterpriseAuthentication auth, String projectCode) throws EnterpriseAuthenticationException {
		if (!auth.hasProjectAuthority() && !auth.projectAuthenticate(projectService.getProjectAuthority(auth.getEnterpriseUser(), projectCode), MANAGE_AUTH)) {
			throw new EnterpriseAuthenticationException("You are not authorized.");
		}
	}
	
	private void authorizeView(EnterpriseAuthentication auth, String projectCode) throws EnterpriseAuthenticationException {
		if (!auth.hasProjectAuthority() && !auth.projectAuthenticate(projectService.getProjectAuthority(auth.getEnterpriseUser(), projectCode), VIEW_AUTH)) {
			throw new EnterpriseAuthenticationException("You are not authorized.");
		}
	}
	
}
