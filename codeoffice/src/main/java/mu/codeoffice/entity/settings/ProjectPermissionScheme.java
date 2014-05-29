package mu.codeoffice.entity.settings;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.User;

@Entity
@Table(name = "projectpermission_scheme", uniqueConstraints = @UniqueConstraint(columnNames = { "name", "enterprise_id" }))
public class ProjectPermissionScheme implements Serializable {

	private static final long serialVersionUID = -4340120129502238222L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_id")
	private User creator;

	@Column(name = "name")
	private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectPermissionScheme")
	private List<Project> projects;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "projectPermissionScheme")
	private List<ProjectPermissionSettings> projectPermissionSettings;
    
    public ProjectPermissionScheme() {}

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

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<ProjectPermissionSettings> getProjectPermissionSettings() {
		return projectPermissionSettings;
	}

	public void setProjectPermissionSettings(
			List<ProjectPermissionSettings> projectPermissionSettings) {
		this.projectPermissionSettings = projectPermissionSettings;
	}

}
