package mu.codeoffice.entity;

import java.util.Date;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import mu.codeoffice.entity.settings.GlobalPermissionSettings;
import mu.codeoffice.entity.settings.ProjectPermissionSettings;

@StaticMetamodel(EnterpriseUser.class)
public class EnterpriseUser_ {

	public static volatile SingularAttribute<EnterpriseUser, Long> id;

	public static volatile SingularAttribute<EnterpriseUser, Enterprise> enterprise;

	public static volatile SingularAttribute<EnterpriseUser, String> account;

	public static volatile SingularAttribute<EnterpriseUser, String> email;

	public static volatile SingularAttribute<EnterpriseUser, String> password;
	
	public static volatile SingularAttribute<EnterpriseUser, Boolean> gender;

	public static volatile SingularAttribute<EnterpriseUser, Date> create;

	public static volatile SingularAttribute<EnterpriseUser, Date> login;

	public static volatile SingularAttribute<EnterpriseUser, String> profilePath;

	public static volatile SingularAttribute<EnterpriseUser, String> firstName;

	public static volatile SingularAttribute<EnterpriseUser, String> lastName;

	public static volatile SingularAttribute<EnterpriseUser, String> phone;

	public static volatile SingularAttribute<EnterpriseUser, String> address;
    
    public static volatile SingularAttribute<EnterpriseUser, Integer> globalPermissionValue;

    public static volatile SingularAttribute<EnterpriseUser, Integer> projectPermissionValue;

	public static volatile ListAttribute<EnterpriseUser, GlobalPermissionSettings> globalPermissions;

	public static volatile ListAttribute<EnterpriseUser, ProjectPermissionSettings> projectPermissions;

	public static volatile ListAttribute<EnterpriseUser, UserGroup> userGroups;

	public static volatile ListAttribute<EnterpriseUser, Issue> watching;
}
