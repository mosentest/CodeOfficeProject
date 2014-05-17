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

@Entity
@Table(name = "office_project_component", uniqueConstraints = @UniqueConstraint(columnNames = {"code", "office_project_id"}))
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
	@JoinColumn(name = "office_project_id")
	private Project project;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "no_case")
	private int noCase;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "office_component_lead_id")
	private EnterpriseUser lead;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "office_component_reporter_id")
	private EnterpriseUser defaultReporter;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "office_component_assignee_id")
	private EnterpriseUser defaultAssignee;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "components")
	private List<Case> cases;
	
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
		Component c = (Component) o;
		return c.id != null && this.id != null && c.id.equals(this.id);
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

	public EnterpriseUser getLead() {
		return lead;
	}

	public void setLead(EnterpriseUser lead) {
		this.lead = lead;
	}

	public EnterpriseUser getDefaultReporter() {
		return defaultReporter;
	}

	public void setDefaultReporter(EnterpriseUser defaultReporter) {
		this.defaultReporter = defaultReporter;
	}

	public EnterpriseUser getDefaultAssignee() {
		return defaultAssignee;
	}

	public void setDefaultAssignee(EnterpriseUser defaultAssignee) {
		this.defaultAssignee = defaultAssignee;
	}

	public List<Case> getCases() {
		return cases;
	}

	public void setCases(List<Case> cases) {
		this.cases = cases;
	}
	
	public int getNoCase() {
		return noCase;
	}

	public void setNoCase(int noCase) {
		this.noCase = noCase;
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
