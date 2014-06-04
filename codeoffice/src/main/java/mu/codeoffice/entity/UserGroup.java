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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import mu.codeoffice.entity.settings.GlobalPermissionSettings;
import mu.codeoffice.json.JSONSerializable;
import mu.codeoffice.json.UserGroupJSON;

@Entity
@Table(name = "usergroup")
public class UserGroup implements Serializable, JSONSerializable<UserGroup> {

	private static final long serialVersionUID = -1838588996310300202L;
	
	private static final String[] SORTABLE_COLUMNS = {
		"name", "description", "userCount"
	};
	
	private static final String DEFAULT_COLUMN = "name";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@Column(name = "default_group")
	private boolean defaultGroup;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "user_count")
	private int userCount;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "userGroups")
	private List<GlobalPermissionSettings> globalPermissions;

	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usergroup_user", uniqueConstraints = @UniqueConstraint(columnNames = {"usergroup_id", "user_id"}),
        	joinColumns = @JoinColumn(name = "usergroup_id", referencedColumnName = "id"), 
        	inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	private List<User> users;
	
	public UserGroup() {}

	@Override
	public UserGroupJSON toJSONObject() {
		UserGroupJSON json = new UserGroupJSON();
		json.setId(id);
		json.setName(name);
		return json;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof UserGroup)) {
			return false;
		}
		UserGroup u = (UserGroup) obj;
		return u.id != null && this.id != null && u.id.equals(this.id);
	}
	
	public static String getSortColumn(String column) {
		for (String c : SORTABLE_COLUMNS) {
			if (c.equals(column)) {
				return c;
			}
		}
		return DEFAULT_COLUMN;
	}

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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public boolean isDefaultGroup() {
		return defaultGroup;
	}

	public void setDefaultGroup(boolean defaultGroup) {
		this.defaultGroup = defaultGroup;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public List<GlobalPermissionSettings> getGlobalPermissions() {
		return globalPermissions;
	}

	public void setGlobalPermissions(
			List<GlobalPermissionSettings> globalPermissions) {
		this.globalPermissions = globalPermissions;
	}
	
}
