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
import javax.persistence.UniqueConstraint;

import mu.codeoffice.json.ComponentJSON;
import mu.codeoffice.json.JSONSerializable;

@Entity
@Table(name = "project_component", uniqueConstraints = @UniqueConstraint(columnNames = {"code", "project_id"}))
public class Component implements Serializable, JSONSerializable<Component> {
	
	private static final long serialVersionUID = 7413950969585396632L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@Column(name = "code")
	private String code;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	private Project project;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "total_issues")
	private int totalIssues;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "component_lead_id")
	private User lead;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "component_reporter_id")
	private User defaultReporter;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "component_assignee_id")
	private User defaultAssignee;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "components")
	private List<Issue> issues;
	
	public Component() {}

	@Override
	public ComponentJSON toJSONObject() {
		ComponentJSON json = new ComponentJSON();
		json.setCode(code);
		json.setDefaultAssignee(defaultAssignee.getFullName());
		json.setDefaultReporter(defaultReporter.getFullName());
		json.setLead(lead.getFullName());
		json.setId(id);
		json.setName(name);
		json.setDescription(description);
		return json;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Component)) {
			return false;
		}
		Component e = (Component) o;
		return e.id != null && this.id != null && e.id.equals(this.id);
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public User getLead() {
		return lead;
	}

	public void setLead(User lead) {
		this.lead = lead;
	}

	public User getDefaultReporter() {
		return defaultReporter;
	}

	public void setDefaultReporter(User defaultReporter) {
		this.defaultReporter = defaultReporter;
	}

	public User getDefaultAssignee() {
		return defaultAssignee;
	}

	public void setDefaultAssignee(User defaultAssignee) {
		this.defaultAssignee = defaultAssignee;
	}

	public List<Issue> getIssues() {
		return issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}
	
	public int getTotalIssues() {
		return totalIssues;
	}

	public void setTotalIssues(int totalIssues) {
		this.totalIssues = totalIssues;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}
	
}
