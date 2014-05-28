package mu.codeoffice.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import mu.codeoffice.entity.settings.GlobalPermissionSettings;

@StaticMetamodel(UserGroup.class)
public class UserGroup_ {

	public static volatile SingularAttribute<UserGroup, Long> id;

	public static volatile SingularAttribute<UserGroup, Enterprise> enterprise;
	
	public static volatile SingularAttribute<UserGroup, Boolean> defaultGroup;
	
	public static volatile SingularAttribute<UserGroup, String> name;
	
	public static volatile SingularAttribute<UserGroup, String> description;
	
	public static volatile SingularAttribute<UserGroup, Integer> userCount;
	
	public static volatile ListAttribute<UserGroup, GlobalPermissionSettings> globalPermissions;
	
	public static volatile ListAttribute<UserGroup, GlobalPermissionSettings> projectPermissions;

	public static volatile ListAttribute<UserGroup, User> users;
	
}
