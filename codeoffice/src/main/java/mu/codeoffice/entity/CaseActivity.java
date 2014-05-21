package mu.codeoffice.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("C")
public class CaseActivity extends OfficeActivity {

	private static final long serialVersionUID = -1376441281378278590L;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "office_case_activity_id")
	private Issue caseObject;

	public Issue getCaseObject() {
		return caseObject;
	}

	public void setCaseObject(Issue caseObject) {
		this.caseObject = caseObject;
	}
}
