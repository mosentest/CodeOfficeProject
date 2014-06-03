package mu.codeoffice.entity.settings;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.User;

@Entity
@Table(name = "settings_workflow", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "enterprise_id"}))
public class WorkFlow implements Serializable {
	
	private static final long serialVersionUID = -1574987429237936201L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;
	
	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "steps")
	private int steps;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_id")
	private User creator;
	
	@Column(name = "modified_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date modified;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "default_status_id")
	private IssueStatus defaultStatus;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "destination_status_id")
	private IssueStatus destinationStatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workFlow")
	private List<WorkFlowTransition> transitions;

	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "workflow_status", uniqueConstraints = @UniqueConstraint(columnNames = {"workflow_id", "status_id"}),
        joinColumns = @JoinColumn(name = "workflow_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "status_id", referencedColumnName = "id"))
	private List<IssueStatus> issueStatus;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workFlow")
    private List<Project> projects;
    
    public WorkFlow() {}

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

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public IssueStatus getDefaultStatus() {
		return defaultStatus;
	}

	public void setDefaultStatus(IssueStatus defaultStatus) {
		this.defaultStatus = defaultStatus;
	}

	public IssueStatus getDestinationStatus() {
		return destinationStatus;
	}

	public void setDestinationStatus(IssueStatus destinationStatus) {
		this.destinationStatus = destinationStatus;
	}

	public List<WorkFlowTransition> getTransitions() {
		return transitions;
	}

	public void setTransitions(List<WorkFlowTransition> transitions) {
		this.transitions = transitions;
	}

	public List<IssueStatus> getIssueStatus() {
		return issueStatus;
	}

	public void setIssueStatus(List<IssueStatus> issueStatus) {
		this.issueStatus = issueStatus;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}
	
}
