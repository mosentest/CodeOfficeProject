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

@Entity
@Table(name = "enterprise_user")
public class EnterpriseUser implements Serializable {

	private static final long serialVersionUID = 7445898962052022294L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

    @Column(name = "account", nullable = false, unique = true)
	private String account;

    @Column(name = "email", nullable = false, unique = true)
	private String email;

    @Column(name = "password_hash", nullable = false)
	private String password;
	
    @Column(name = "gender")
	private boolean gender;

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

    @Column(name = "authority")
    private int authority;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "enterpriseUsers")
	private List<EnterpriseUserGroup> groups;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "office_watcher_case", 
 			joinColumns = @JoinColumn(name = "enterprise_user_id", referencedColumnName = "id"), 
 			inverseJoinColumns = @JoinColumn(name = "case_id", referencedColumnName = "id"))
	private List<Case> watching;
    
    public EnterpriseUser() {}
    
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

	public int getAuthority() {
		return authority;
	}

	public void setAuthority(int authority) {
		this.authority = authority;
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

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
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

	public List<EnterpriseUserGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<EnterpriseUserGroup> groups) {
		this.groups = groups;
	}

	public List<Case> getWatching() {
		return watching;
	}

	public void setWatching(List<Case> watching) {
		this.watching = watching;
	}
	
}
