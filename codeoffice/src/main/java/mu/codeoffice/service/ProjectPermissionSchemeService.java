package mu.codeoffice.service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.User;
import mu.codeoffice.entity.UserGroup;
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
import mu.codeoffice.utility.CodeUtil;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectPermissionSchemeService {

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
	public List<ProjectPermissionScheme> getProjectPermissionSchemes(EnterpriseAuthentication auth, boolean initialize) {
		List<ProjectPermissionScheme> schemes = projectPermissionSchemeRepository.getProjectPermissionSchemes(auth.getEnterprise());
		if (initialize) {
			schemes.forEach(scheme -> scheme.getProjects().size());
		}
		return schemes;
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public void associate(EnterpriseAuthentication auth, String scheme, ProjectPermission permission, 
			Long[] groups, Long[] roles, Long[] users) throws InformationException {
		ProjectPermissionSettings settings = projectPermissionSettingsRepository.getProjectPermissionSettings(auth.getEnterprise(), scheme, permission);
		if (settings == null) {
			throw new InformationException("Project Permission Settings doesn't exist.");
		}
		if (groups != null) {
			Set<Long> groupSet = CodeUtil.toSet(groups);
			for (UserGroup group : settings.getUserGroups()) {
				if (groupSet.contains(group.getId())) {
					groupSet.remove(group.getId());
				}
			}
			if (groupSet.size() > 0) {
				settings.getUserGroups().addAll(userGroupRepository.getUserGroups(auth.getEnterprise(), groupSet));
			}
		}
		if (roles != null) {
			Set<Long> roleSet = CodeUtil.toSet(roles);
			for (ProjectRole role : settings.getProjectRoles()) {
				if (roleSet.contains(role.getId())) {
					roleSet.remove(role.getId());
				}
			}
			if (roleSet.size() > 0) {
				settings.getProjectRoles().addAll(projectRoleRepository.getProjectRoles(auth.getEnterprise(), roleSet));
			}
		}
		if (users != null) {
			Set<Long> userSet = CodeUtil.toSet(users);
			for (User user : settings.getUsers()) {
				if (userSet.contains(user.getId())) {
					userSet.remove(user.getId());
				}
			}
			if (userSet.size() > 0) {
				settings.getUsers().addAll(userRepository.getUsers(auth.getEnterprise(), userSet));
			}
		}
		projectPermissionSettingsRepository.save(settings);
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public void create(EnterpriseAuthentication auth, ProjectPermissionScheme scheme) throws InformationException {
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
	public String clone(EnterpriseAuthentication auth, String schemeName) throws InformationException {
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
			Set<Long> groupSet = settings.getUserGroups()
					.stream()
					.filter(g -> g.getId() != null)
					.map(g -> g.getId())
					.collect(Collectors.toSet());
			Set<Long> userSet = settings.getUsers()
					.stream()
					.filter(u -> u.getId() != null)
					.map(u -> u.getId())
					.collect(Collectors.toSet());
			Set<Long> roleSet = settings.getProjectRoles()
					.stream()
					.filter(r -> r.getId() != null)
					.map(r -> r.getId())
					.collect(Collectors.toSet());
			if (groupSet.size() > 0) {
				projectPermissionSettings.setUserGroups(userGroupRepository.getUserGroups(auth.getEnterprise(), groupSet));
			}
			if (userSet.size() > 0) {
				projectPermissionSettings.setUsers(userRepository.getUsers(auth.getEnterprise(), userSet));
			}
			if (roleSet.size() > 0) {
				projectPermissionSettings.setProjectRoles(projectRoleRepository.getProjectRoles(auth.getEnterprise(), roleSet));
			}
			projectPermissionSettingsRepository.save(projectPermissionSettings);
		}

		projectPermissionSchemeRepository.save(clone);
		return clone.getName();
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public void removeGroup(EnterpriseAuthentication auth, String scheme, ProjectPermission permission, Long group) throws InformationException {
		ProjectPermissionSettings settings = projectPermissionSettingsRepository.getProjectPermissionSettings(auth.getEnterprise(), scheme, permission);
		if (settings == null) {
			throw new InformationException("Project Permission Settings doesn't exist.");
		}
		if (settings.getUserGroups().remove(userGroupRepository.getUserGroup(auth.getEnterprise(), group))) {
			projectPermissionSettingsRepository.save(settings);
		} else {
			throw new InformationException("User Group doesn't exist.");
		}
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public void removeUser(EnterpriseAuthentication auth, String scheme, ProjectPermission permission, Long user) throws InformationException {
		ProjectPermissionSettings settings = projectPermissionSettingsRepository.getProjectPermissionSettings(auth.getEnterprise(), scheme, permission);
		if (settings == null) {
			throw new InformationException("Project Permission Settings doesn't exist.");
		}
		if (settings.getUsers().remove(userRepository.getUser(auth.getEnterprise(), user))) {
			projectPermissionSettingsRepository.save(settings);
		} else {
			throw new InformationException("User doesn't exist.");
		}
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public void removeRole(EnterpriseAuthentication auth, String scheme, ProjectPermission permission, Long role) throws InformationException {
		ProjectPermissionSettings settings = projectPermissionSettingsRepository.getProjectPermissionSettings(auth.getEnterprise(), scheme, permission);
		if (settings == null) {
			throw new InformationException("Project Permission Settings doesn't exist.");
		}
		if (settings.getProjectRoles().remove(projectRoleRepository.getProjectRole(auth.getEnterprise(), role))) {
			projectPermissionSettingsRepository.save(settings);
		} else {
			throw new InformationException("Project Role doesn't exist.");
		}
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public void resetAll(EnterpriseAuthentication auth, String scheme) throws InformationException {
		ProjectPermissionScheme permissionScheme = projectPermissionSchemeRepository.getProjectPermissionScheme(auth.getEnterprise(), scheme);
		if (permissionScheme == null) {
			throw new InformationException("Project Permission Scheme doesn't exist.");
		}
		for (ProjectPermissionSettings settings : permissionScheme.getProjectPermissionSettings()) {
			settings.getUserGroups().clear();
			settings.getUsers().clear();
			settings.getProjectRoles().clear();
			projectPermissionSettingsRepository.save(settings);
		}
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public void reset(EnterpriseAuthentication auth, String scheme, ProjectPermission permission) throws InformationException {
		ProjectPermissionSettings settings = projectPermissionSettingsRepository.getProjectPermissionSettings(auth.getEnterprise(), scheme, permission);
		if (settings == null) {
			throw new InformationException("Project Permission Settings doesn't exist.");
		}
		settings.getUserGroups().clear();
		settings.getUsers().clear();
		settings.getProjectRoles().clear();
		projectPermissionSettingsRepository.save(settings);
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
}
