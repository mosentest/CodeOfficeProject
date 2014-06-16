package mu.codeoffice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import mu.codeoffice.entity.settings.IssueTypeScheme;
import mu.codeoffice.entity.settings.NotificationScheme;
import mu.codeoffice.entity.settings.ProjectPermissionScheme;
import mu.codeoffice.entity.settings.WorkFlow;

@Entity
@Table(name = "project", uniqueConstraints = @UniqueConstraint(columnNames = {
		"p_key", "enterprise_id" }))
public class Project implements Serializable {

	private static final long serialVersionUID = 1939535351112095185L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private ProjectCategory category;

	@Column(name = "p_key")
	private String key;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "icon_path")
	private String iconPath;

	@Column(name = "create_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date create;

	@Column(name = "update_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date update;

	@Column(name = "end_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date end;

	@Column(name = "removed")
	private boolean removed;

	@Column(name = "completed")
	private boolean completed;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "lead_id")
	private User lead;

	@Column(name = "user_count")
	private int userCount;

	@Column(name = "issue_count")
	private int issueCount;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "notificationscheme_id")
	private NotificationScheme notificationScheme;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "projectpermissionscheme_id")
	private ProjectPermissionScheme projectPermissionScheme;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "issuetypescheme_id")
	private IssueTypeScheme issueTypeScheme;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "workflow_id")
	private WorkFlow workFlow;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
	private List<Issue> issues;

	public Project() {
	}

	public String getProjectHeader() {
		return String.format("[%s] - %s", key, name);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreate() {
		return create;
	}

	public void setCreate(Date create) {
		this.create = create;
	}

	public Date getUpdate() {
		return update;
	}

	public void setUpdate(Date update) {
		this.update = update;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public List<Issue> getIssues() {
		return issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public ProjectCategory getCategory() {
		return category;
	}

	public void setCategory(ProjectCategory category) {
		this.category = category;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public User getLead() {
		return lead;
	}

	public void setLead(User lead) {
		this.lead = lead;
	}

	public ProjectPermissionScheme getProjectPermissionScheme() {
		return projectPermissionScheme;
	}

	public void setProjectPermissionScheme(
			ProjectPermissionScheme projectPermissionScheme) {
		this.projectPermissionScheme = projectPermissionScheme;
	}

	public IssueTypeScheme getIssueTypeScheme() {
		return issueTypeScheme;
	}

	public void setIssueTypeScheme(IssueTypeScheme issueTypeScheme) {
		this.issueTypeScheme = issueTypeScheme;
	}

	public WorkFlow getWorkFlow() {
		return workFlow;
	}

	public void setWorkFlow(WorkFlow workFlow) {
		this.workFlow = workFlow;
	}

	public NotificationScheme getNotificationScheme() {
		return notificationScheme;
	}

	public void setNotificationScheme(NotificationScheme notificationScheme) {
		this.notificationScheme = notificationScheme;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public int getIssueCount() {
		return issueCount;
	}

	public void setIssueCount(int issueCount) {
		this.issueCount = issueCount;
	}

}
