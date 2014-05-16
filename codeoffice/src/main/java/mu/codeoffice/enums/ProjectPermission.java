package mu.codeoffice.enums;

import java.util.ArrayList;
import java.util.List;

import mu.codeoffice.security.Permission;

import org.springframework.cache.annotation.Cacheable;

public enum ProjectPermission implements Permission {
	
	CASE("case", 1, 1), CASE_MANAGE("case_manage", 2, 21), VERSION_COMPONENT("version_component", 4, 23), VERSION_COMPONENT_MANAGE("version_component_manage", 8, 31), 
	PROJECT("project", 16, 21), PROJECT_MANAGE("project_manage", 32, 63);
	
	private final String code;
	
	private final int value;
	
	private final int authority;
	
	private ProjectPermission(String code, int value, int authority) {
		this.code = code;
		this.value = value;
		this.authority = authority;
	}
	
	public static boolean authenticate(int authority, ProjectPermission...roles) {
		List<ProjectPermission> auths = getRoles(authority);
		for (ProjectPermission roleType : roles) {
			if (auths.contains(roleType)) {
				return true;
			}
		}
		return false;
	}
	
	@Cacheable(value = "projectRoleAuth", key = "#value")
	public static List<ProjectPermission> getRoles(int value) {
		List<ProjectPermission> auths = new ArrayList<>();
		
		for (int i = 0; i < ProjectPermission.values().length; i++) {
			if ((value & 1) == 1) {
				auths.add(ProjectPermission.values()[i]);
				value = value >> 1;
			}
		}
		return auths;
	}
	
	public String getCode() {
		return code;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getAuthority() {
		return authority;
	}
	
}
