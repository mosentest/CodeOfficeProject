package mu.codeoffice.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "worklog")
public class WorkLog implements Serializable {

	private static final long serialVersionUID = -6498976646080200527L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "issue_id")
	private Issue issueObject;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "creator_id")
	private EnterpriseUser creator;
	
	@Column(name = "time_spent")
	private long timeSpent;

	@Column(name = "create_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date create;

	@Column(name = "start_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date start;

	@Column(name = "description")
	private String description;
	
	public WorkLog() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Issue getIssueObject() {
		return issueObject;
	}

	public void setIssueObject(Issue issueObject) {
		this.issueObject = issueObject;
	}

	public long getTimeSpent() {
		return timeSpent;
	}

	public void setTimeSpent(long timeSpent) {
		this.timeSpent = timeSpent;
	}

	public Date getCreate() {
		return create;
	}

	public void setCreate(Date create) {
		this.create = create;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public EnterpriseUser getCreator() {
		return creator;
	}

	public void setCreator(EnterpriseUser creator) {
		this.creator = creator;
	}

}
