package mu.codeoffice.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum GlobalPermission implements Permission {

	SYSTEM_ADMIN("codeoffice.permission.global.system_admin", 32, 63),
	ADMIN("codeoffice.permission.global.admin", 16, 31),
	PROJECT_ADMIN("codeoffice.permission.global.project_admin", 8, 11),
	SHARED_OBJECT("codeoffice.permission.global.shared_object", 4, 5),
	BROWSE_USER("codeoffice.permission.global.browse_user", 2, 3),
	USER("codeoffice.permission.global.user", 1, 1);
	
	private final String key;
	
	private final String category = "codeoffice.permission.global.global";
	
	private final String description;
	
	private final int authority;
	
	private final int fullAuthority;
	
	private final static Map<Integer, List<GlobalPermission>> cacheManager = new HashMap<>();
	
	private GlobalPermission(String key, int authority, int fullAuthority) {
		this.key = key;
		this.description = key + ".description";
		this.authority = authority;
		this.fullAuthority = fullAuthority;
	}
	
	@Override
	public int getAuthority() {
		return authority;
	}
	
	public static List<GlobalPermission> getPermissions(int fullAuthority) {
		if (cacheManager.containsKey(fullAuthority)) {
			return cacheManager.get(fullAuthority);
		}
		List<GlobalPermission> permissions = new ArrayList<>();
		for (GlobalPermission permission : values()) {
			if ((permission.getAuthority() & fullAuthority) > 1) {
				permissions.add(permission);
			}
		}
		cacheManager.put(fullAuthority, permissions);
		return permissions;
	}
	
	public String getKey() {
		return key;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getFullAuthority() {
		return fullAuthority;
	}
	
}
