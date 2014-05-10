package mu.codeoffice.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mu.codeoffice.entity.EnterpriseUser;
import mu.codeoffice.repository.EnterpriseUserRepository;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseUserDetailsService implements UserDetailsService {
	
	private static final Logger logger = Logger.getLogger(EnterpriseUser.class);

	private static final String ROLE_USER = "ROLE_USER";
	private static final String ROLE_DEVELOPER = "ROLE_DEVELOPER";
	private static final String ROLE_PROJECT_MANAGER = "ROLE_PROJECT_MANAGER";
	private static final String ROLE_DESIGNER = "ROLE_DESIGNER";
	private static final String ROLE_MANAGER = "ROLE_MANAGER";
	private static final String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";
	private static final String ROLE_ADMIN = "ROLE_ADMIN";
	private static final String ROLE_HR = "ROLE_HR";

	private static final String ROLE_SYS_DEVELOPER = "ROLE_SYS_DEVELOPER";
	private static final String ROLE_SYS_TESTER = "ROLE_SYS_TESTER";
	private static final String ROLE_SYS_MANAGER = "ROLE_SYS_MANAGER";
	private static final String ROLE_SYS_ADMIN = "ROLE_SYS_ADMIN";
	
	private static final int MAX_ROLE_OFFSET = 1 << 11;
	
	private static final Map<Integer, GrantedAuthority> roleMap;
	
	static {
		roleMap = new HashMap<>();
		roleMap.put(1, new SimpleGrantedAuthority(ROLE_USER));
		roleMap.put(1 << 1, new SimpleGrantedAuthority(ROLE_EMPLOYEE));
		roleMap.put(1 << 2, new SimpleGrantedAuthority(ROLE_DESIGNER));
		roleMap.put(1 << 3, new SimpleGrantedAuthority(ROLE_DEVELOPER));
		roleMap.put(1 << 4, new SimpleGrantedAuthority(ROLE_PROJECT_MANAGER));
		roleMap.put(1 << 5, new SimpleGrantedAuthority(ROLE_MANAGER));
		roleMap.put(1 << 6, new SimpleGrantedAuthority(ROLE_HR));
		roleMap.put(1 << 7, new SimpleGrantedAuthority(ROLE_ADMIN));
		roleMap.put(1 << 8, new SimpleGrantedAuthority(ROLE_SYS_DEVELOPER));
		roleMap.put(1 << 9, new SimpleGrantedAuthority(ROLE_SYS_TESTER));
		roleMap.put(1 << 10, new SimpleGrantedAuthority(ROLE_SYS_MANAGER));
		roleMap.put(1 << 11, new SimpleGrantedAuthority(ROLE_SYS_ADMIN));
	}

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
				authorities.add(roleMap.get(i));
			}
			authority = authority >> 1;
		}
		return authorities;
	}

}
