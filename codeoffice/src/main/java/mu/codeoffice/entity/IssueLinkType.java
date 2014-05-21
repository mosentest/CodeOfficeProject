package mu.codeoffice.entity;

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

@Entity
@Table(name = "settings_issuelink", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "enterprise_id"}))
public class IssueLinkType implements Serializable {

	private static final long serialVersionUID = 8782629757866371748L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;
	
	@Column(name = "name")
	private String name;

	@Column(name = "outward_link")
	private String outwardLink;

	@Column(name = "inward_link")
	private String inwardLink;
	
	public IssueLinkType() {}

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
