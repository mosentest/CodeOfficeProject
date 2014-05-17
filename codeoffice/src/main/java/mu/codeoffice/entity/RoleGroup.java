package mu.codeoffice.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name = "office_rolegroup", uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "role_id"}))
public class RoleGroup implements Serializable {

	private static final long serialVersionUID = -1231511159602314501L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	private Project project;

	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "role_id")
	private ProjectRole role;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "office_rolegroup_users", 
    	joinColumns = @JoinColumn(name = "rolegroup_id", referencedColumnName = "id"), 
    	inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	private List<EnterpriseUser> users;
	
	public RoleGroup() {}
	
	public static boolean hasUser(List<RoleGroup> roleGroup, EnterpriseUser user) {
		for (RoleGroup group : roleGroup) {
			if (group.hasUser(user)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasUser(EnterpriseUser user) {
		for (EnterpriseUser u : users) {
			if (u.getId() == user.getId()) {
				return true;
			}
		}
		return false;
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

	public List<EnterpriseUser> getUsers() {
		return users;
	}

	public void setUsers(List<EnterpriseUser> users) {
		this.users = users;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public ProjectRole getRole() {
		return role;
	}

	public void setRole(ProjectRole role) {
		this.role = role;
	}

}
