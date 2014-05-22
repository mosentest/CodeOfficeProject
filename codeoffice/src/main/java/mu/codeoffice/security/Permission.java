package mu.codeoffice.security;

import java.util.List;

public interface Permission {
	
	default boolean isAuthorized(int authority) {
		return (getAuthority() & authority) > 1;
	}
	
	default int getPermissionValue(List<Permission> permissions) {
		int value = 0;
		for (Permission permission : permissions) {
			value = value | permission.getAuthority();
		}
		return value;
	}
	
	public int getAuthority();
	
	public String getKey();
	
	public String getAuthKey();
	
	public String getCategory();
	
	public String getDescription();
	
	public int getFullAuthority();
}
