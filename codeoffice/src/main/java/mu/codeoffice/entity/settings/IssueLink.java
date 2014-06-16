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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import mu.codeoffice.entity.Enterprise;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "issuelink", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "enterprise_id"}))
public class IssueLink implements Serializable {

	private static final long serialVersionUID = 8782629757866371748L;

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

	@Column(name = "outward_link")
	@NotEmpty
	@Size(max = 50)
	private String outwardLink;

	@Column(name = "inward_link")
	@Size(max = 50)
	@NotEmpty
	private String inwardLink;
	
	public IssueLink() {}

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

	public String getOutwardLink() {
		return outwardLink;
	}

	public void setOutwardLink(String outwardLink) {
		this.outwardLink = outwardLink;
	}

	public String getInwardLink() {
		return inwardLink;
	}

	public void setInwardLink(String inwardLink) {
		this.inwardLink = inwardLink;
	}
	
}
