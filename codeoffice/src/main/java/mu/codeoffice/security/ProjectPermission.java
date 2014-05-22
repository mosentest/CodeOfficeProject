package mu.codeoffice.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ProjectPermission implements Permission {

	PROJECT_ADMIN("codeoffice.permission.project.project_admin", "", "codeoffice.permission.project.project", 536870912, 0),
	BROWSE_PROJECT("codeoffice.permission.project.browse_project", "", "codeoffice.permission.project.project", 268435456, 0),
	DEVELOPEMENT_TOOLS("codeoffice.permission.project.development_tools", "", "codeoffice.permission.project.project", 134217728, 0),
	WORK_FLOW("codeoffice.permission.project.work_flow", "", "codeoffice.permission.project.project", 67108864, 0),
	ISSUE_ASSIGN("codeoffice.permission.project.issue_assigne", "", "codeoffice.permission.project.issue", 33554432, 0),
	ASSIGNABLE_USER("codeoffice.permission.project.assignable_user", "", "codeoffice.permission.project.issue", 16777216, 0),
	ISSUE_CLOSE("codeoffice.permission.project.issue_close", "", "codeoffice.permission.project.issue", 8388608, 0),
	ISSUE_CREATE("codeoffice.permission.project.issue_create", "", "codeoffice.permission.project.issue", 4194304, 0),
	ISSUE_DELETE("codeoffice.permission.project.issue_delete", "", "codeoffice.permission.project.issue", 2097152, 0),
	ISSUE_EDIT("codeoffice.permission.project.issue_edit", "", "codeoffice.permission.project.issue", 1048576, 0),
	ISSUE_LINK("codeoffice.permission.project.issue_link", "", "codeoffice.permission.project.issue", 524288, 0),
	MODIFY_REPORTER("codeoffice.permission.project.modify_reporter", "", "codeoffice.permission.project.issue", 262144, 0),
	ISSUE_RESOLVE("codeoffice.permission.project.issue_resolve", "", "codeoffice.permission.project.issue", 131072, 0),
	ISSUE_SCHEDULE("codeoffice.permission.project.issue_schedule", "", "codeoffice.permission.project.issue", 65536, 0),
	ISSUE_SECURITY("codeoffice.permission.project.security_issue", "", "codeoffice.permission.project.issue", 32768, 0),
	ISSUE_TRANSITION("codeoffice.permission.project.transition_issue", "", "codeoffice.permission.project.issue", 1073741824, 0),
	
	COMMENT_ADD("codeoffice.permission.project.comment_add", "", "codeoffice.permission.project.comment", 16384, 0),
	COMMENT_DEL_ALL("codeoffice.permission.project.comment_del_all", "", "codeoffice.permission.project.comment", 8192, 0),
	COMMENT_DEL_OWN("codeoffice.permission.project.comment_del_own", "", "codeoffice.permission.project.comment", 4096, 0),
	COMMENT_EDIT_ALL("codeoffice.permission.project.comment_edit_all", "", "codeoffice.permission.project.comment", 2048, 0),
	COMMENT_EDIT_OWN("codeoffice.permission.project.comment_edit_own", "", "codeoffice.permission.project.comment", 1024, 0),

	ATTACHMENT_ADD("codeoffice.permission.project.attachment_add", "", "codeoffice.permission.project.attachment", 512, 0),
	ATTACHMENT_DEL_ALL("codeoffice.permission.project.attachment_del_all", "", "codeoffice.permission.project.attachment", 256, 0),
	ATTACHMENT_DEL_OWN("codeoffice.permission.project.attachment_del_own", "", "codeoffice.permission.project.attachment", 128, 0),

	MANAGE_WATCHER("codeoffice.permission.project.manage_watcher", "", "codeoffice.permission.project.vote_watcher", 64, 0),
	VIEW_VOTE_WATCHER("codeoffice.permission.project.view_vote_watcher", "", "codeoffice.permission.project.vote_watcher", 32, 0),

	LOG_ADD("codeoffice.permission.project.log_add", "", "codeoffice.permission.project.log", 16, 0),
	LOG_DEL_ALL("codeoffice.permission.project.log_del_all", "", "codeoffice.permission.project.log", 8, 0),
	LOG_DEL_OWN("codeoffice.permission.project.log_del_own", "", "codeoffice.permission.project.log", 4, 0),
	LOG_EDIT_ALL("codeoffice.permission.project.log_edit_all", "", "codeoffice.permission.project.log", 2, 0),
	LOG_EDIT_OWN("codeoffice.permission.project.log_edit_own", "", "codeoffice.permission.project.log", 1, 0);
	
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
			if ((permission.getAuthority() & fullAuthority) > 1) {
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
