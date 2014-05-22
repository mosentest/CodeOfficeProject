package mu.codeoffice.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("I")
public class IssueHistory extends History {

	private static final long serialVersionUID = 6017196451650119814L;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "issue_id")
	private Issue issueObject;

	public Issue getIssueObject() {
		return issueObject;
	}

	public void setIssueObject(Issue issueObject) {
		this.issueObject = issueObject;
	}

}
