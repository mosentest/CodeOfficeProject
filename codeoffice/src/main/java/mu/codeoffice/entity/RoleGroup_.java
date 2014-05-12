package mu.codeoffice.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(RoleGroup.class)
public class RoleGroup_ {

	public static volatile ListAttribute<RoleGroup, EnterpriseUser> users;
	
}
