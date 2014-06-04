package mu.codeoffice.service;

import static mu.codeoffice.query.GenericSpecifications.pageSpecification;
import static mu.codeoffice.query.GenericSpecifications.sort;

import java.util.List;

import javax.annotation.Resource;

import mu.codeoffice.entity.User;
import mu.codeoffice.entity.settings.ProjectRole;
import mu.codeoffice.repository.settings.ProjectRoleRepository;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.utility.StringUtil;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectRoleService {

	@Resource
	private ProjectRoleRepository projectRoleRepository;
	
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
	public ProjectRole getProjectRole(EnterpriseAuthentication auth, String name) {
		ProjectRole projectRole = projectRoleRepository.getProjectRole(auth.getEnterprise(), name);
		if (projectRole == null) {
			return null;
		}
		projectRole.getDefaultMembers().size();
		return projectRole;
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public Page<User> getUsers(EnterpriseAuthentication auth, Long projectRole, Integer pageIndex, String query) {
		Pageable pageable = pageSpecification(pageIndex, 20, sort(false, "id"));
		if (StringUtil.isEmptyString(query)) {
			return projectRoleRepository.getUsers(auth.getEnterprise(), projectRole, pageable);
		} else {
			return projectRoleRepository.getUsers(auth.getEnterprise(), projectRole, "%" + query + "%", pageable);
		}
	}
}
