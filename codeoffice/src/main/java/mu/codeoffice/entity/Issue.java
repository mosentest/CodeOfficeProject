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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import mu.codeoffice.entity.settings.IssuePriority;
import mu.codeoffice.entity.settings.IssueResolution;
import mu.codeoffice.entity.settings.IssueStatus;
import mu.codeoffice.entity.settings.IssueType;

@Entity
@Table(name = "issue", uniqueConstraints = @UniqueConstraint(columnNames = {"i_key", "enterprise_id"}))
public class Issue implements Serializable {

	private static final long serialVersionUID = 410952237049468161L;
	
	private static final String[] SORTABLE_COLUMNS =  {
		"code", "summary", "create", "update", "close", "project"
	};
	
	private static final String DEFAULT_COLUMN = "code";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;
	
	@Column(name = "i_key")
	private String key;

	@Column(name = "summary")
	private String summary;

	@Column(name = "description")
	private String description;

	@Column(name = "create_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date create;

	@Column(name = "update_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date update;

	@Column(name = "close_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date close;
	
	@Column(name = "in_progress")
	private boolean inProgress;

	@Column(name = "resolved")
	private boolean resolved;

	@Column(name = "closed")
	private boolean closed;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	private Project project;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id")
	private IssueType type;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "resolution_id")
	private IssueResolution resolution;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "status_id")
	private IssueStatus status;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "priority_id")
	private IssuePriority priority;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "issue_reporter_id")
	private User reporter;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "issue_assignee_id")
	private User assignee;
	
	@Column(name = "removed")
	private boolean removed;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<User> participants;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "watching")
	private List<User> watchers;
	
	public Issue() {}
	
	public static String getSortColumn(String column) {
		for (String c : SORTABLE_COLUMNS) {
			if (c.equals(column)) {
				return c;
			}
		}
		return DEFAULT_COLUMN;
	}
	
	public String getCaseHeader() {
		return String.format("[%s] - %s", key, summary);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
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

	public Date getClose() {
		return close;
	}

	public void setClose(Date close) {
		this.close = close;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public IssueStatus getStatus() {
		return status;
	}

	public void setStatus(IssueStatus status) {
		this.status = status;
	}

	public IssuePriority getPriority() {
		return priority;
	}

	public void setPriority(IssuePriority priority) {
		this.priority = priority;
	}

	public IssueType getType() {
		return type;
	}

	public void setType(IssueType type) {
		this.type = type;
	}

	public List<User> getParticipants() {
		return participants;
	}

	public void setParticipants(List<User> participants) {
		this.participants = participants;
	}

	public List<User> getWatchers() {
		return watchers;
	}

	public void setWatchers(List<User> watchers) {
		this.watchers = watchers;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public User getReporter() {
		return reporter;
	}

	public void setReporter(User reporter) {
		this.reporter = reporter;
	}

	public User getAssignee() {
		return assignee;
	}

	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}

	public boolean isInProgress() {
		return inProgress;
	}

	public void setInProgress(boolean inProgress) {
		this.inProgress = inProgress;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public boolean isResolved() {
		return resolved;
	}

	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public IssueResolution getResolution() {
		return resolution;
	}

	public void setResolution(IssueResolution resolution) {
		this.resolution = resolution;
	}
	
}
