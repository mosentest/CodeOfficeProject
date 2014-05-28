package mu.codeoffice.entity.settings;

import java.io.Serializable;
import java.util.List;

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
@Table(name = "settings_globalpermission")
public class GlobalPermissionSettings implements Serializable {

	private static final long serialVersionUID = -3478455507851817587L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@Column(name = "global_permission")
	@Enumerated(EnumType.STRING)
	private mu.codeoffice.security.GlobalPermission globalPermission;

	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "globalpermission_usergroup", uniqueConstraints = @UniqueConstraint(columnNames = {"globalpermission_gid", "usergroup_id"}),
        joinColumns = @JoinColumn(name = "globalpermission_gid", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "usergroup_id", referencedColumnName = "id"))
	private List<UserGroup> userGroups;

	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "globalpermission_user", uniqueConstraints = @UniqueConstraint(columnNames = {"globalpermission_uid", "user_id"}),
        joinColumns = @JoinColumn(name = "globalpermission_uid", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	private List<User> users;
	
	public GlobalPermissionSettings() {}

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

	public mu.codeoffice.security.GlobalPermission getGlobalPermission() {
		return globalPermission;
	}

	public void setGlobalPermission(mu.codeoffice.security.GlobalPermission globalPermission) {
		this.globalPermission = globalPermission;
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
	
}
