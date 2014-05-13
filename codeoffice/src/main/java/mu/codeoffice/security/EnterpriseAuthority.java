package mu.codeoffice.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class EnterpriseAuthority {

	public static final SimpleGrantedAuthority ROLE_USER = new SimpleGrantedAuthority("ROLE_USER");
	public static final SimpleGrantedAuthority ROLE_DEVELOPER = new SimpleGrantedAuthority("ROLE_DEVELOPER");
	public static final SimpleGrantedAuthority ROLE_PROJECT_MANAGER = new SimpleGrantedAuthority("ROLE_PROJECT_MANAGER");
	public static final SimpleGrantedAuthority ROLE_DESIGNER = new SimpleGrantedAuthority("ROLE_DESIGNER");
	public static final SimpleGrantedAuthority ROLE_MANAGER = new SimpleGrantedAuthority("ROLE_MANAGER");
	public static final SimpleGrantedAuthority ROLE_EMPLOYEE = new SimpleGrantedAuthority("ROLE_EMPLOYEE");
	public static final SimpleGrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority("ROLE_ADMIN");
	public static final SimpleGrantedAuthority ROLE_HR = new SimpleGrantedAuthority("ROLE_HR");

	public static final SimpleGrantedAuthority ROLE_SYS_DEVELOPER = new SimpleGrantedAuthority("ROLE_SYS_DEVELOPER");
	public static final SimpleGrantedAuthority ROLE_SYS_TESTER = new SimpleGrantedAuthority("ROLE_SYS_TESTER");
	public static final SimpleGrantedAuthority ROLE_SYS_MANAGER = new SimpleGrantedAuthority("ROLE_SYS_MANAGER");
	public static final SimpleGrantedAuthority ROLE_SYS_ADMIN = new SimpleGrantedAuthority("ROLE_SYS_ADMIN");
	
	private static final Map<Integer, GrantedAuthority> roleMap;
	
	static {
		roleMap = new HashMap<>();
		roleMap.put(1, EnterpriseAuthority.ROLE_USER);
		roleMap.put(1 << 1, EnterpriseAuthority.ROLE_EMPLOYEE);
		roleMap.put(1 << 2, EnterpriseAuthority.ROLE_DESIGNER);
		roleMap.put(1 << 3, EnterpriseAuthority.ROLE_DEVELOPER);
		roleMap.put(1 << 4, EnterpriseAuthority.ROLE_PROJECT_MANAGER);
		roleMap.put(1 << 5, EnterpriseAuthority.ROLE_MANAGER);
		roleMap.put(1 << 6, EnterpriseAuthority.ROLE_HR);
		roleMap.put(1 << 7, EnterpriseAuthority.ROLE_ADMIN);
		roleMap.put(1 << 8, EnterpriseAuthority.ROLE_SYS_DEVELOPER);
		roleMap.put(1 << 9, EnterpriseAuthority.ROLE_SYS_TESTER);
		roleMap.put(1 << 10, EnterpriseAuthority.ROLE_SYS_MANAGER);
		roleMap.put(1 << 11, EnterpriseAuthority.ROLE_SYS_ADMIN);
	}
	
	public static GrantedAuthority getAuthority(int authority) {
		return roleMap.get(authority);
	}
	
}
