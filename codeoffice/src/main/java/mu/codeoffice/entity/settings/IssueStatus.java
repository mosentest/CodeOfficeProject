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
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "settings_issuestatus", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "enterprise_id"}))
public class IssueStatus implements Serializable {
	
	public static final String[] ICONS = {
		"assigned", "closed", "document", "down", "email", "generic", "information", "inprogress",
		"invisible", "needinfo", "open", "reopened", "resolved", "trash", "unassigned", "up", "visible"
	};

	private static final long serialVersionUID = -8322633157968172805L;
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

	@Column(name = "icon")
	@NotEmpty
	private String icon;
	
	@Column(name = "status_order")
	@Range(min = 0)
	private int order;

	@Column(name = "color")
	private String color;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "issueStatus")
	private List<WorkFlow> workFlows;
	
	public IssueStatus() {}
	
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
		if (!(obj instanceof IssueStatus)) {
			return false;
		}
		IssueStatus o = (IssueStatus) obj;
		return o.id != null && this.id != null && o.id.equals(this.id);
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public List<WorkFlow> getWorkFlows() {
		return workFlows;
	}

	public void setWorkFlows(List<WorkFlow> workFlows) {
		this.workFlows = workFlows;
	}

}
