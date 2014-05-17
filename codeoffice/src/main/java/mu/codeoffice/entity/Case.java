package mu.codeoffice.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import mu.codeoffice.enums.CasePriority;
import mu.codeoffice.enums.CaseResolution;
import mu.codeoffice.enums.CaseStatus;
import mu.codeoffice.enums.CaseType;

@Entity
@Table(name = "office_case", uniqueConstraints = @UniqueConstraint(columnNames = {"code", "enterprise_id"}))
public class Case implements Serializable {

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
	
	@Column(name = "code")
	private String code;

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

	@Column(name = "estimation")
	private long estimation;

	@Column(name = "overtime")
	private long overtime;

	@Column(name = "time_spent")
	private long timeSpent;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "office_project_id")
	private Project project;

	@Column(length = 3)
	@Enumerated(EnumType.STRING)
	private CaseResolution resolution;

	@Column(length = 3)
	@Enumerated(EnumType.STRING)
	private CaseStatus status;

	@Column(length = 3)
	@Enumerated(EnumType.STRING)
	private CasePriority priority;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "office_case_reporter_id")
	private EnterpriseUser reporter;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "office_case_asignee_id")
	private EnterpriseUser assignee;

	@Column(length = 3)
	@Enumerated(EnumType.STRING)
	private CaseType type;
	
	@Column(name = "edited")
	private boolean edited;
	
	@Column(name = "removed")
	private boolean removed;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "office_release_version_id", nullable = true)
	private Version releaseVersion;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "office_case_version", 
        joinColumns = @JoinColumn(name = "case_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "version_id", referencedColumnName = "id"))
	@OrderBy("id ASC")
	private List<Version> versions;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "office_case_component", 
        joinColumns = @JoinColumn(name = "case_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "component_id", referencedColumnName = "id"))
	@OrderBy("name ASC")
	private List<Component> components;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Attachment> attachments;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "caseObject")
	@OrderBy("create DESC")
	private List<CaseNote> notes;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "caseObject")
	@OrderBy("create DESC")
	private List<CaseHistory> histories;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "caseObject")
	private List<CaseLink> caseLinks;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "caseObject")
	@OrderBy("create DESC")
	private List<CaseActivity> activities;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "caseObject")
	private List<WorkLog> workLogs;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<EnterpriseUser> participants;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "watching")
	private List<EnterpriseUser> watchers;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "office_case_label", 
        	joinColumns = @JoinColumn(name = "case_id", referencedColumnName = "id"), 
        	inverseJoinColumns = @JoinColumn(name = "label_id", referencedColumnName = "id"))
	@OrderBy("label ASC")
	private List<Label> labels;
	
	public Case() {}
	
	public static String getSortColumn(String column) {
		for (String c : SORTABLE_COLUMNS) {
			if (c.equals(column)) {
				return c;
			}
		}
		return DEFAULT_COLUMN;
	}
	
	public String getCaseHeader() {
		return String.format("[%s] - %s", code, summary);
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

	public long getEstimation() {
		return estimation;
	}

	public void setEstimation(long estimation) {
		this.estimation = estimation;
	}

	public long getTimeSpent() {
		return timeSpent;
	}

	public void setTimeSpent(long timeSpent) {
		this.timeSpent = timeSpent;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public CaseResolution getResolution() {
		return resolution;
	}

	public void setResolution(CaseResolution resolution) {
		this.resolution = resolution;
	}

	public CaseStatus getStatus() {
		return status;
	}

	public void setStatus(CaseStatus status) {
		this.status = status;
	}

	public CasePriority getPriority() {
		return priority;
	}

	public void setPriority(CasePriority priority) {
		this.priority = priority;
	}

	public CaseType getType() {
		return type;
	}

	public void setType(CaseType type) {
		this.type = type;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public List<CaseNote> getNotes() {
		return notes;
	}

	public void setNotes(List<CaseNote> notes) {
		this.notes = notes;
	}

	public List<CaseHistory> getHistories() {
		return histories;
	}

	public void setHistories(List<CaseHistory> histories) {
		this.histories = histories;
	}

	public List<CaseActivity> getActivities() {
		return activities;
	}

	public void setActivities(List<CaseActivity> activities) {
		this.activities = activities;
	}
	
	public List<Version> getVersions() {
		return versions;
	}

	public void setVersions(List<Version> versions) {
		this.versions = versions;
	}

	public List<EnterpriseUser> getParticipants() {
		return participants;
	}

	public void setParticipants(List<EnterpriseUser> participants) {
		this.participants = participants;
	}

	public List<Label> getLabels() {
		return labels;
	}

	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

	public long getOvertime() {
		return overtime;
	}

	public void setOvertime(long overtime) {
		this.overtime = overtime;
	}

	public List<WorkLog> getWorkLogs() {
		return workLogs;
	}

	public void setWorkLogs(List<WorkLog> workLogs) {
		this.workLogs = workLogs;
	}

	public boolean isEdited() {
		return edited;
	}

	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	public List<EnterpriseUser> getWatchers() {
		return watchers;
	}

	public void setWatchers(List<EnterpriseUser> watchers) {
		this.watchers = watchers;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	public Version getReleaseVersion() {
		return releaseVersion;
	}

	public void setReleaseVersion(Version releaseVersion) {
		this.releaseVersion = releaseVersion;
	}

	public List<CaseLink> getCaseLinks() {
		return caseLinks;
	}

	public void setCaseLinks(List<CaseLink> caseLinks) {
		this.caseLinks = caseLinks;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public EnterpriseUser getReporter() {
		return reporter;
	}

	public void setReporter(EnterpriseUser reporter) {
		this.reporter = reporter;
	}

	public EnterpriseUser getAssignee() {
		return assignee;
	}

	public void setAssignee(EnterpriseUser assignee) {
		this.assignee = assignee;
	}

	public boolean isInProgress() {
		return inProgress;
	}

	public void setInProgress(boolean inProgress) {
		this.inProgress = inProgress;
	}
	
}
