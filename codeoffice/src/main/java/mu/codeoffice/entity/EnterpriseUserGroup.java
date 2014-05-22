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

@Entity
@Table(name = "enterprise_usergroup", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "enterprise_id"}))
public class EnterpriseUserGroup implements Serializable {

	private static final long serialVersionUID = -7084742565850981601L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;
	
	@Column(name = "users")
	private int users;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usergroup_user",
			joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	private List<EnterpriseUser> enterpriseUsers;
	
	public EnterpriseUserGroup() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public int getUsers() {
		return users;
	}

	public void setUsers(int users) {
		this.users = users;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

}
