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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "project", uniqueConstraints = @UniqueConstraint(columnNames = {"code", "enterprise_id"}))
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

	@Column(name = "code")
	private String code;

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
	private EnterpriseUser lead;
	
	@Column(name = "total_users")
	private int totalUsers;

	@Column(name = "total_issues")
	private int totalIssues;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
	private List<RoleGroup> roleGroups;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
	private List<Component> components;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
	private List<Version> versions;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
	private List<Label> labels;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
	private List<Issue> issues;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "projectObject")
	private List<ProjectHistory> histories;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Attachment> attachment;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "projectObject")
	private List<ProjectNote> notes;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "projectObject")
	@OrderBy("create DESC")
	private List<ProjectActivity> activities;
	
	public Project() {}
	
	public String getProjectHeader() {
		return String.format("[%s] - %s", code, name);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public List<ProjectHistory> getHistories() {
		return histories;
	}

	public void setHistories(List<ProjectHistory> histories) {
		this.histories = histories;
	}

	public List<Attachment> getAttachment() {
		return attachment;
	}

	public void setAttachment(List<Attachment> attachment) {
		this.attachment = attachment;
	}

	public List<ProjectNote> getNotes() {
		return notes;
	}

	public void setNotes(List<ProjectNote> notes) {
		this.notes = notes;
	}

	public List<ProjectActivity> getActivities() {
		return activities;
	}

	public void setActivities(List<ProjectActivity> activities) {
		this.activities = activities;
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

	public List<RoleGroup> getRoleGroups() {
		return roleGroups;
	}

	public void setRoleGroups(List<RoleGroup> roleGroups) {
		this.roleGroups = roleGroups;
	}

	public int getTotalIssues() {
		return totalIssues;
	}

	public void setTotalIssues(int totalIssues) {
		this.totalIssues = totalIssues;
	}

	public int getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(int totalUsers) {
		this.totalUsers = totalUsers;
	}

	public List<Version> getVersions() {
		return versions;
	}

	public void setVersions(List<Version> versions) {
		this.versions = versions;
	}

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

	public List<Label> getLabels() {
		return labels;
	}

	public void setLabels(List<Label> labels) {
		this.labels = labels;
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

	public EnterpriseUser getLead() {
		return lead;
	}

	public void setLead(EnterpriseUser lead) {
		this.lead = lead;
	}
	
}
