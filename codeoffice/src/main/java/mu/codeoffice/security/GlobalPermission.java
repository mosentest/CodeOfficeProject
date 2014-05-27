package mu.codeoffice.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum GlobalPermission implements Permission {

	SYSTEM_ADMIN("codeoffice.permission.global.system_admin", "ROLE_GLOBAL_SYSTEM_ADMIN", 32, 63),
	ADMIN("codeoffice.permission.global.admin", "ROLE_GLOBAL_ADMIN", 16, 31),
	PROJECT_ADMIN("codeoffice.permission.global.project_admin", "ROLE_GLOBAL_PROJECT_ADMIN", 8, 11),
	SHARED_OBJECT("codeoffice.permission.global.shared_object", "ROLE_GLOBAL_SHARED_OBJECT", 4, 5),
	BROWSE_USER("codeoffice.permission.global.browse_user", "ROLE_GLOBAL_BROWSE_USER", 2, 3),
	USER("codeoffice.permission.global.user", "ROLE_GLOBAL_USER", 1, 1);
	
	private final String key;
	
	private final String authKey;
	
	private final String category = "codeoffice.permission.global.global";
	
	private final String description;
	
	private final int authority;
	
	private final int fullAuthority;
	
	private final static Map<Integer, List<GlobalPermission>> cacheManager = new HashMap<>();
	
	private GlobalPermission(String key, String authKey, int authority, int fullAuthority) {
		this.key = key;
		this.authKey = authKey;
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
			if ((permission.getAuthority() & fullAuthority) == permission.getAuthority()) {
				permissions.add(permission);
			}
		}
		cacheManager.put(fullAuthority, permissions);
		return permissions;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getCategory() {
		return category;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public int getFullAuthority() {
		return fullAuthority;
	}

	@Override
	public String getAuthKey() {
		return authKey;
	}
	
}
