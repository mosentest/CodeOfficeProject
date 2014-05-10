package mu.codeoffice.entity;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("C")
public class CaseHistory extends OfficeHistory {

	private static final long serialVersionUID = 6017196451650119814L;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "office_case_history_id")
	private Case caseObject;

	public Case getCaseObject() {
		return caseObject;
	}

	public void setCaseObject(Case caseObject) {
		this.caseObject = caseObject;
	}

}
