package mu.codeoffice.service;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.dto.ProjectRoleDTO;
import mu.codeoffice.entity.User;
import mu.codeoffice.entity.settings.ProjectRole;
import mu.codeoffice.repository.UserRepository;
import mu.codeoffice.repository.settings.ProjectPermissionSchemeRepository;
import mu.codeoffice.repository.settings.ProjectRoleRepository;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.utility.CodeUtil;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectRoleService {

	@Resource
	private ProjectRoleRepository projectRoleRepository;
	
	@Resource
	private ProjectPermissionSchemeRepository projectPermissionSchemeRepository;

	@Resource
	private UserRepository userRepository;
	
	@Transactional(readOnly = true)
	public List<ProjectRole> getProjectRoles(EnterpriseAuthentication auth, boolean initialize) {
		List<ProjectRole> projectRoles = projectRoleRepository.getProjectRoles(auth.getEnterprise());
		if (initialize) {
			for (ProjectRole role : projectRoles) {
				role.getDefaultMembers().size();
			}
		}
		return projectRoles;
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public ProjectRole getProjectRole(EnterpriseAuthentication auth, String name, boolean initialize) {
		ProjectRole projectRole = projectRoleRepository.getProjectRole(auth.getEnterprise(), name);
		if (projectRole == null) {
			return null;
		}
		if (initialize) {
			projectRole.getDefaultMembers().size();
		}
		return projectRole;
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public void edit(EnterpriseAuthentication auth, ProjectRoleDTO projectRoleDTO) throws AuthenticationException, InformationException {
		ProjectRole projectRole = projectRoleRepository.getProjectRole(auth.getEnterprise(), projectRoleDTO.getId());
		if (!projectRoleRepository.isNameAvailable(auth.getEnterprise(), projectRoleDTO.getName(), projectRoleDTO.getId())) {
			throw new InformationException("Project Role Name not available");
		}
		if (projectRoleDTO.getNewUsers() != null) {
			Set<Long> idSet = CodeUtil.toSet(projectRoleDTO.getNewUsers());
			for (User user : projectRole.getDefaultMembers()) {
				if (idSet.contains(user.getId())) {
					idSet.remove(user.getId());
				}
			}
			projectRole.getDefaultMembers().addAll(userRepository.getUsers(auth.getEnterprise(), idSet));
		}
		if (projectRoleDTO.getRemovedUsers() != null) {
			projectRole.getDefaultMembers().removeAll(userRepository.getUsers(auth.getEnterprise(), CodeUtil.toSet(projectRoleDTO.getRemovedUsers())));
		}
		projectRole.setName(projectRoleDTO.getName());
		projectRole.setDescription(projectRoleDTO.getDescription());
		projectRoleRepository.save(projectRole);
	}
	
	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public void create(EnterpriseAuthentication auth, ProjectRole projectRole) throws InformationException {
		if (!projectRoleRepository.isNameAvailable(auth.getEnterprise(), projectRole.getName(), 0l)) {
			throw new InformationException("Project Role Name is not available.");
		}
		projectRole.setId(null);
		projectRole.setEnterprise(auth.getEnterprise());
		projectRoleRepository.save(projectRole);
	}
	
	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public void delete(EnterpriseAuthentication auth, String projectRole) throws InformationException {
		ProjectRole role = projectRoleRepository.getProjectRole(auth.getEnterprise(), projectRole);
		if (role == null) {
			throw new InformationException("Project Role not found.");
		}
		if (projectPermissionSchemeRepository.isProjectRoleInUse(auth.getEnterprise(), role)) {
			throw new InformationException("Can not delete, role in use.");
		}
		role.getDefaultMembers().clear();
		projectRoleRepository.delete(role);
	}
}
