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
	
	public boolean hasEnterpriseAuthority(Enterprise enterprise) {
		return enterprise != null && this.enterprise.getId().equals(enterprise.getId());
	}
	
	public boolean hasProjectAuthority() {
		boolean authorized = false;
		for (GrantedAuthority authority : getAuthorities()) {
			if (authority.getAuthority().equals(EnterpriseAuthority.ROLE_ADMIN.getAuthority()) || 
					authority.getAuthority().equals(EnterpriseAuthority.ROLE_MANAGER.getAuthority()) ||
					authority.getAuthority().equals(EnterpriseAuthority.ROLE_PROJECT_MANAGER.getAuthority())) {
				authorized = true;
				break;
			}
		}
		return authorized;
	}

}
