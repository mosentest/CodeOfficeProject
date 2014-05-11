package mu.codeoffice.metamodel;

import java.util.Date;

import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import mu.codeoffice.entity.Case;
import mu.codeoffice.entity.Component;
import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.EnterpriseUser;
import mu.codeoffice.entity.Label;
import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.Version;
import mu.codeoffice.enums.CasePriority;
import mu.codeoffice.enums.CaseStatus;
import mu.codeoffice.enums.CaseType;

@StaticMetamodel(Case.class)
public class Case_ {
	
	public static volatile SingularAttribute<Case, Enterprise> enterprise;
	
	public static volatile SingularAttribute<Case, Project> project;
	
	public static volatile SingularAttribute<Case, EnterpriseUser> assignee;
	
	public static volatile SingularAttribute<Case, EnterpriseUser> reporter;
	
	public static volatile SingularAttribute<Case, Date> create; 
	
	public static volatile CollectionAttribute<Case, Version> versions;
	
	public static volatile CollectionAttribute<Case, Component> components;
	
	public static volatile CollectionAttribute<Case, Label> labels;
	
	public static volatile SingularAttribute<Case, CaseStatus> status;
	
	public static volatile SingularAttribute<Case, CaseType> type;
	
	public static volatile SingularAttribute<Case, CasePriority> priority;
	
	public static volatile SingularAttribute<Case, Boolean> removed;

}
