package mu.codeoffice.security;

import java.util.Collection;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.User;

import org.springframework.security.core.GrantedAuthority;

public class EnterpriseAuthentication extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 6978257266007790166L;
	
	private Enterprise enterprise;
	
	private User user;

	public EnterpriseAuthentication(Enterprise enterprise, User user, String username, String password,
			boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
		this.enterprise = enterprise;
		this.user = user;
	}
	
	public Enterprise getEnterprise() {
		return enterprise;
	}
	
	public User getUser() {
		return user;
	}
	
	public String getFullname() {
		return user.getFullName();
	}
	
	public boolean authorized(GrantedAuthority authority) {
		return getAuthorities().contains(authority);
	}

}
