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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.Project;

@Entity
@Table(name = "issuetype_scheme", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "enterprise_id"}))
public class IssueTypeScheme implements Serializable {

	private static final long serialVersionUID = -5849149551116388296L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;
	
	@Column(name = "name")
	@Size(max = 30)
	private String name;

	@Column(name = "description")
	@Size(max = 200)
	private String description;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "issueTypeScheme")
	private List<Project> projects;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "issuetypescheme_types", uniqueConstraints = @UniqueConstraint(columnNames = {"issuetype_scheme_id", "type_id"}),
        joinColumns = @JoinColumn(name = "issuetype_scheme_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "type_id", referencedColumnName = "id"))
	@OrderBy("name ASC")
	private List<IssueType> issueTypes;
	
	public IssueTypeScheme() {}

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

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<IssueType> getIssueTypes() {
		return issueTypes;
	}

	public void setIssueTypes(List<IssueType> issueTypes) {
		this.issueTypes = issueTypes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
