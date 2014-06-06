package mu.codeoffice.entity.settings;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import mu.codeoffice.entity.Enterprise;

@Entity
@Table(name = "settings_projecttemplate", uniqueConstraints = @UniqueConstraint(columnNames = { "name", "enterprise_id" }))
public class ProjectTemplate implements Serializable {

	private static final long serialVersionUID = -5180697553803867204L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;
	
	@Column(name = "name")
	@Size(max = 20)
	private String name;

	@Column(name = "description")
	@Size(max = 200)
	private String description;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "projectpermissionscheme_id")
	private ProjectPermissionScheme projectPermissionScheme;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "workflow_id")
	private WorkFlow workFlow;
	
	public ProjectTemplate() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProjectPermissionScheme getProjectPermissionScheme() {
		return projectPermissionScheme;
	}

	public void setProjectPermissionScheme(
			ProjectPermissionScheme projectPermissionScheme) {
		this.projectPermissionScheme = projectPermissionScheme;
	}

	public WorkFlow getWorkFlow() {
		return workFlow;
	}

	public void setWorkFlow(WorkFlow workFlow) {
		this.workFlow = workFlow;
	}
	
}
