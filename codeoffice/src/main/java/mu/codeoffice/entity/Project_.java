package mu.codeoffice.entity;

import java.util.Date;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Project.class)
public class Project_ {

	public static volatile SingularAttribute<Project, Long> id;

	public static volatile SingularAttribute<Project, Enterprise> enterprise;

	public static volatile SingularAttribute<Project, ProjectCategory> category;

	public static volatile SingularAttribute<Project, String> code;

	public static volatile SingularAttribute<Project, String> name;

	public static volatile SingularAttribute<Project, String> description;

	public static volatile SingularAttribute<Project, String> iconPath;

	public static volatile SingularAttribute<Project, Date> create;

	public static volatile SingularAttribute<Project, Date> update;

	public static volatile SingularAttribute<Project, Date> end;

	public static volatile SingularAttribute<Project, Boolean> removed;

	public static volatile SingularAttribute<Project, Boolean> completed;

	public static volatile SingularAttribute<Project, User> lead;
	
	public static volatile SingularAttribute<Project, Integer> visibility;
	
	public static volatile SingularAttribute<Project, Integer> totalUsers;
	
	public static volatile SingularAttribute<Project, Integer> totalIssues;
	
	public static volatile ListAttribute<Project, Component> components;
	
	public static volatile ListAttribute<Project, Version> versions;

	public static volatile ListAttribute<Project, Label> labels;
	
	public static volatile ListAttribute<Project, Issue> issues;
	
	public static volatile ListAttribute<Project, ProjectHistory> histories;

	public static volatile ListAttribute<Project, Attachment> attachment;
	
	public static volatile ListAttribute<Project, ProjectNote> notes;
	
	public static volatile ListAttribute<Project, ProjectActivity> activities;
	
}
