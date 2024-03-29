package mu.codeoffice.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ProjectPermission implements Permission {

	PROJECT_ADMIN("codeoffice.permission.project.project_admin", "ROLE_PROJECT_PROJECT_ADMIN", "codeoffice.permission.project.project", 536870912, -1),
	BROWSE_PROJECT("codeoffice.permission.project.browse_project", "ROLE_PROJECT_BROWSE_PROJECT", "codeoffice.permission.project.project", 268435456, 268435456),
	DEVELOPEMENT_TOOLS("codeoffice.permission.project.development_tools", "ROLE_PROJECT_DEV_TOOLS", "codeoffice.permission.project.project", 134217728, 134217728),
	WORK_FLOW("codeoffice.permission.project.work_flow", "ROLE_PROJECT_WORKFLOW", "codeoffice.permission.project.project", 67108864, 67108864),
	ISSUE_ASSIGN("codeoffice.permission.project.issue_assign", "ROLE_PROJECT_ISSUE_ASSIGN", "codeoffice.permission.project.issue", 33554432, 33554432),
	ASSIGNABLE_USER("codeoffice.permission.project.assignable_user", "ROLE_PROJECT_ASSIGNABLE", "codeoffice.permission.project.issue", 16777216, 16777216),
	ISSUE_CLOSE("codeoffice.permission.project.issue_close", "ROLE_PROJECT_ISSUE_CLOSE", "codeoffice.permission.project.issue", 8388608, 8388608),
	ISSUE_CREATE("codeoffice.permission.project.issue_create", "ROLE_PROJECT_ISSUE_CREATE", "codeoffice.permission.project.issue", 4194304, 4194304),
	ISSUE_DELETE("codeoffice.permission.project.issue_delete", "ROLE_PROJECT_ISSUE_DEL", "codeoffice.permission.project.issue", 2097152, 2097152),
	ISSUE_EDIT("codeoffice.permission.project.issue_edit", "ROLE_PROJECT_ISSUE_EDIT", "codeoffice.permission.project.issue", 1048576, 1048576),
	ISSUE_LINK("codeoffice.permission.project.issue_link", "ROLE_PROJECT_ISSUE_LINK", "codeoffice.permission.project.issue", 524288, 524288),
	MODIFY_REPORTER("codeoffice.permission.project.modify_reporter", "ROLE_PROJECT_MODIFY_REPORTER", "codeoffice.permission.project.issue", 262144, 262144),
	ISSUE_RESOLVE("codeoffice.permission.project.issue_resolve", "ROLE_PROJECT_ISSUE_RESOLVE", "codeoffice.permission.project.issue", 131072, 131072),
	ISSUE_SCHEDULE("codeoffice.permission.project.issue_schedule", "ROLE_PROJECT_ISSUE_SCHEDULE", "codeoffice.permission.project.issue", 65536, 65536),
	ISSUE_SECURITY("codeoffice.permission.project.security_issue", "ROLE_PROJECT_ISSUE_SECURITY", "codeoffice.permission.project.issue", 32768, 32768),
	ISSUE_TRANSITION("codeoffice.permission.project.transition_issue", "ROLE_PROJECT_ISSUE_TRANSITION", "codeoffice.permission.project.issue", 1073741824, 1073741824),
	
	COMMENT_ADD("codeoffice.permission.project.comment_add", "ROLE_PROJECT_COMMENT_ADD", "codeoffice.permission.project.comment", 16384, 16384),
	COMMENT_DEL_ALL("codeoffice.permission.project.comment_del_all", "ROLE_PROJECT_COMMENT_DEL_ALL", "codeoffice.permission.project.comment", 8192, 8192),
	COMMENT_DEL_OWN("codeoffice.permission.project.comment_del_own", "ROLE_PROJECT_COMMENT_DEL_OWN", "codeoffice.permission.project.comment", 4096, 4096),
	COMMENT_EDIT_ALL("codeoffice.permission.project.comment_edit_all", "ROLE_PROJECT_COMMENT_EDIT_ALL", "codeoffice.permission.project.comment", 2048, 2048),
	COMMENT_EDIT_OWN("codeoffice.permission.project.comment_edit_own", "ROLE_PROJECT_COMMENT_EDIT_OWN", "codeoffice.permission.project.comment", 1024, 1024),

	ATTACHMENT_ADD("codeoffice.permission.project.attachment_add", "ROLE_PROJECT_ATTACHMENT_ADD", "codeoffice.permission.project.attachment", 512, 512),
	ATTACHMENT_DEL_ALL("codeoffice.permission.project.attachment_del_all", "ROLE_PROJECT_ATTACHMENT_DEL_ALL", "codeoffice.permission.project.attachment", 256, 256),
	ATTACHMENT_DEL_OWN("codeoffice.permission.project.attachment_del_own", "ROLE_PROJECT_ATTACHMENT_DEL_OWN", "codeoffice.permission.project.attachment", 128, 128),

	MANAGE_WATCHER("codeoffice.permission.project.manage_watcher", "ROLE_PROJECT_MANAGE_WATCHER", "codeoffice.permission.project.vote_watcher", 64, 64),
	VIEW_VOTE_WATCHER("codeoffice.permission.project.view_vote_watcher", "ROLE_PROJECT_VIEW_VOTE_WATCHER", "codeoffice.permission.project.vote_watcher", 32, 32),

	LOG_ADD("codeoffice.permission.project.log_add", "ROLE_PROJECT_LOG_ADD", "codeoffice.permission.project.log", 16, 16),
	LOG_DEL_ALL("codeoffice.permission.project.log_del_all", "ROLE_PROJECT_LOG_DEL_ALL", "codeoffice.permission.project.log", 8, 8),
	LOG_DEL_OWN("codeoffice.permission.project.log_del_own", "ROLE_PROJECT_LOG_DEL_OWN", "codeoffice.permission.project.log", 4, 4),
	LOG_EDIT_ALL("codeoffice.permission.project.log_edit_all", "ROLE_PROJECT_LOG_EDIT_ALL", "codeoffice.permission.project.log", 2, 2),
	LOG_EDIT_OWN("codeoffice.permission.project.log_edit_own", "ROLE_PROJECT_LOG_EDIT_OWN", "codeoffice.permission.project.log", 1, 1);
	
	public static final List<ProjectPermission> WORKFLOW_PERMISSIONS;
	
	public static final int WORK_FLOW_ALL = 1721729024;
	
	static {
		WORKFLOW_PERMISSIONS = Arrays.asList(PROJECT_ADMIN, WORK_FLOW, ISSUE_ASSIGN, ISSUE_CLOSE, ISSUE_EDIT, ISSUE_LINK, 
				MODIFY_REPORTER, ISSUE_RESOLVE, ISSUE_SCHEDULE, 
				ISSUE_SECURITY, ISSUE_TRANSITION);
	}
	
	private final String key;
	
	private final String authKey;
	
	private final String category;
	
	private final String description;
	
	private final int authority;
	
	private final int fullAuthority;
	
	private final static Map<Integer, List<ProjectPermission>> cacheManager = new HashMap<>();
	
	private ProjectPermission(String key, String authKey, String category, int authority, int fullAuthority) {
		this.key = key;
		this.authKey = authKey;
		this.category = category;
		this.description = key + ".description";
		this.authority = authority;
		this.fullAuthority = fullAuthority;
	}
	
	@Override
	public int getAuthority() {
		return authority;
	}
	
	public static List<ProjectPermission> getPermissions(int fullAuthority) {
		if (cacheManager.containsKey(fullAuthority)) {
			return cacheManager.get(fullAuthority);
		}
		List<ProjectPermission> permissions = new ArrayList<>();
		for (ProjectPermission permission : values()) {
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

	public String getAuthKey() {
		return authKey;
	}
	
}
