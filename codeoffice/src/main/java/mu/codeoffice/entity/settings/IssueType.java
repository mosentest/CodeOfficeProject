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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.Enterprise;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "issuetype", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "enterprise_id"}))
public class IssueType implements Serializable {

	private static final long serialVersionUID = -8322633157968172805L;
	
	public static final String[] ICONS = {
		"all_unassigned", "blank", "bug", "defect", "delete", "documentation",
		"epic", "exclamation", "generic", "health", "improvement", "newfeature",
		"removefeature", "requirement", "sales", "story", "subtask", "task", "taskagile", "undefined"
	};
	
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

	@Column(name = "is_standard")
	private boolean standard;

	@Column(name = "icon")
	@NotEmpty
	private String icon;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "issueTypes")
	private List<IssueTypeScheme> issueTypeSchemes;
	
	public IssueType() {}
	
	public static boolean validateIcon(String icon) throws InformationException {
		for (String string : ICONS) {
			if (string.equals(icon)) {
				return true;
			}
		}
		throw new InformationException("Icon is invalid");
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IssueType)) {
			return false;
		}
		IssueType t = (IssueType) obj;
		return t.id != null && this.id != null && t.id.equals(this.id);
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
		return standard;
	}

	public void setStandard(boolean standard) {
		this.standard = standard;
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

}
