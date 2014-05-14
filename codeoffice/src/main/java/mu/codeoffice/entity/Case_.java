package mu.codeoffice.entity;

import java.util.Date;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

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
	
	public static volatile SingularAttribute<Case, Date> close; 
	
	public static volatile ListAttribute<Case, Version> versions;
	
	public static volatile SingularAttribute<Case, Version> releaseVersion;
	
	public static volatile ListAttribute<Case, Component> components;
	
	public static volatile ListAttribute<Case, Label> labels;
	
	public static volatile SingularAttribute<Case, Boolean> inProgress;
	
	public static volatile SingularAttribute<Case, CaseStatus> status;
	
	public static volatile SingularAttribute<Case, CaseType> type;
	
	public static volatile SingularAttribute<Case, CasePriority> priority;
	
	public static volatile SingularAttribute<Case, Boolean> removed;

}
