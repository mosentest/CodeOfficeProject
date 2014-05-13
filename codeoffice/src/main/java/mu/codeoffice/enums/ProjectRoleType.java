package mu.codeoffice.enums;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;

public enum ProjectRoleType {
	
	CASE("case", 1), CASE_MANAGE("case_manage", 2), VERSION_COMPONENT("version_component", 4), VERSION_COMPONENT_MANAGE("version_component_manage", 8), 
	USER("user", 16), USER_MANAGE("user_manage", 32), PROJECT("project", 64), PROJECT_MANAGE("project_manage", 128), ADMIN("admin", 256);
	
	private final String code;
	
	private final int value;
	
	private ProjectRoleType(String code, int value) {
		this.code = code;
		this.value = value;
	}
	
	public static boolean authenticate(int value, ProjectRoleType...roles) {
		List<ProjectRoleType> auths = getRoles(value);
		for (ProjectRoleType roleType : roles) {
			if (auths.contains(roleType)) {
				return true;
			}
		}
		return false;
	}
	
	@Cacheable(value = "projectRoleAuth", key = "#value")
	public static List<ProjectRoleType> getRoles(int value) {
		List<ProjectRoleType> auths = new ArrayList<>();
		
		for (int i = 0; i < ProjectRoleType.values().length; i++) {
			if ((value & 1) == 1) {
				auths.add(ProjectRoleType.values()[i]);
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
	
}
