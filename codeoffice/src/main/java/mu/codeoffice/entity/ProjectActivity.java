package mu.codeoffice.entity;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("P")
public class ProjectActivity extends OfficeActivity {

	private static final long serialVersionUID = 3307131411326668519L;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "office_activity_project_id")
	private Project projectObject;

	public Project getProjectObject() {
		return projectObject;
	}

	public void setProjectObject(Project projectObject) {
		this.projectObject = projectObject;
	}
	

}
