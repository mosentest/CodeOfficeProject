package mu.codeoffice.entity.settings;

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

import mu.codeoffice.entity.Enterprise;

@Entity
@Table(name = "settings_issuetype", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "enterprise_id"}))
public class IssueType implements Serializable {
	
	private static final String[] VALID_ICONS = {
		"all_unassigned", "blank", "bug", "defect", "delete", "documentation",
		"epic", "exclamation", "generic", "health", "improvement", "newfeature",
		"removefeature", "requirement", "sales", "story", "subtask", "task", "taskagile", "undefined"
	};

	private static final long serialVersionUID = -8322633157968172805L;
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

	@Column(name = "is_standard")
	private boolean isStandard;

	@Column(name = "default_type")
	private boolean defaultType;

	@Column(name = "icon")
	private String icon;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "issueTypes")
	private List<IssueTypeScheme> issueTypeSchemes;
	
	public IssueType() {}
	
	public static boolean isValidIcon(String icon) {
		for (String string : VALID_ICONS) {
			if (string.equals(icon)) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isStandard() {
		return isStandard;
	}

	public void setStandard(boolean isStandard) {
		this.isStandard = isStandard;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<IssueTypeScheme> getIssueTypeSchemes() {
		return issueTypeSchemes;
	}

	public void setIssueTypeSchemes(List<IssueTypeScheme> issueTypeSchemes) {
		this.issueTypeSchemes = issueTypeSchemes;
	}

	public boolean isDefaultType() {
		return defaultType;
	}

	public void setDefaultType(boolean defaultType) {
		this.defaultType = defaultType;
	}

}
