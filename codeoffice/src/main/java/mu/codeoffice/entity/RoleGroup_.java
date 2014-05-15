package mu.codeoffice.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(RoleGroup.class)
public class RoleGroup_ {

	public static volatile SingularAttribute<RoleGroup, Long> id;

	public static volatile SingularAttribute<RoleGroup, Enterprise> enterprise;

	public static volatile SingularAttribute<RoleGroup, Project> project;

	public static volatile SingularAttribute<RoleGroup, ProjectRole> role;

	public static volatile ListAttribute<RoleGroup, EnterpriseUser> users;
	
}
