package mu.codeoffice.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class EnterpriseAuthority {
	
	private static final Map<String, GrantedAuthority> authMap;
	
	static {
		authMap = new HashMap<>();
		for (GlobalPermission permission : GlobalPermission.values()) {
			authMap.put(permission.getAuthKey(), new SimpleGrantedAuthority(permission.getAuthKey()));
		}
		for (ProjectPermission permission : ProjectPermission.values()) {
			authMap.put(permission.getAuthKey(), new SimpleGrantedAuthority(permission.getAuthKey()));
		}
	}
	
	public static List<GrantedAuthority> getGrantedAuthorities(List<? extends Permission> permissions) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		permissions.forEach(permission -> authorities.add(authMap.get(permission.getAuthKey())));
		return authorities;
	}
	
}
