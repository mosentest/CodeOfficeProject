package mu.codeoffice.security;

import java.util.ArrayList;
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
	
	private static final int MAX_ROLE_OFFSET = 1 << 11;

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
					true, true, true, true, grantAuthorities(enterpriseUser.getAuthority()));
		} catch (UsernameNotFoundException e) {
			throw e;
		} catch (Exception e) {
            logger.error("Error in retrieving user");  
            throw new UsernameNotFoundException("Error in retrieving user"); 
		}
		return userDetails;
	}
	
	private List<GrantedAuthority> grantAuthorities(Integer authority) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (int i = 1; i <= MAX_ROLE_OFFSET; i *= 2) {
			if ((authority & 1) == 1) {
				authorities.add(EnterpriseAuthority.getAuthority(i));
			}
			authority = authority >> 1;
		}
		return authorities;
	}

}
