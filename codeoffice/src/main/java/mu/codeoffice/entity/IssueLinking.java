package mu.codeoffice.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "issuelink")
public class IssueLinking implements Serializable {

	private static final long serialVersionUID = -6567864973408779465L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "issue_id")
	private Issue issueObject;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "linktype_id")
	private IssueLinking linkType;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name="issuelink_linked", uniqueConstraints = @UniqueConstraint(columnNames = {"issuelink_id", "linked_id"}),
		joinColumns = @JoinColumn(name = "issuelink_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "linked_id", referencedColumnName = "id"))
	private List<Issue> issues;
	
	public IssueLinking() {}

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

	public IssueLinking getLinkType() {
		return linkType;
	}

	public void setLinkType(IssueLinking linkType) {
		this.linkType = linkType;
	}

	public List<Issue> getIssues() {
		return issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

}
