package mu.codeoffice.service;

import static mu.codeoffice.query.GenericSpecifications.pageSpecification;
import static mu.codeoffice.query.GenericSpecifications.sort;
import static mu.codeoffice.query.UserSpecifications.groupFilter;
import static mu.codeoffice.query.UserSpecifications.search;

import javax.annotation.Resource;

import mu.codeoffice.entity.User;
import mu.codeoffice.repository.UserRepository;
import mu.codeoffice.security.EnterpriseAuthentication;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	@Resource
	private UserRepository userRepository;
	
	@Transactional(readOnly = true)
	public Page<User> basicSearch(EnterpriseAuthentication auth, String query) {
		return userRepository.findAll(
				search(auth.getEnterprise(), query), 
				pageSpecification(0, 20, sort(false, User.getSortColumn("firstName"))));
	}
	
	@Transactional(readOnly = true)
	public Page<User> groupSearch(EnterpriseAuthentication auth, Long group, String account, String name,
			Integer pageIndex, Integer pageSize, String sort, boolean ascending, boolean loadProperties) {
		Page<User> users = userRepository.findAll(
				groupFilter(auth.getEnterprise(), group, account, name), 
				pageSpecification(pageIndex, pageSize, sort(ascending, User.getSortColumn(sort))));
		if (loadProperties) {
			for (User user : users.getContent()) {
				user.getUserGroups().size();
				user.getGlobalPermissions().size();
			}
		}
		return users;
	}
	
	@Transactional(readOnly = true)
	public Page<User> roleSearch(EnterpriseAuthentication auth, Long group, String account, String name,
			Integer pageIndex, Integer pageSize, String sort, boolean ascending, boolean loadProperties) {
		Page<User> users = userRepository.findAll(
				groupFilter(auth.getEnterprise(), group, account, name), 
				pageSpecification(pageIndex, pageSize, sort(false, User.getSortColumn(sort))));
		if (loadProperties) {
			for (User user : users.getContent()) {
				user.getUserGroups().size();
				user.getGlobalPermissions().size();
			}
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
