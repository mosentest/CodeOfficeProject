package mu.codeoffice.controller;

import mu.codeoffice.entity.Project;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;
import mu.codeoffice.service.ProjectService;
import mu.codeoffice.service.VersionService;

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
public class VersionController {

	@Autowired
	private VersionService versionService;
	
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value = "pro_{project}/v_{version}", method = RequestMethod.GET) 
	public ModelAndView version(@PathVariable("project") String projectCode, @PathVariable("version") String version, 
			@AuthenticationPrincipal User user, ModelMap model) throws EnterpriseAuthenticationException {
		Project project = projectService.getProjectInfo(projectCode, (EnterpriseAuthentication) user);
		model.put("project", project);
		return new ModelAndView("enterprise/project/version");
	}
	
}
