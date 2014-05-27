package mu.codeoffice.service;

import static mu.codeoffice.query.GenericSpecifications.pageSpecification;
import static mu.codeoffice.query.GenericSpecifications.sort;
import static mu.codeoffice.query.UserGroupSpecifications.all;
import static mu.codeoffice.query.UserSpecifications.search;

import java.util.List;

import javax.annotation.Resource;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.EnterpriseUser;
import mu.codeoffice.entity.UserGroup;
import mu.codeoffice.repository.EnterpriseUserRepository;
import mu.codeoffice.repository.settings.EnterpriseUserGroupRepository;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;
import mu.codeoffice.utility.StringUtil;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserManagementService {

	@Resource
	private EnterpriseUserGroupRepository enterpriseUserGroupRepository;
	
	@Resource
	private EnterpriseUserRepository enterpriseUserRepository;

	@Transactional
	@CacheEvict(value = "userGroupsCache", key = "#auth.enterprise.id")
	public void createUserGroup(EnterpriseAuthentication auth, UserGroup userGroup) throws AuthenticationException, InformationException {
		if (StringUtil.isEmptyString(userGroup.getName()) || !userGroup.getName().matches("[a-zA-Z]+((-)?[a-zA-Z])+")) {
			throw new InformationException("Group Name Is Invalid.");
		}
		if (!enterpriseUserGroupRepository.isNameAvailable(auth.getEnterprise(), userGroup.getName())) {
			throw new InformationException("Group Name is Not Available.");
		}
		userGroup.setId(null);
		userGroup.setEnterprise(auth.getEnterprise());
		userGroup.setGlobalPermissions(null);
		userGroup.setProjectPermissions(null);
		userGroup.setUserCount(0);
		userGroup.setDefaultGroup(false);
		enterpriseUserGroupRepository.save(userGroup);
	}
	
	@Transactional(readOnly = true)
	public UserGroup getUserGroup(EnterpriseAuthentication auth, String userGroupName) throws AuthenticationException {
		UserGroup userGroup = enterpriseUserGroupRepository.getUserGroup(auth.getEnterprise(), userGroupName);
		if (userGroup == null) {
			throw new EnterpriseAuthenticationException("Access Denied.");
		}
		userGroup.getUsers().size();
		userGroup.getGlobalPermissions().size();
		userGroup.getProjectPermissions().size();
		return userGroup;
	}
	
	@Transactional
	@CacheEvict(value = "userGroupsCache", key = "#auth.enterprise.id")
	public void deleteUserGroup(EnterpriseAuthentication auth) throws AuthenticationException, InformationException {
		
	}
	
	@Transactional
	@CacheEvict(value = "userGroupsCache", key = "#auth.enterprise.id")
	public void editUserGroup(EnterpriseAuthentication auth) throws AuthenticationException, InformationException {
		
	}
	
	@Transactional(readOnly = true)
	public Page<EnterpriseUser> filterEnterpriseUsers(EnterpriseAuthentication auth, String account, String name, Long groupFilter,
			Integer pageIndex, Integer pageSize, String sort) {
		Page<EnterpriseUser> users = enterpriseUserRepository.findAll(
				search(auth.getEnterprise(), account, name, groupFilter), 
				pageSpecification(pageIndex, pageSize, sort(false, EnterpriseUser.getSortColumn(sort))));
		for (EnterpriseUser user : users) {
			user.getUserGroups().size();
			user.getGlobalPermissions();
		}
		return users;
	}
	
	@Transactional(readOnly = true)
	@Cacheable(value = "userGroupsCache", key = "#auth.enterprise.id")
	public List<UserGroup> getUserGroups(EnterpriseAuthentication auth) 
			throws AuthenticationException {
		return enterpriseUserGroupRepository.getEnterpriseUserGroups(auth.getEnterprise());
	}
	
	@Transactional(readOnly = true)
	public Page<UserGroup> filterUserGroups(EnterpriseAuthentication auth, String name, Integer pageIndex, Integer pageSize, String sort) 
			throws AuthenticationException {
		Page<UserGroup> userGroups = enterpriseUserGroupRepository.findAll(
				all(auth.getEnterprise(), name), 
				pageSpecification(pageIndex, pageSize, sort(false, UserGroup.getSortColumn(sort))));
		for (UserGroup userGroup : userGroups.getContent()) {
			userGroup.getGlobalPermissions().size();
		}
		return userGroups;
	}
 	
}
