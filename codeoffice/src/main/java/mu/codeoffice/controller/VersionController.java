package mu.codeoffice.controller;

import static mu.codeoffice.utility.MessageUtil.addErrorMessage;
import static mu.codeoffice.utility.MessageUtil.addNoticeMessage;

import java.util.Date;

import javax.servlet.http.HttpSession;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.Version;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.service.VersionService;
import mu.codeoffice.utility.DateEditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/enterprise/")
public class VersionController extends ProjectPermissionRequired {
	
	private static final int LIST_ITEMS = 15;

	@Autowired
	private VersionService versionService;

	@RequestMapping(value = "pro_{projectCode}/v_{versionCode}/start", method = RequestMethod.GET) 
	public ModelAndView start(@PathVariable("projectCode") String projectCode, @PathVariable("versionCode") String versionCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) 
					throws AuthenticationException, InformationException {
		try {
			versionService.start(auth, projectCode, versionCode);
			addNoticeMessage(session, "Changed status of version '" + versionCode + "' to 'Started'.");
			return new ModelAndView("redirect:/enterprise/pro_" + projectCode + "/v_" + versionCode);
		} catch (InformationException e) {
			addErrorMessage(session, e.getMessage());
			return new ModelAndView("redirect:/enterprise/pro_" + projectCode + "/v_" + versionCode);
		}
	}

	@RequestMapping(value = "pro_{projectCode}/v_{versionCode}/stop", method = RequestMethod.GET) 
	public ModelAndView stop(@PathVariable("projectCode") String projectCode, @PathVariable("versionCode") String versionCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) 
					throws AuthenticationException, InformationException {
		try {
			versionService.stop(auth, projectCode, versionCode);
			addNoticeMessage(session, "Stopped progress on version '" + versionCode + "'.");
			return new ModelAndView("redirect:/enterprise/pro_" + projectCode + "/v_" + versionCode);
		} catch (InformationException e) {
			addErrorMessage(session, e.getMessage());
			return new ModelAndView("redirect:/enterprise/pro_" + projectCode + "/v_" + versionCode);
		}
	}

	@RequestMapping(value = "pro_{projectCode}/v_{versionCode}/release", method = RequestMethod.GET) 
	public ModelAndView release(@PathVariable("projectCode") String projectCode, @PathVariable("versionCode") String versionCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) 
					throws AuthenticationException, InformationException {
		try {
			versionService.release(auth, projectCode, versionCode);
			addNoticeMessage(session, "Released version '" + versionCode + "'.");
			return new ModelAndView("redirect:/enterprise/pro_" + projectCode + "/v_" + versionCode);
		} catch (InformationException e) {
			addErrorMessage(session, e.getMessage());
			return new ModelAndView("redirect:/enterprise/pro_" + projectCode + "/v_" + versionCode);
		}
	}

	@RequestMapping(value = "pro_{projectCode}/v_{versionCode}/edit", method = RequestMethod.GET) 
	public ModelAndView editRequest(@PathVariable("projectCode") String projectCode, @PathVariable("versionCode") String versionCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) 
					throws AuthenticationException, InformationException {
		model.put("project", projectService.getProjectInfo(projectCode, auth));
		model.put("version", versionService.getProjectVersion(auth, projectCode, versionCode));
		model.put("edit", true);
		return new ModelAndView("enterprise/project/version_form", model);
	}

	@RequestMapping(value = "pro_{projectCode}/v_{versionCode}/edit", method = RequestMethod.POST) 
	public ModelAndView edit(@PathVariable("projectCode") String projectCode, @PathVariable("versionCode") String versionCode,
			@ModelAttribute("version") Version version, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) 
					throws AuthenticationException, InformationException {
		try {
			versionService.edit(auth, projectCode, versionCode, version);
			addNoticeMessage(session, "Version has been updated.");
			return new ModelAndView("redirect:/enterprise/pro_" + projectCode + "/v_" + versionCode);
		} catch (InformationException e) {
			//TODO check if model already has these attributes
			model.put("project", projectService.getProjectInfo(projectCode, auth));
			model.put("version", versionService.getProjectVersion(auth, projectCode, versionCode));
			model.put("edit", true);
			addErrorMessage(session, e.getMessage());
			return new ModelAndView("enterprise/project/version_form", model);
		}
	}

	@RequestMapping(value = "pro_{projectCode}/v_{versionCode}/delete", method = RequestMethod.GET) 
	public ModelAndView delete(@PathVariable("projectCode") String projectCode, @PathVariable("versionCode") String versionCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) 
					throws AuthenticationException, InformationException {
		try {
			versionService.delete(auth, projectCode, versionCode);
			addNoticeMessage(session, "Version has been deleted.");
			return new ModelAndView("redirect:/enterprise/pro_" + projectCode + "/v_" + versionCode);
		} catch (InformationException e) {
			addErrorMessage(session, e.getMessage());
			return new ModelAndView("redirect:/enterprise/pro_" + projectCode + "/v_" + versionCode);
		}
	}

	@RequestMapping(value = "pro_{projectCode}/version/create", method = RequestMethod.GET) 
	public ModelAndView createRequest(@PathVariable("projectCode") String projectCode,
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) 
					throws AuthenticationException, InformationException {
		model.put("project", projectService.getProjectInfo(projectCode, auth));
		model.put("version", new Version());
		model.put("edit", false);
		return new ModelAndView("enterprise/project/version_form", model);
	}

