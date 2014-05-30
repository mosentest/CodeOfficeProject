package mu.codeoffice.security;

import java.util.List;

import javax.annotation.Resource;

import mu.codeoffice.entity.User;
import mu.codeoffice.repository.UserRepository;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseUserDetailsService implements UserDetailsService {
	
	private static final Logger logger = Logger.getLogger(EnterpriseUserDetailsService.class);

	@Resource
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
		UserDetails userDetails = null;
		try {
			User user = userRepository.findByAccount(account, account);
			if (user == null) {
				throw new UsernameNotFoundException("User '" + account + "' doesn't exist");
			}
			user.getEnterprise().getId();
			userDetails = new EnterpriseAuthentication(user.getEnterprise(), user, 
					user.getAccount(), user.getPassword(),
					true, true, true, true, grantAuthorities(user));
		} catch (UsernameNotFoundException e) {
			throw e;
		} catch (Exception e) {
            logger.error("Error in retrieving user");  
            throw new UsernameNotFoundException("Error in retrieving user"); 
		}
		return userDetails;
	}
	
	private List<GrantedAuthority> grantAuthorities(User user) {
		List<GrantedAuthority> globalAuthorities = EnterpriseAuthority.getGrantedAuthorities(GlobalPermission.getPermissions(user.getGlobalPermissionValue()));
		logger.debug(user.getAccount() + " granted global authorities: ");
		for (GrantedAuthority grantedAuthority: globalAuthorities) {
			logger.debug("Global: " + grantedAuthority.getAuthority());
		}
		return globalAuthorities;
	}

}
