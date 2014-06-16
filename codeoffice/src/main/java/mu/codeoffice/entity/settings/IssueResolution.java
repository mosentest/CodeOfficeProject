package mu.codeoffice.entity.settings;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import mu.codeoffice.entity.Enterprise;

import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "issueresolution", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "enterprise_id"}))
public class IssueResolution implements Serializable {

	private static final long serialVersionUID = -8322633157968172805L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;
	
	@Column(name = "name")
	@Size(max = 20)
	private String name;

	@Column(name = "description")
	@Size(max = 200)
	private String description;
	
	@Column(name = "resolution_order")
	@Range(min = 0)
	private int order;
	
	@Column(name = "color")
	private String color;
	
	public IssueResolution() {}

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

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
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

}