	@RequestMapping(value = "pro_{projectCode}/version/create", method = RequestMethod.POST) 
	public ModelAndView create(@PathVariable("projectCode") String projectCode,
			@ModelAttribute("version") Version version,
			@AuthenticationPrincipal EnterpriseAuthentication auth, HttpSession session, ModelMap model) 
					throws AuthenticationException, InformationException {
		try {
			versionService.create(auth, projectCode, version);
			addNoticeMessage(session, "Version has been created.");
			return new ModelAndView("redirect:/enterprise/pro_" + projectCode + "/v_" + version.getCode());
		} catch (InformationException e) {
			model.put("project", projectService.getProjectInfo(projectCode, auth));
			model.put("edit", false);
			addErrorMessage(session, e.getMessage());
			return new ModelAndView("enterprise/project/version_form");
		}
	}
	
	@RequestMapping(value = "pro_{projectCode}/v_{versionCode}", method = RequestMethod.GET) 
	public ModelAndView summary(@PathVariable("projectCode") String projectCode, @PathVariable("versionCode") String versionCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws AuthenticationException {
		Project project = projectService.getProjectInfo(projectCode, auth);
		Version version = versionService.getProjectVersion(auth, projectCode, versionCode);
		model.put("project", project);
		model.put("version", version);
		model.put("monthlySummary", versionService.getVersionMonthlySummary(project, version));
		model.put("VC_MANAGER_AUTH", null);
		return new ModelAndView("enterprise/project/version_summary");
	}
	
	@RequestMapping(value = "pro_{projectCode}/v_{versionCode}/crelated", method = RequestMethod.GET) 
	public ModelAndView related(@PathVariable("projectCode") String projectCode, @PathVariable("versionCode") String versionCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws AuthenticationException {
		Project project = projectService.getProjectInfo(projectCode, auth);
		Version version = versionService.getProjectVersion(auth, projectCode, versionCode);
		model.put("project", project);
		model.put("version", version);
		model.put("statusMap", versionService.getCaseStatusSummary(project, version.getId(), null));
		model.put("priorityMap", versionService.getCasePrioritySummary(project, version.getId(), null));
		model.put("assigneeMap", versionService.getAssigneeSummary(project, version.getId(), null));
		model.put("labelMap", versionService.getLabelSummary(project, version.getId(), null));
		model.put("componentMap", versionService.getComponentSummary(project, version.getId(), null));
		model.put("totalCase", version.getTotalRelated());
		return new ModelAndView("enterprise/project/version_relatedsummary");
	}
	
	@RequestMapping(value = "pro_{projectCode}/v_{versionCode}/crelease", method = RequestMethod.GET) 
	public ModelAndView release(@PathVariable("projectCode") String projectCode, @PathVariable("versionCode") String versionCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws AuthenticationException {
		Project project = projectService.getProjectInfo(projectCode, auth);
		Version version = versionService.getProjectVersion(auth, projectCode, versionCode);
		model.put("project", project);
		model.put("version", version);
		model.put("statusMap", versionService.getCaseStatusSummary(project, null, version.getId()));
		model.put("priorityMap", versionService.getCasePrioritySummary(project, null, version.getId()));
		model.put("assigneeMap", versionService.getAssigneeSummary(project, null, version.getId()));
		model.put("labelMap", versionService.getLabelSummary(project, null, version.getId()));
		model.put("componentMap", versionService.getComponentSummary(project, null, version.getId()));
		model.put("totalCase", version.getTotalRelease());
		return new ModelAndView("enterprise/project/version_releasesummary");
	}
	
	@RequestMapping(value = "pro_{projectCode}/v_{versionCode}/releasenote", method = RequestMethod.GET) 
	public ModelAndView releasenote(@PathVariable("projectCode") String projectCode, @PathVariable("versionCode") String versionCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws AuthenticationException {
		Project project = projectService.getProjectInfo(projectCode, auth);
		Version version = versionService.getProjectVersion(auth, projectCode, versionCode);
		model.put("project", project);
		model.put("version", version);
		model.put("releaseNote", versionService.getReleaseNote(project.getId(), version.getId()));
		return new ModelAndView("enterprise/project/version_releasenote");
	}
	
	@RequestMapping(value = "pro_{projectCode}/v_{versionCode}/case", method = RequestMethod.GET) 
	public ModelAndView cases(@PathVariable("projectCode") String projectCode, @PathVariable("versionCode") String versionCode, 
			@AuthenticationPrincipal EnterpriseAuthentication auth, ModelMap model) throws AuthenticationException {
		Project project = projectService.getProjectInfo(projectCode, auth);
		Version version = versionService.getProjectVersion(auth, projectCode, versionCode);
		model.put("project", project);
		model.put("version", version);
		model.put("relatedCase", versionService.getRelatedCase(project.getId(), version.getId(), 0, LIST_ITEMS, true, "code"));
		model.put("releaseCase", versionService.getReleaseCase(project.getId(), version.getId(), 0, LIST_ITEMS, true, "code"));
		return new ModelAndView("enterprise/project/version_cases");
	}
	
	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}
	
}
