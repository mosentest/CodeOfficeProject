package mu.codeoffice.service;

import static mu.codeoffice.query.GenericSpecifications.pageSpecification;
import static mu.codeoffice.query.GenericSpecifications.sort;
import static mu.codeoffice.query.UserSpecifications.groupFilter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.dto.UserGroupDTO;
import mu.codeoffice.entity.User;
import mu.codeoffice.entity.UserGroup;
import mu.codeoffice.repository.UserRepository;
import mu.codeoffice.repository.settings.UserGroupRepository;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;
import mu.codeoffice.tag.AuthenticationUtils;
import mu.codeoffice.utility.CodeUtil;
import mu.codeoffice.utility.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserGroupService {

	@Resource
	private UserGroupRepository userGroupRepository;

	@Resource
	private UserRepository userRepository;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
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

	@Transactional
	public void update(EnterpriseAuthentication auth, String userGroupName, UserGroupDTO userGroupDTO) throws AuthenticationException, InformationException {
		UserGroup userGroup = userGroupRepository.getUserGroup(auth.getEnterprise(), userGroupName);
		if (userGroup == null) {
			throw new EnterpriseAuthenticationException("Access Denied.");
		}
		if (userGroup.isDefaultGroup()) {
			throw new InformationException("Can not update default Group");
		}
		if (userGroup.getUsers() == null) {
			userGroup.setUsers(new ArrayList<>());
		}
		if (userGroupDTO.getRemovedUsers() != null) {
			List<User> userList = userRepository.getUsers(auth.getEnterprise(), CodeUtil.toSet(userGroupDTO.getRemovedUsers()));
			userGroup.getUsers().removeAll(userList);
			for (User user : userList) {
				AuthenticationUtils.invalidateUser(auth.getEnterprise(), user.getId(), servletContext, sessionRegistry, false);
			}
		}
		if (userGroupDTO.getNewUsers() != null) {
			List<User> userList = userRepository.getUsers(auth.getEnterprise(), CodeUtil.toSet(userGroupDTO.getNewUsers()));
			userGroup.getUsers().addAll(userList);
			for (User user : userList) {
				AuthenticationUtils.invalidateUser(auth.getEnterprise(), user.getId(), servletContext, sessionRegistry, false);
			}
		}
		userGroup.setUserCount(userGroup.getUsers().size());
		userGroupRepository.save(userGroup);
	}
	
	@Transactional
	public void createUserGroup(EnterpriseAuthentication auth, UserGroup userGroup) throws AuthenticationException, InformationException {
		if (!userGroupRepository.isNameAvailable(auth.getEnterprise(), userGroup.getName())) {
			throw new InformationException("Group Name is Not Available.");
		}
		userGroup.setId(null);
		userGroup.setEnterprise(auth.getEnterprise());
		userGroup.setGlobalPermissions(null);
		userGroup.setUserCount(0);
		userGroup.setDefaultGroup(false);
		userGroupRepository.save(userGroup);
	}
	
	@Transactional(readOnly = true)
	public UserGroup getUserGroup(EnterpriseAuthentication auth, String userGroupName) throws AuthenticationException {
		UserGroup userGroup = userGroupRepository.getUserGroup(auth.getEnterprise(), userGroupName);
		if (userGroup == null) {
			throw new EnterpriseAuthenticationException("Access Denied.");
		}
		userGroup.getUsers().size();
		userGroup.getGlobalPermissions().size();
		return userGroup;
	}
	
	@Transactional
	public void deleteUserGroup(EnterpriseAuthentication auth, String userGroupName) throws AuthenticationException, InformationException {
		UserGroup userGroup = userGroupRepository.getUserGroup(auth.getEnterprise(), userGroupName);
		if (userGroup == null) {
			throw new InformationException("User Group doesn't exist.");
		}
		if (userGroup.isDefaultGroup()) {
			throw new InformationException("Can not delete default Group");
		}
		userGroupRepository.delete(userGroup);
	}
	
}
