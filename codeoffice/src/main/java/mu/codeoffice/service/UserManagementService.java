package mu.codeoffice.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.dto.UserGroupDTO;
import mu.codeoffice.entity.UserGroup;
import mu.codeoffice.repository.UserRepository;
import mu.codeoffice.repository.settings.UserGroupRepository;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;
import mu.codeoffice.utility.CodeUtil;

import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserManagementService {

	@Resource
	private UserGroupRepository userGroupRepository;
	
	@Resource
	private UserRepository userRepository;

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
			userGroup.getUsers().removeAll(userRepository.getUsers(auth.getEnterprise(), CodeUtil.toSet(userGroupDTO.getRemovedUsers())));
		}
		if (userGroupDTO.getNewUsers() != null) {
			userGroup.getUsers().addAll(userRepository.getUsers(auth.getEnterprise(), CodeUtil.toSet(userGroupDTO.getNewUsers())));
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
