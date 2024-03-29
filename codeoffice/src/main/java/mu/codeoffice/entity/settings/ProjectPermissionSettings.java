package mu.codeoffice.entity.settings;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.User;
import mu.codeoffice.entity.UserGroup;

@Entity
@Table(name = "projectpermission")
public class ProjectPermissionSettings implements Serializable {

	private static final long serialVersionUID = -3478455507851817587L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "projectpermissionscheme_id")
	private ProjectPermissionScheme projectPermissionScheme;

	@Column(name = "project_permission")
	@Enumerated(EnumType.STRING)
	private mu.codeoffice.security.ProjectPermission projectPermission;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "projectpermission_usergroup", uniqueConstraints = @UniqueConstraint(columnNames = {"projectpermission_gid", "usergroup_id"}),
        joinColumns = @JoinColumn(name = "projectpermission_gid", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "usergroup_id", referencedColumnName = "id"))
	private List<UserGroup> userGroups;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "projectpermission_user", uniqueConstraints = @UniqueConstraint(columnNames = {"projectpermission_uid", "user_id"}),
        joinColumns = @JoinColumn(name = "projectpermission_uid", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	private List<User> users;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "projectpermission_projectrole", uniqueConstraints = @UniqueConstraint(columnNames = {"projectpermission_rid", "role_id"}),
        joinColumns = @JoinColumn(name = "projectpermission_rid", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<ProjectRole> projectRoles;
	
	public ProjectPermissionSettings() {}

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

	public mu.codeoffice.security.ProjectPermission getProjectPermission() {
		return projectPermission;
	}

	public void setProjectPermission(mu.codeoffice.security.ProjectPermission projectPermission) {
		this.projectPermission = projectPermission;
	}

	public List<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public ProjectPermissionScheme getProjectPermissionScheme() {
		return projectPermissionScheme;
	}

	public void setProjectPermissionScheme(ProjectPermissionScheme projectPermissionScheme) {
		this.projectPermissionScheme = projectPermissionScheme;
	}

	public List<ProjectRole> getProjectRoles() {
		return projectRoles;
	}

	public void setProjectRoles(List<ProjectRole> projectRoles) {
		this.projectRoles = projectRoles;
	}
	
}
