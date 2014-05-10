package mu.codeoffice.security;

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
}
