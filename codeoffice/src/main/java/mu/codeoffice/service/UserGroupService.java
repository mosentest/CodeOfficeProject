package mu.codeoffice.service;

import java.util.List;

import javax.annotation.Resource;

import mu.codeoffice.entity.UserGroup;
import mu.codeoffice.repository.settings.UserGroupRepository;
import mu.codeoffice.security.EnterpriseAuthentication;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserGroupService {

	@Resource
	private UserGroupRepository userGroupRepository;
	
	@Transactional(readOnly = true)
	public List<UserGroup> getGroups(EnterpriseAuthentication auth) {
		return userGroupRepository.getUserGroups(auth.getEnterprise());
	}
	
}
