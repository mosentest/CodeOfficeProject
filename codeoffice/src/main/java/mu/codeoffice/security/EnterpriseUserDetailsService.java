package mu.codeoffice.security;

import java.util.List;

import javax.annotation.Resource;

import mu.codeoffice.entity.EnterpriseUser;
import mu.codeoffice.repository.EnterpriseUserRepository;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseUserDetailsService implements UserDetailsService {
	
	private static final Logger logger = Logger.getLogger(EnterpriseUser.class);

	@Resource
	private EnterpriseUserRepository enterpriseUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
		UserDetails userDetails = null;
		try {
			EnterpriseUser enterpriseUser = enterpriseUserRepository.findByAccount(account, account);
			if (enterpriseUser == null) {
				throw new UsernameNotFoundException("User '" + account + "' doesn't exist");
			}
			enterpriseUser.getEnterprise().getId();
			userDetails = new EnterpriseAuthentication(enterpriseUser.getEnterprise(), enterpriseUser, 
					enterpriseUser.getAccount(), enterpriseUser.getPassword(),
					true, true, true, true, grantAuthorities(enterpriseUser.getGlobalPermissionValue(), enterpriseUser.getProjectPermissionValue()));
		} catch (UsernameNotFoundException e) {
			throw e;
		} catch (Exception e) {
            logger.error("Error in retrieving user");  
            throw new UsernameNotFoundException("Error in retrieving user"); 
		}
		return userDetails;
	}
	
	private List<GrantedAuthority> grantAuthorities(int globalPermissionValue, int projectPermissionValue) {
		List<GrantedAuthority> globalAuthorities = EnterpriseAuthority.getGrantedAuthorities(GlobalPermission.getPermissions(globalPermissionValue));
		List<GrantedAuthority> projectAuthorities = EnterpriseAuthority.getGrantedAuthorities(ProjectPermission.getPermissions(projectPermissionValue));
		globalAuthorities.addAll(projectAuthorities);
		return globalAuthorities;
	}

}
