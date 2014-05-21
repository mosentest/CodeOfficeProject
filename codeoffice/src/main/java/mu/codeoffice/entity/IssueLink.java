package mu.codeoffice.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
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

@Entity
@Table(name = "issue_link")
public class IssueLink implements Serializable {

	private static final long serialVersionUID = -6567864973408779465L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "case_id")
	private Issue caseObject;

	@Column(length = 3)
	private IssueLinkType linkType;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name="office_case_link_caselink",
			joinColumns = { @JoinColumn(name="holder_id", referencedColumnName="id") },
			inverseJoinColumns = { @JoinColumn(name="case_id", referencedColumnName="id")
	})
	private List<Issue> cases;
	
	public IssueLink() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Issue getCaseObject() {
		return caseObject;
	}

	public void setCaseObject(Issue caseObject) {
		this.caseObject = caseObject;
	}

	public IssueLinkType getLinkType() {
		return linkType;
	}

	public void setLinkType(IssueLinkType linkType) {
		this.linkType = linkType;
	}

	public List<Issue> getCases() {
		return cases;
	}

	public void setCases(List<Issue> cases) {
		this.cases = cases;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

}
