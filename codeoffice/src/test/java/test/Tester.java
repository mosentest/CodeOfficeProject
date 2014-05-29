package test;

import java.util.Arrays;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.User;
import mu.codeoffice.entity.UserGroup;
import mu.codeoffice.entity.settings.ProjectPermissionScheme;
import mu.codeoffice.entity.settings.ProjectPermissionSettings;
import mu.codeoffice.entity.settings.ProjectRole;
import mu.codeoffice.security.ProjectPermission;

public class Tester {

	public static void main(String[] args) throws Exception {
		Enterprise enterprise = new Enterprise();
		enterprise.setId(1l);
		
		ProjectPermissionScheme scheme = new ProjectPermissionScheme();
		scheme.setId(1l);
		scheme.setEnterprise(enterprise);
		scheme.setName("TEST");
		scheme.setDefaultScheme(true);
		scheme.setDescription("OK");

		
		ProjectPermissionSettings s1 = new ProjectPermissionSettings();
		s1.setId(1l);
		s1.setEnterprise(enterprise);
		s1.setProjectPermission(ProjectPermission.PROJECT_ADMIN);
		
		ProjectPermissionSettings s2 = new ProjectPermissionSettings();
		s2.setId(1l);
		s2.setEnterprise(enterprise);
		s2.setProjectPermission(ProjectPermission.DEVELOPEMENT_TOOLS);

		UserGroup g1 = new UserGroup();
		g1.setId(1l);
		UserGroup g2 = new UserGroup();
		g2.setId(2l);

		User u1 = new User();
		u1.setId(1l);
		User u2 = new User();
		u2.setId(2l);

		ProjectRole r1 = new ProjectRole();
		r1.setId(1l);
		ProjectRole r2 = new ProjectRole();
		r2.setId(2l);
		
		s1.setUserGroups(Arrays.asList(g1));
		s1.setUsers(Arrays.asList(u1));
		s1.setProjectRoles(Arrays.asList(r1));
		
		s2.setUserGroups(Arrays.asList(g1, g2));
		s2.setUsers(Arrays.asList(u1, u2));
		s2.setProjectRoles(Arrays.asList(r1, r2));
		
		scheme.setProjectPermissionSettings(Arrays.asList(s1, s2));
		
		ProjectPermissionScheme clone = new ProjectPermissionScheme();
		
		System.out.println(clone.getId());
		System.out.println(clone.getEnterprise().getId());
		System.out.println(clone.isDefaultScheme());
		
		for (ProjectPermissionSettings settings : clone.getProjectPermissionSettings()) {
			System.out.println(settings.getProjectPermission());
			settings.getUsers().forEach(user -> System.out.println(user.getId()));
			settings.getUserGroups().forEach(user -> System.out.println(user.getId()));
			settings.getProjectRoles().forEach(user -> System.out.println(user.getId()));
		}
		
	}

}
