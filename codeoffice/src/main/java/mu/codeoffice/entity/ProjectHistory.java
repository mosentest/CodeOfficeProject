package mu.codeoffice.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("H")
public class ProjectHistory extends OfficeHistory {

	private static final long serialVersionUID = 3492615518200114251L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "office_project_history_id")
	private Project projectObject;

	public Project getProjectObject() {
		return projectObject;
	}

	public void setProjectObject(Project projectObject) {
		this.projectObject = projectObject;
	}

}
