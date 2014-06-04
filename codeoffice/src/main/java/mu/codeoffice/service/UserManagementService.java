package mu.codeoffice.service;

import static mu.codeoffice.query.GenericSpecifications.pageSpecification;
import static mu.codeoffice.query.GenericSpecifications.sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.dto.UserGroupDTO;
import mu.codeoffice.entity.User;
import mu.codeoffice.entity.UserGroup;
import mu.codeoffice.repository.UserRepository;
import mu.codeoffice.repository.settings.UserGroupRepository;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;
import mu.codeoffice.utility.StringUtil;

import org.springframework.data.domain.Page;
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
		if (userGroupDTO.getRemovedUser() != null) {
			Iterator<User> it = userGroup.getUsers().iterator();
			while (it.hasNext()) {
				Long itId = it.next().getId();
				for (Long id : userGroupDTO.getRemovedUser()) {
					if (itId.equals(id)) {
						it.remove();
						break;
					}
				}
			}
		}
		if (userGroupDTO.getNewUser() != null) {
			for (Long id : userGroupDTO.getNewUser()) {
				User user = userRepository.findById(auth.getEnterprise(), id);
				if (!userGroup.getUsers().contains(user)) {
					userGroup.getUsers().add(user);
				}
			}
		}
		userGroup.setUserCount(userGroup.getUsers().size());
		userGroupRepository.save(userGroup);
	}
	
	@Transactional
	public void createUserGroup(EnterpriseAuthentication auth, UserGroup userGroup) throws AuthenticationException, InformationException {
		if (StringUtil.isEmptyString(userGroup.getName()) || !userGroup.getName().matches("[a-zA-Z]+((-)?[a-zA-Z])+")) {
			throw new InformationException("Group Name Is Invalid.");
		}
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
		userGroup.getProjectPermissionSchemes().size();
		return userGroup;
	}
	
	@Transactional
	public void deleteUserGroup(EnterpriseAuthentication auth, String userGroupName) throws AuthenticationException, InformationException {
		UserGroup userGroup = userGroupRepository.getUserGroup(auth.getEnterprise(), userGroupName);
		if (userGroup.isDefaultGroup()) {
			throw new InformationException("Can not delete default Group");
		}
		userGroupRepository.delete(userGroup);
	}
	
	@Transactional(readOnly = true)
	public List<UserGroup> getUserGroups(EnterpriseAuthentication auth) 
			throws AuthenticationException {
		return userGroupRepository.getUserGroups(auth.getEnterprise());
	}
	
	@Transactional(readOnly = true)
	public Page<UserGroup> filterUserGroups(EnterpriseAuthentication auth, String name, Integer pageIndex, Integer pageSize, String sort) 
			throws AuthenticationException {
		Page<UserGroup> userGroups = userGroupRepository.getUserGroups(auth.getEnterprise(), "%" + name + "%", 
				pageSpecification(pageIndex, pageSize, sort(false, UserGroup.getSortColumn(sort))));
		for (UserGroup userGroup : userGroups.getContent()) {
			userGroup.getGlobalPermissions().size();
			userGroup.getProjectPermissionSchemes().size();
		}
		return userGroups;
	}
 	
}
