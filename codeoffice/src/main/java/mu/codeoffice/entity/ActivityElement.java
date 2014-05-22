package mu.codeoffice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import mu.codeoffice.enums.ActivityType;

@Entity
@Table(name = "activity_element")
public class ActivityElement implements Serializable {

	private static final long serialVersionUID = 5371097946101843439L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "activity_id")
	private Activity activity;
	
	@Column(name = "description")
	protected String description;

	@Column(length = 3)
	@Enumerated(EnumType.STRING)
	protected ActivityType type;
	
	@Column(name = "association_id")
	private long associationId;

	@Transient
	private Object associationObject;
	
	public ActivityElement() {}

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

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ActivityType getType() {
		return type;
	}

	public void setType(ActivityType type) {
		this.type = type;
	}

	public long getAssociationId() {
		return associationId;
	}

	public void setAssociationId(long associationId) {
		this.associationId = associationId;
	}

	public Object getAssociationObject() {
		return associationObject;
	}

	public void setAssociationObject(Object associationObject) {
		this.associationObject = associationObject;
	}

}
