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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.User;

@Entity
@Table(name = "projectrole", uniqueConstraints = @UniqueConstraint(columnNames = { "name", "enterprise_id" }))
public class ProjectRole implements Serializable {

	private static final long serialVersionUID = 5484556658802063500L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;
	
	@Column(name = "name")
	@Pattern(regexp = "[a-zA-Z]+(( )?[a-zA-Z])+")
	@Size(max = 20)
	private String name;

	@Column(name = "description")
	@Size(max = 200)
	private String description;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "projectrole_user", uniqueConstraints = @UniqueConstraint(columnNames = {"projectrole_id", "user_id"}),
        joinColumns = @JoinColumn(name = "projectrole_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	private List<User> defaultMembers;

	public ProjectRole() {}

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

	public List<User> getDefaultMembers() {
		return defaultMembers;
	}

	public void setDefaultMembers(List<User> defaultMembers) {
		this.defaultMembers = defaultMembers;
	}

}
