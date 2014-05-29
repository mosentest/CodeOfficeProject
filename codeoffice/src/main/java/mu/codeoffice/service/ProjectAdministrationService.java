package mu.codeoffice.service;

import java.util.List;

import javax.annotation.Resource;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.settings.ProjectPermissionScheme;
import mu.codeoffice.entity.settings.ProjectPermissionSettings;
import mu.codeoffice.entity.settings.ProjectRole;
import mu.codeoffice.repository.settings.ProjectPermissionSchemeRepository;
import mu.codeoffice.repository.settings.ProjectPermissionSettingsRepository;
import mu.codeoffice.repository.settings.ProjectRoleRepository;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;
import mu.codeoffice.security.ProjectPermission;

import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectAdministrationService {
	
	@Resource
	private ProjectRoleRepository projectRoleRepository;

	@Resource
	private ProjectPermissionSchemeRepository projectPermissionSchemeRepository;

	@Resource
	private ProjectPermissionSettingsRepository projectPermissionSettingsRepository;

	@Transactional
	public void createPermissionScheme(EnterpriseAuthentication auth, ProjectPermissionScheme scheme) throws AuthenticationException, InformationException {
		if (scheme.getName() == null || !scheme.getName().matches("[a-zA-Z]+((-)?[a-zA-Z])+")) {
			throw new InformationException("Invalid Permission Scheme Name.");
		}
		if (!projectPermissionSchemeRepository.isNameAvailable(auth.getEnterprise(), scheme.getName())) {
			throw new InformationException("Scheme Name Is Not Available.");
		}
		scheme.setId(null);
		scheme.setEnterprise(auth.getEnterprise());
		scheme.setCreator(auth.getUser());
		projectPermissionSchemeRepository.save(scheme);

		for (ProjectPermission permission : ProjectPermission.values()) {
			ProjectPermissionSettings projectPermissionSettings = new ProjectPermissionSettings();
			projectPermissionSettings.setEnterprise(auth.getEnterprise());
			projectPermissionSettings.setProjectPermissionScheme(scheme);
			projectPermissionSettings.setProjectPermission(permission);
			projectPermissionSettingsRepository.save(projectPermissionSettings);
		}
	}
	
	@Transactional
	public void deletePermissionScheme(EnterpriseAuthentication auth, String schemeName) throws AuthenticationException, InformationException {
		ProjectPermissionScheme scheme = projectPermissionSchemeRepository.getProjectPermissionScheme(auth.getEnterprise(), schemeName);
		if (scheme == null) {
			throw new EnterpriseAuthenticationException("Access denied.");
		}
		if (scheme.isDefaultScheme()) {
			throw new InformationException("Can not delete default scheme.");
		}
		if (scheme.getProjects().size() > 0) {
			throw new InformationException("Can not delete, scheme has multiple related projects.");
		}
		projectPermissionSchemeRepository.delete(scheme);
	}

	@Transactional
	public String clonePermissionScheme(EnterpriseAuthentication auth, String schemeName)
			throws AuthenticationException, InformationException {
		ProjectPermissionScheme scheme = projectPermissionSchemeRepository.getProjectPermissionScheme(auth.getEnterprise(), schemeName);
		if (scheme == null) {
			throw new EnterpriseAuthenticationException("Access denied.");
		}
		ProjectPermissionScheme clone = new ProjectPermissionScheme();
		clone.setEnterprise(auth.getEnterprise());
		clone.setName("CLONE - " + scheme.getName());
		clone.setCreator(auth.getUser());
		projectPermissionSchemeRepository.save(clone);

		for (ProjectPermission permission : ProjectPermission.values()) {
			ProjectPermissionSettings projectPermissionSettings = new ProjectPermissionSettings();
			projectPermissionSettings.setEnterprise(auth.getEnterprise());
			projectPermissionSettings.setProjectPermissionScheme(scheme);
			projectPermissionSettings.setProjectPermission(permission);
			projectPermissionSettingsRepository.save(projectPermissionSettings);
		}

		projectPermissionSchemeRepository.save(clone);
		return clone.getName();
	}
	
	@Transactional(readOnly = true)
	public ProjectPermissionScheme getProjectPermissionScheme(EnterpriseAuthentication auth, String schemeName) throws AuthenticationException {
		ProjectPermissionScheme scheme = projectPermissionSchemeRepository.getProjectPermissionScheme(auth.getEnterprise(), schemeName);
		if (scheme == null) {
			throw new EnterpriseAuthenticationException("Access Denied.");
		}
		scheme.getProjects().size();
		scheme.getProjectPermissionSettings().forEach(permission -> {
			permission.getUserGroups().size();
			permission.getUsers().size();
			permission.getProjectRoles().size();
		});
		return scheme;
	}
	
	@Transactional(readOnly = true)
	public List<ProjectPermissionScheme> getProjectPermissionSchemes(EnterpriseAuthentication auth) {
		List<ProjectPermissionScheme> schemes = projectPermissionSchemeRepository.getProjectPermissionSchemes(auth.getEnterprise());
		schemes.forEach(scheme -> scheme.getProjects().size());
		return schemes;
	}

	@Transactional(readOnly = true)
	public List<ProjectRole> getProjectRoles(EnterpriseAuthentication auth) {
		return projectRoleRepository.getProjectRoles(auth.getEnterprise());
	}

}
