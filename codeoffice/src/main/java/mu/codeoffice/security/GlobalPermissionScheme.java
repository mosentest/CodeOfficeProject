package mu.codeoffice.security;

public enum GlobalPermissionScheme {

	ADMIN_GENERAL("globalPermissionScheme.admin.general", 1),
	VIEW_GENERAL("globalPermissionScheme.view.general", 2),
	ADMIN_OPTIONS("globalPermissionScheme.admin.options", 4),
	VIEW_OPTIONS("globalPermissionScheme.view.options", 8),
	ADMIN_PERMISSON("globalPermissionScheme.admin.permission", 16),
	VIEW_PERMISSONS("globalPermissionScheme.view.permission", 32),
	ADMIN_SESSION("globalPermissionScheme.admin.session", 64),
	ADMIN_USER("globalPermissionScheme.admin.user", 128),
	VIEW_USER("globalPermissionScheme.view.user", 256),
	ADMIN_PROJECT("globalPermissionScheme.admin.project", 512),
	MANAGE_PROJECT("globalPermissionScheme.manage.project", 1024),
	VIEW_PROJECT("globalPermissionScheme.view.project", 2048);

	private final String key;
	
	private final String description;
	
	private final int value;
	
	private GlobalPermissionScheme(String key, int value) {
		this.key = key;
		this.description = key + ".description";
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}
	
	public int getValue() {
		return value;
	}
	
}
