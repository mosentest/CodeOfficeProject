package mu.codeoffice.entity;

import java.util.Date;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import mu.codeoffice.entity.settings.GlobalPermissionSettings;
import mu.codeoffice.entity.settings.ProjectPermissionSettings;
import mu.codeoffice.entity.settings.ProjectRole;

@StaticMetamodel(User.class)
public class User_ {

	public static volatile SingularAttribute<User, Long> id;

	public static volatile SingularAttribute<User, Enterprise> enterprise;

	public static volatile SingularAttribute<User, String> account;

	public static volatile SingularAttribute<User, String> email;

	public static volatile SingularAttribute<User, String> password;
	
	public static volatile SingularAttribute<User, Boolean> gender;

	public static volatile SingularAttribute<User, Date> create;

	public static volatile SingularAttribute<User, Date> login;

	public static volatile SingularAttribute<User, String> profilePath;

	public static volatile SingularAttribute<User, String> firstName;

	public static volatile SingularAttribute<User, String> lastName;

	public static volatile SingularAttribute<User, String> phone;

	public static volatile SingularAttribute<User, String> address;
    
    public static volatile SingularAttribute<User, Integer> globalPermissionValue;

    public static volatile SingularAttribute<User, Integer> projectPermissionValue;

	public static volatile ListAttribute<User, GlobalPermissionSettings> globalPermissions;

	public static volatile ListAttribute<User, ProjectPermissionSettings> projectPermissions;

	public static volatile ListAttribute<User, UserGroup> userGroups;

	public static volatile ListAttribute<User, ProjectRole> projectRoles;

	public static volatile ListAttribute<User, Issue> watching;
}
