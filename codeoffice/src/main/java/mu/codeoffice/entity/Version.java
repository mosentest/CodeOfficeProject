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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "office_project_version", uniqueConstraints = @UniqueConstraint(columnNames = {"code", "office_project_id"}))
public class Version implements Serializable {

	private static final long serialVersionUID = -7015366395114015526L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(cascade = CascadeType.REMOVE, optional = false)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "office_project_id")
	private Project project;

	@Column(name = "code")
	private String code;

	@Column(name = "description")
	private String description;

	@Column(name = "start_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date start;

	@Column(name = "update_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date update;

	@Column(name = "release_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date release;

	@Column(name = "delay_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date delay;

	@Column(name = "started")
	private boolean started;
	
	@Column(name = "released")
	private boolean released;

	@Column(name = "no_release")
	private int noRelease;
	
	@Column(name = "no_related")
	private int noRelated;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "versions")
	private List<Case> relatedCases;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "releaseVersion")
	private List<Case> releaseCases;
	
	@Transient
	private List<Summary> summary;
	
	public Version() {}

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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getUpdate() {
		return update;
	}

	public void setUpdate(Date update) {
		this.update = update;
	}

	public Date getRelease() {
		return release;
	}

	public void setRelease(Date release) {
		this.release = release;
	}

	public List<Case> getRelatedCases() {
		return relatedCases;
	}

	public void setRelatedCases(List<Case> relatedCases) {
		this.relatedCases = relatedCases;
	}

	public Date getDelay() {
		return delay;
	}

	public void setDelay(Date delay) {
		this.delay = delay;
	}

	public boolean isReleased() {
		return released;
	}

	public void setReleased(boolean released) {
		this.released = released;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public List<Summary> getSummary() {
		return summary;
	}

	public void setSummary(List<Summary> summary) {
		this.summary = summary;
	}

	public List<Case> getReleaseCases() {
		return releaseCases;
	}

	public void setReleaseCases(List<Case> releaseCases) {
		this.releaseCases = releaseCases;
	}

	public int getNoRelease() {
		return noRelease;
	}

	public void setNoRelease(int noRelease) {
		this.noRelease = noRelease;
	}

	public int getNoRelated() {
		return noRelated;
	}

	public void setNoRelated(int noRelated) {
		this.noRelated = noRelated;
	}
	
}
