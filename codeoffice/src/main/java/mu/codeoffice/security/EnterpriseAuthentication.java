package mu.codeoffice.security;

import java.util.Collection;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.EnterpriseUser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class EnterpriseAuthentication extends User {

	private static final long serialVersionUID = 6978257266007790166L;
	
	private Enterprise enterprise;
	
	private EnterpriseUser enterpriseUser;

	public EnterpriseAuthentication(Enterprise enterprise, EnterpriseUser enterpriseUser, String username, String password,
			boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
		this.enterprise = enterprise;
		this.enterpriseUser = enterpriseUser;
	}
	
	public Enterprise getEnterprise() {
		return enterprise;
	}
	
	public EnterpriseUser getEnterpriseUser() {
		return enterpriseUser;
	}
	
	public String getFullname() {
		return enterpriseUser.getFullName();
	}
	
	public boolean authorized(GrantedAuthority authority) {
		return getAuthorities().contains(authority);
	}

}
