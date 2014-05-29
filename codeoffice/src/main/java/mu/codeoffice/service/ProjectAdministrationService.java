package mu.codeoffice.service;

import javax.annotation.Resource;

import mu.codeoffice.repository.settings.ProjectPermissionSchemeRepository;
import mu.codeoffice.repository.settings.ProjectPermissionSettingsRepository;
import mu.codeoffice.repository.settings.ProjectRoleRepository;

import org.springframework.stereotype.Service;

@Service
public class ProjectAdministrationService {
	
	@Resource
	private ProjectRoleRepository projectRoleRepository;

	@Resource
	private ProjectPermissionSchemeRepository projectPermissionSchemeRepository;

	@Resource
	private ProjectPermissionSettingsRepository projectPermissionSettingsRepository;

}
