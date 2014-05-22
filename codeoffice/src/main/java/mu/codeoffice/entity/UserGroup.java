package mu.codeoffice.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mu.codeoffice.entity.settings.GlobalPermission;

@Entity
@Table(name = "usergroup")
public class UserGroup implements Serializable {

	private static final long serialVersionUID = -1838588996310300202L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@Column(name = "default_group")
	private boolean defaultGroup;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "userGroups")
	private List<GlobalPermission> globalPermissions;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "userGroups")
	private List<GlobalPermission> projectPermissions;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "userGroups")
	private List<EnterpriseUser> users;
	
	public UserGroup() {}

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<EnterpriseUser> getUsers() {
		return users;
	}

	public void setUsers(List<EnterpriseUser> users) {
		this.users = users;
	}

	public List<GlobalPermission> getProjectPermissions() {
		return projectPermissions;
	}

	public void setProjectPermissions(List<GlobalPermission> projectPermissions) {
		this.projectPermissions = projectPermissions;
	}

	public boolean isDefaultGroup() {
		return defaultGroup;
	}

	public void setDefaultGroup(boolean defaultGroup) {
		this.defaultGroup = defaultGroup;
	}
	
}
