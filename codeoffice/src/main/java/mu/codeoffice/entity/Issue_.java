package mu.codeoffice.entity;

import java.util.Date;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import mu.codeoffice.entity.settings.IssuePriority;
import mu.codeoffice.entity.settings.IssueResolution;
import mu.codeoffice.entity.settings.IssueStatus;
import mu.codeoffice.entity.settings.IssueType;

@StaticMetamodel(Issue.class)
public class Issue_ {
	
	public static volatile SingularAttribute<Issue, Long> id;
	
	public static volatile SingularAttribute<Issue, Enterprise> enterprise;
	
	public static volatile SingularAttribute<Issue, String> key;
	
	public static volatile SingularAttribute<Issue, String> summary;
	
	public static volatile SingularAttribute<Issue, String> description;
	
	public static volatile SingularAttribute<Issue, Date> create;
	
	public static volatile SingularAttribute<Issue, Date> update;
	
	public static volatile SingularAttribute<Issue, Date> close;
	
	public static volatile SingularAttribute<Issue, Boolean> inProgress;
	
	public static volatile SingularAttribute<Issue, Long> estimation;
	
	public static volatile SingularAttribute<Issue, Long> overtime;
	
	public static volatile SingularAttribute<Issue, Long> timeSpent;
	
	public static volatile SingularAttribute<Issue, Project> project;
	
	public static volatile SingularAttribute<Issue, User> reporter;
	
	public static volatile SingularAttribute<Issue, User> assignee;
	
	public static volatile SingularAttribute<Issue, Boolean> edited;
	
	public static volatile SingularAttribute<Issue, Boolean> removed;
	
	public static volatile SingularAttribute<Issue, Boolean> closed;
	
	public static volatile SingularAttribute<Issue, Boolean> resolved;
	
	public static volatile SingularAttribute<Issue, IssueStatus> status;
	
	public static volatile SingularAttribute<Issue, IssueResolution> resolution;
	
	public static volatile SingularAttribute<Issue, IssuePriority> priority;
	
	public static volatile SingularAttribute<Issue, IssueType> type;
	
	public static volatile ListAttribute<Issue, User> participants;
	
	public static volatile ListAttribute<Issue, User> watchers;

}
