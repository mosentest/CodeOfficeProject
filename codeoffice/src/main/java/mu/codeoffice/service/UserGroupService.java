package mu.codeoffice.service;

import static mu.codeoffice.query.GenericSpecifications.pageSpecification;
import static mu.codeoffice.query.GenericSpecifications.sort;
import static mu.codeoffice.query.UserSpecifications.groupFilter;

import java.util.List;

import javax.annotation.Resource;

import mu.codeoffice.entity.User;
import mu.codeoffice.entity.UserGroup;
import mu.codeoffice.repository.UserRepository;
import mu.codeoffice.repository.settings.UserGroupRepository;
import mu.codeoffice.security.EnterpriseAuthentication;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserGroupService {

	@Resource
	private UserGroupRepository userGroupRepository;

	@Resource
	private UserRepository userRepository;
	
	@Transactional(readOnly = true)
	public List<UserGroup> getGroups(EnterpriseAuthentication auth) {
		return userGroupRepository.getUserGroups(auth.getEnterprise());
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public Page<User> getUsers(EnterpriseAuthentication auth, Long userGroup, Integer pageIndex, String query) {
		return userRepository.findAll(
				groupFilter(auth.getEnterprise(), userGroup, "%" + query + "%", "%" + query + "%"),
				pageSpecification(pageIndex, 20, sort(false, "id")));
	}
	
}
