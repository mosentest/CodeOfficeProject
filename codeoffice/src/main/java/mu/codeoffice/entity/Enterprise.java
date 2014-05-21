package mu.codeoffice.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "enterprise")
public class Enterprise implements Serializable {

	private static final long serialVersionUID = 782680675742454535L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    @Column(name = "code", nullable = false, unique = true, length = 3)
	private String code;

    @Column(name = "name", nullable = false, unique = true)
	private String name;

    @Column(name = "description")
	private String description;	
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "enterprise")
    private List<ProjectCategory> projectCategories;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "enterprise")
    private List<Project> projects;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "enterprise")
    private List<Issue> projectCases;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "enterprise")
    private List<Component> projectComponents;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "enterprise")
    private List<Version> projectVersions;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "enterprise")
    private List<Label> projectLabels;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "enterprise")
    private List<EnterpriseUser> users;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "enterprise")
    private List<ProjectRole> projectRoles;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "enterprise")
    private List<RoleGroup> projectRoleGroups;
    
    public Enterprise() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public List<ProjectCategory> getProjectCategories() {
		return projectCategories;
	}

	public void setProjectCategories(List<ProjectCategory> projectCategories) {
		this.projectCategories = projectCategories;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<Issue> getProjectCases() {
		return projectCases;
	}

	public void setProjectCases(List<Issue> projectCases) {
		this.projectCases = projectCases;
	}

	public List<Component> getProjectComponents() {
		return projectComponents;
	}

	public void setProjectComponents(List<Component> projectComponents) {
		this.projectComponents = projectComponents;
	}

	public List<Version> getProjectVersions() {
		return projectVersions;
	}

	public void setProjectVersions(List<Version> projectVersions) {
		this.projectVersions = projectVersions;
	}

	public List<Label> getProjectLabels() {
		return projectLabels;
	}

	public void setProjectLabels(List<Label> projectLabels) {
		this.projectLabels = projectLabels;
	}

	public List<EnterpriseUser> getUsers() {
		return users;
	}

	public void setUsers(List<EnterpriseUser> users) {
		this.users = users;
	}

	public List<ProjectRole> getProjectRoles() {
		return projectRoles;
	}

	public void setProjectRoles(List<ProjectRole> projectRoles) {
		this.projectRoles = projectRoles;
	}

	public List<RoleGroup> getProjectRoleGroups() {
		return projectRoleGroups;
	}

	public void setProjectRoleGroups(List<RoleGroup> projectRoleGroups) {
		this.projectRoleGroups = projectRoleGroups;
	}    
    
}
