package mu.codeoffice.entity.settings;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.User;
import mu.codeoffice.entity.UserGroup;

@Entity
@Table(name = "notification")
public class Notification implements Serializable {

	private static final long serialVersionUID = -6385522745961286741L;

	public static enum NotificationType {
		CREATE("notification.create"), UPDATE("notification.update"), START("notification.start"), STOP("notification.stop"),
		ASSIGN("notification.assign"), RESOLVE("notification.resolve"), CLOSE("notification.close"), REOPEN("notification.reopen"), 
		COMMENT("notification.comment"), ATTACH("notification.attach"), LOG("notification.log");
		
		private final String key;
		
		private final String description;
		
		private NotificationType(String key) {
			this.key = key;
			this.description = key + ".description";
		}
		
		public String getKey() {
			return key;
		}
		
		public String getDescription() {
			return description;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "notificationscheme_id")
	private NotificationScheme notificationScheme;

	@Column(name = "notification_type")
	@Enumerated(EnumType.STRING)
	private NotificationType notificationType;
	
	@Column(name = "assignee_on")
	private boolean assignee;

	@Column(name = "reporter_on")
	private boolean reporter;

	@Column(name = "project_lead_on")
	private boolean projectLead;

	@Column(name = "component_lead_on")
	private boolean componentLead;

	@Column(name = "watchers_on")
	private boolean watchers;

	@Column(name = "all_groups_on")
	private boolean allGroups;

	@Column(name = "all_roles_on")
	private boolean allRoles;

	@Column(name = "email_string")
	private String emailString;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "notification_roles", uniqueConstraints = @UniqueConstraint(columnNames = {"notification_rid", "role_id"}),
        joinColumns = @JoinColumn(name = "notification_rid", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<ProjectRole> projectRoles;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "notification_groups", uniqueConstraints = @UniqueConstraint(columnNames = {"notification_gid", "group_id"}),
        joinColumns = @JoinColumn(name = "notification_gid", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
	private List<UserGroup> userGroups;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "notification_users", uniqueConstraints = @UniqueConstraint(columnNames = {"notification_uid", "user_id"}),
        joinColumns = @JoinColumn(name = "notification_uid", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	private List<User> users;

	@Transient
	private List<String> emails;
	
	public Notification() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public NotificationScheme getNotificationScheme() {
		return notificationScheme;
	}

	public void setNotificationScheme(NotificationScheme notificationScheme) {
		this.notificationScheme = notificationScheme;
	}

	public NotificationType getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}

	public boolean isAssignee() {
		return assignee;
	}

	public void setAssignee(boolean assignee) {
		this.assignee = assignee;
	}

	public boolean isReporter() {
		return reporter;
	}

	public void setReporter(boolean reporter) {
		this.reporter = reporter;
	}

	public boolean isProjectLead() {
		return projectLead;
	}

	public void setProjectLead(boolean projectLead) {
		this.projectLead = projectLead;
	}

	public boolean isComponentLead() {
		return componentLead;
	}

	public void setComponentLead(boolean componentLead) {
		this.componentLead = componentLead;
	}

	public boolean isWatchers() {
		return watchers;
	}

	public void setWatchers(boolean watchers) {
		this.watchers = watchers;
	}

	public String getEmailString() {
		return emailString;
	}

	public void setEmailString(String emailString) {
		this.emailString = emailString;
	}

	public List<ProjectRole> getProjectRoles() {
		return projectRoles;
	}

	public void setProjectRoles(List<ProjectRole> projectRoles) {
		this.projectRoles = projectRoles;
	}

	public List<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	public boolean isAllRoles() {
		return allRoles;
	}

	public void setAllRoles(boolean allRoles) {
		this.allRoles = allRoles;
	}

	public boolean isAllGroups() {
		return allGroups;
	}

	public void setAllGroups(boolean allGroups) {
		this.allGroups = allGroups;
	}
	
}
