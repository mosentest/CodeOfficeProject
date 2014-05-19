package mu.codeoffice.service;

import java.util.List;

import javax.annotation.Resource;

import mu.codeoffice.entity.ProjectRole;
import mu.codeoffice.repository.ProjectRoleRepository;
import mu.codeoffice.repository.RoleGroupRepository;
import mu.codeoffice.security.EnterpriseAuthentication;

import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleGroupService {
	
	@Resource
	private RoleGroupRepository roleGroupRepository;
	
	@Resource
	private ProjectRoleRepository projectRoleRepository;
	
	@Transactional(readOnly = true)
	public List<ProjectRole> getAvailableRoles(EnterpriseAuthentication auth, String projectCode) throws AuthenticationException {
		List<ProjectRole> roles = projectRoleRepository.getRoles(auth.getEnterprise());
		List<ProjectRole> projectRoles = roleGroupRepository.getProjectRoles(auth.getEnterprise(), projectCode);
		roles.removeAll(projectRoles);
		return roles;
	}

	@Transactional(readOnly = true)
	public List<ProjectRole> getRoles(EnterpriseAuthentication auth, String projectCode) throws AuthenticationException {
		return roleGroupRepository.getProjectRoles(auth.getEnterprise(), projectCode);
	}
	
}
