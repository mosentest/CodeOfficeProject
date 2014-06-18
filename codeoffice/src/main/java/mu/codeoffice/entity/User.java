package mu.codeoffice.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import mu.codeoffice.entity.settings.GlobalPermissionSettings;
import mu.codeoffice.entity.settings.ProjectRole;
import mu.codeoffice.json.JSONSerializable;
import mu.codeoffice.json.UserJSON;
import mu.codeoffice.security.GlobalPermission;

@Entity
@Table(name = "enterprise_user")
public class User implements Serializable, JSONSerializable<User> {

	private static final long serialVersionUID = 7445898962052022294L;
	
	private static final String[] SORTABLE_COLUMNS = {
		"firstName", "lastName", "account", "email", "globalPermissionValue", "projectPermissionValue"
	};
	
	private static final String DEFAULT_COLUMN = "firstName";

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

    @Column(name = "account", nullable = false, unique = true)
	private String account;

    @Column(name = "email", nullable = false, unique = true)
	private String email;

    @Column(name = "password_hash", nullable = false)
	private String password;

	@Column(name = "create_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date create;

	@Column(name = "login_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date login;

    @Column(name = "profile_path")
	private String profilePath;

    @Column(name = "first_name")
	private String firstName;

    @Column(name = "last_name")
	private String lastName;

    @Column(name = "phone")
	private String phone;

    @Column(name = "address")
	private String address;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
	private List<GlobalPermissionSettings> globalPermissionSettings;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
	private List<UserGroup> userGroups;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "defaultMembers")
	private List<ProjectRole> projectRoles;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "issue_watchlist", uniqueConstraints = @UniqueConstraint(columnNames = {"user_wid", "issue_id"}),
 			joinColumns = @JoinColumn(name = "user_wid", referencedColumnName = "id"), 
 			inverseJoinColumns = @JoinColumn(name = "issue_id", referencedColumnName = "id"))
	private List<Issue> watching;
	
	@Transient
	private List<GlobalPermission> globalPermissions;
    
    public User() {}

	@Override
	public UserJSON toJSONObject() {
		UserJSON json = new UserJSON();
		json.setEmail(email);
		json.setId(id);
		json.setLastName(lastName);
		json.setFirstName(firstName);
		return json;
	}
	
	public static String getSortColumn(String column) {
		for (String c : SORTABLE_COLUMNS) {
			if (c.equals(column)) {
				return c;
			}
		}
		return DEFAULT_COLUMN;
	}
	
	public void setGlobalPermissions(List<GlobalPermission> globalPermissions) {
		this.globalPermissions = globalPermissions;
	}
    
    public List<GlobalPermission> getGlobalPermissions() {
    	return globalPermissions;
    }
	
	public List<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	public void setGlobalPermissionSettings(List<GlobalPermissionSettings> globalPermissionSettings) {
		this.globalPermissionSettings = globalPermissionSettings;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof User)) {
			return false;
		}
		User e = (User) o;
		return e.id != null && this.id != null && e.id.equals(this.id);
	}

    
    public String getNameLink() {
    	return lastName + "_" + firstName;
    }
    
    public String getFullName() {
    	return lastName + " " + firstName;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreate() {
		return create;
	}

	public void setCreate(Date create) {
		this.create = create;
	}

	public Date getLogin() {
		return login;
	}

	public void setLogin(Date login) {
		this.login = login;
	}

	public String getProfilePath() {
		return profilePath;
	}

	public void setProfilePath(String profilePath) {
		this.profilePath = profilePath;
	}

	public List<Issue> getWatching() {
		return watching;
	}

	public void setWatching(List<Issue> watching) {
		this.watching = watching;
	}

	public List<ProjectRole> getProjectRoles() {
		return projectRoles;
	}

	public void setProjectRoles(List<ProjectRole> projectRoles) {
		this.projectRoles = projectRoles;
	}

	public List<GlobalPermissionSettings> getGlobalPermissionSettings() {
		return globalPermissionSettings;
	}
	
}
