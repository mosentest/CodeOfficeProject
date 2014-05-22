package mu.codeoffice.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("P")
public class ProjectNote extends Note {

	private static final long serialVersionUID = -727167430656648892L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	private Project projectObject;

	public Project getProjectObject() {
		return projectObject;
	}

	public void setProjectObject(Project projectObject) {
		this.projectObject = projectObject;
	}

}
