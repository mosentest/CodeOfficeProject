package mu.codeoffice.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Project.class)
public class Project_ {

	public static volatile SingularAttribute<Project, String> code;
	
	public static volatile SingularAttribute<Project, Enterprise> enterprise;
	
	public static volatile ListAttribute<Project, RoleGroup> roleGroups;
	
}
