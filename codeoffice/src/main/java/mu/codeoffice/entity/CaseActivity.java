package mu.codeoffice.entity;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("C")
public class CaseActivity extends OfficeActivity {

	private static final long serialVersionUID = -1376441281378278590L;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "office_case_activity_id")
	private Case caseObject;

	public Case getCaseObject() {
		return caseObject;
	}

	public void setCaseObject(Case caseObject) {
		this.caseObject = caseObject;
	}
}
