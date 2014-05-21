package mu.codeoffice.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("C")
public class CaseHistory extends OfficeHistory {

	private static final long serialVersionUID = 6017196451650119814L;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "office_case_history_id")
	private Issue caseObject;

	public Issue getCaseObject() {
		return caseObject;
	}

	public void setCaseObject(Issue caseObject) {
		this.caseObject = caseObject;
	}

}
