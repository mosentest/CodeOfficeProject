package mu.codeoffice.entity;

import java.util.Date;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import mu.codeoffice.enums.CasePriority;
import mu.codeoffice.enums.CaseResolution;
import mu.codeoffice.enums.CaseStatus;
import mu.codeoffice.enums.CaseType;

@StaticMetamodel(Case.class)
public class Case_ {
	
	public static volatile SingularAttribute<Case, Long> id;
	
	public static volatile SingularAttribute<Case, Enterprise> enterprise;
	
	public static volatile SingularAttribute<Case, String> code;
	
	public static volatile SingularAttribute<Case, String> summary;
	
	public static volatile SingularAttribute<Case, String> description;
	
	public static volatile SingularAttribute<Case, Date> create;
	
	public static volatile SingularAttribute<Case, Date> update;
	
	public static volatile SingularAttribute<Case, Date> close;
	
	public static volatile SingularAttribute<Case, Boolean> inProgress;
	
	public static volatile SingularAttribute<Case, Long> estimation;
	
	public static volatile SingularAttribute<Case, Long> overtime;
	
	public static volatile SingularAttribute<Case, Long> timeSpent;
	
	public static volatile SingularAttribute<Case, Project> project;
	
	public static volatile SingularAttribute<Case, CaseResolution> resolution;
	
	public static volatile SingularAttribute<Case, CaseStatus> status;
	
	public static volatile SingularAttribute<Case, CasePriority> priority;
	
	public static volatile SingularAttribute<Case, EnterpriseUser> reporter;
	
	public static volatile SingularAttribute<Case, EnterpriseUser> assignee;
	
	public static volatile SingularAttribute<Case, CaseType> type;
	
	public static volatile SingularAttribute<Case, Boolean> edited;
	
	public static volatile SingularAttribute<Case, Boolean> removed;
	
	public static volatile SingularAttribute<Case, Version> releaseVersion;
	
	public static volatile ListAttribute<Case, Version> versions;
	
	public static volatile ListAttribute<Case, Component> components;
	
	public static volatile ListAttribute<Case, Label> labels;
	
	public static volatile ListAttribute<Case, Attachment> attachments;
	
	public static volatile ListAttribute<Case, CaseNote> notes;
	
	public static volatile ListAttribute<Case, CaseHistory> histories;
	
	public static volatile ListAttribute<Case, CaseLink> caseLinks;
	
	public static volatile ListAttribute<Case, CaseActivity> activities;
	
	public static volatile ListAttribute<Case, WorkLog> workLogs;
	
	public static volatile ListAttribute<Case, EnterpriseUser> participants;
	
	public static volatile ListAttribute<Case, EnterpriseUser> watchers;

}
