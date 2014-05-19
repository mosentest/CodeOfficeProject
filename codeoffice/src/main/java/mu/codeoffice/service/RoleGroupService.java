package mu.codeoffice.service;

import java.util.List;

import javax.annotation.Resource;

import mu.codeoffice.entity.ProjectRole;
import mu.codeoffice.repository.RoleGroupRepository;
import mu.codeoffice.security.EnterpriseAuthentication;

import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleGroupService {
	
	@Resource
	private RoleGroupRepository roleGroupRepository;

	@Transactional(readOnly = true)
	public List<ProjectRole> getRoles(EnterpriseAuthentication auth, String projectCode) throws AuthenticationException {
		return roleGroupRepository.getProjectRoles(auth.getEnterprise(), projectCode);
	}
	
}
