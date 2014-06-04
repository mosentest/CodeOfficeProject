package mu.codeoffice.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.settings.ProjectPermissionScheme;
import mu.codeoffice.entity.settings.ProjectPermissionSettings;
import mu.codeoffice.entity.settings.ProjectRole;
import mu.codeoffice.repository.UserRepository;
import mu.codeoffice.repository.settings.ProjectPermissionSchemeRepository;
import mu.codeoffice.repository.settings.ProjectPermissionSettingsRepository;
import mu.codeoffice.repository.settings.ProjectRoleRepository;
import mu.codeoffice.repository.settings.UserGroupRepository;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.ProjectPermission;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectAdministrationService {

	@Resource
	private ProjectPermissionSchemeRepository projectPermissionSchemeRepository;

	@Resource
	private ProjectPermissionSettingsRepository projectPermissionSettingsRepository;

	@Resource
	private UserGroupRepository userGroupRepository;

	@Resource
	private UserRepository userRepository;
	
	@Resource
	private ProjectRoleRepository projectRoleRepository;

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public void addGroup(EnterpriseAuthentication auth, Long permission, Long group) throws AuthenticationException, InformationException {
		
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public void addUser(EnterpriseAuthentication auth, Long permission, Long user) throws AuthenticationException, InformationException {
		
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public void addProjectrole(EnterpriseAuthentication auth, Long permission, Long projectRole) throws AuthenticationException, InformationException {
		
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public void removeGroup(EnterpriseAuthentication auth, Long permission, Long group) throws AuthenticationException, InformationException {
		
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public void removeUser(EnterpriseAuthentication auth, Long permission, Long user) throws AuthenticationException, InformationException {
		
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public void removeProjectrole(EnterpriseAuthentication auth, Long permission, Long projectRole) throws AuthenticationException, InformationException {
		
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public void reset(EnterpriseAuthentication auth, Long permission) throws AuthenticationException, InformationException {
		
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public void create(EnterpriseAuthentication auth, ProjectPermissionScheme scheme) throws AuthenticationException, InformationException {
		if (!projectPermissionSchemeRepository.isNameAvailable(auth.getEnterprise(), scheme.getName(), 0l)) {
			throw new InformationException("Scheme Name Is Not Available.");
		}
		scheme.setId(null);
		scheme.setEnterprise(auth.getEnterprise());
		scheme.setCreator(auth.getUser());
		scheme.setModified(new Date());
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
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public void deletePermissionScheme(EnterpriseAuthentication auth, String schemeName) throws InformationException {
		ProjectPermissionScheme scheme = projectPermissionSchemeRepository.getProjectPermissionScheme(auth.getEnterprise(), schemeName);
		if (scheme == null) {
			throw new InformationException("Project Permission Scheme not found.");
		}
		if (scheme.getProjects().size() > 0) {
			throw new InformationException("Can not delete, scheme has multiple related projects.");
		}
		projectPermissionSchemeRepository.delete(scheme);
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public String clone(EnterpriseAuthentication auth, String schemeName) throws AuthenticationException, InformationException {
		ProjectPermissionScheme original = projectPermissionSchemeRepository.getProjectPermissionScheme(auth.getEnterprise(), schemeName);
		if (original == null) {
			return null;
		}
		if (!projectPermissionSchemeRepository.isNameAvailable(auth.getEnterprise(), "CLONE - " + schemeName, 0l)) {
			throw new InformationException("Scheme Name Is Not Available.");
		}
		ProjectPermissionScheme clone = new ProjectPermissionScheme();
		clone.setEnterprise(auth.getEnterprise());
		clone.setName("CLONE - " + original.getName());
		clone.setCreator(auth.getUser());
		projectPermissionSchemeRepository.save(clone);
		
		for (ProjectPermissionSettings settings : original.getProjectPermissionSettings()) {
			ProjectPermissionSettings projectPermissionSettings = new ProjectPermissionSettings();
			projectPermissionSettings.setEnterprise(auth.getEnterprise());
			projectPermissionSettings.setProjectPermissionScheme(clone);
			projectPermissionSettings.setProjectPermission(settings.getProjectPermission());
			List<Long> groupList = settings.getUserGroups()
					.stream()
					.filter(g -> g.getId() != null)
					.map(g -> g.getId())
					.collect(Collectors.toList());
			List<Long> userList = settings.getUsers()
					.stream()
					.filter(u -> u.getId() != null)
					.map(u -> u.getId())
					.collect(Collectors.toList());
			List<Long> roleList = settings.getProjectRoles()
					.stream()
					.filter(r -> r.getId() != null)
					.map(r -> r.getId())
					.collect(Collectors.toList());
			projectPermissionSettings.setUserGroups(userGroupRepository.getUserGroups(auth.getEnterprise(), groupList));
			projectPermissionSettings.setUsers(userRepository.getUsers(auth.getEnterprise(), userList));
			projectPermissionSettings.setProjectRoles(projectRoleRepository.getProjectRoles(auth.getEnterprise(), roleList));
			projectPermissionSettingsRepository.save(projectPermissionSettings);
		}

		projectPermissionSchemeRepository.save(clone);
		return clone.getName();
	}
	
	@Transactional(readOnly = true)
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public ProjectPermissionScheme getProjectPermissionScheme(EnterpriseAuthentication auth, String schemeName) throws AuthenticationException {
		ProjectPermissionScheme scheme = projectPermissionSchemeRepository.getProjectPermissionScheme(auth.getEnterprise(), schemeName);
		if (scheme == null) {
			return null;
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
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public List<ProjectPermissionScheme> getProjectPermissionSchemes(EnterpriseAuthentication auth) {
		List<ProjectPermissionScheme> schemes = projectPermissionSchemeRepository.getProjectPermissionSchemes(auth.getEnterprise());
		schemes.forEach(scheme -> scheme.getProjects().size());
		return schemes;
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public ProjectRole getProjectRoles(EnterpriseAuthentication auth, String name) {
		ProjectRole projectRole = projectRoleRepository.getProjectRole(auth.getEnterprise(), name);
		if (projectRole == null) {
			return null;
		}
		projectRole.getDefaultMembers().size();
		projectRole.getProjectPermissionSettings().size();
		return projectRole;
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public List<ProjectRole> getProjectRoles(EnterpriseAuthentication auth) {
		List<ProjectRole> projectRoles = projectRoleRepository.getProjectRoles(auth.getEnterprise());
		for (ProjectRole role : projectRoles) {
			role.getDefaultMembers().size();
			role.getProjectPermissionSettings().size();
		}
		return projectRoles;
	}

}
