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
import mu.codeoffice.utility.StringUtil;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public List<UserGroup> getGroups(EnterpriseAuthentication auth, boolean initialize) {
		List<UserGroup> groups = userGroupRepository.getUserGroups(auth.getEnterprise());
		if (initialize) {
			for (UserGroup group : groups) {
				group.getGlobalPermissions().size();
			}
		}
		return groups;
	}

	@Transactional(readOnly = true)
	public Page<UserGroup> getGroups(EnterpriseAuthentication auth, String name, Integer pageIndex, Integer pageSize,
			String sort, boolean ascending, boolean initialize) {
		Pageable pageable = pageSpecification(pageIndex, pageSize, sort(ascending, UserGroup.getSortColumn(sort)));
		Page<UserGroup> page = null;
		if (StringUtil.isEmptyString(name)) {
			page = userGroupRepository.getUserGroups(auth.getEnterprise(), pageable);
		} else {
			page = userGroupRepository.getUserGroups(auth.getEnterprise(), "%" + name + "%", pageable);
		}
		if (initialize) {
			for (UserGroup group : page.getContent()) {
				group.getGlobalPermissions().size();
			}
		}
		return page;
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasRole('ROLE_GLOBAL_PROJECT_ADMIN')")
	public Page<User> getUsers(EnterpriseAuthentication auth, Long userGroup, Integer pageIndex, String query) {
		return userRepository.findAll(
				groupFilter(auth.getEnterprise(), userGroup, "%" + query + "%", "%" + query + "%"),
				pageSpecification(pageIndex, 20, sort(false, "id")));
	}
	
}
