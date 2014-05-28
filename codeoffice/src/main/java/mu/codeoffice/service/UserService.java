package mu.codeoffice.service;

import static mu.codeoffice.query.GenericSpecifications.pageSpecification;
import static mu.codeoffice.query.GenericSpecifications.sort;
import static mu.codeoffice.query.UserSpecifications.generic;

import javax.annotation.Resource;

import mu.codeoffice.entity.User;
import mu.codeoffice.repository.UserRepository;
import mu.codeoffice.repository.SubmenuRepository;
import mu.codeoffice.security.EnterpriseAuthentication;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	@Resource
	private UserRepository userRepository;
	
	@Resource
	private SubmenuRepository submenuRepository;
	
	@Transactional(readOnly = true)
	public Page<User> getUser(EnterpriseAuthentication auth, 
			Long userGroup, Long globalPermission, Long projectPermission,
			Integer pageIndex, Integer pageSize, String sort, boolean ascending) {
		Page<User> users = userRepository.findAll(
				generic(auth.getEnterprise(), userGroup, globalPermission, projectPermission), 
				pageSpecification(pageIndex, pageSize, sort(ascending, User.getSortColumn(sort))));
		for (User user : users) {
			if (userGroup != null) { user.getUserGroups().size(); }
			if (globalPermission != null) { user.getProjectPermissions().size(); }
			if (projectPermission != null) { user.getProjectPermissions().size(); }
		}
		return users;
	}
	
	@Transactional
	public void update(User user) {
		if (user.getId() != null) {
			userRepository.save(user);
		}
	}
	
}
