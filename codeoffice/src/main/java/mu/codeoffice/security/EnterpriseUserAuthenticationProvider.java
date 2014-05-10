package mu.codeoffice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class EnterpriseUserAuthenticationProvider implements AuthenticationProvider {

	private UserDetailsService userDetailsService;
	
	private MessageDigestPasswordEncoder passwordEncoder;
	
	public EnterpriseUserAuthenticationProvider() {
		passwordEncoder = new Md5PasswordEncoder();
	}
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String account = authentication.getName();
		String password = (String) authentication.getCredentials();
		UserDetails user = null;
		
		try {
			user = userDetailsService.loadUserByUsername(account);
		} catch (UsernameNotFoundException e) {
			throw new BadCredentialsException(e.getMessage());
		}
		password = passwordEncoder.encodePassword(password, null);
		if (!password.equals(user.getPassword())) {
			throw new BadCredentialsException("Password is incorrect.");
		}
		return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	@Autowired
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

}
