package mu.codeoffice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "office_activity")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
	    name = "acvitity_type",
	    discriminatorType = DiscriminatorType.STRING
)
public class OfficeActivity implements Serializable {

	private static final long serialVersionUID = -4415448978080731131L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	@ManyToOne(cascade = CascadeType.REMOVE, optional = false)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@ManyToOne(cascade = CascadeType.REMOVE, optional = false)
	@JoinColumn(name = "enterprise_user_id")
	protected EnterpriseUser user;

	@Column(name = "create_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date create;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "activity")
	protected List<ActivityObject> activityObjects;
	
	public OfficeActivity() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreate() {
		return create;
	}

	public void setCreate(Date create) {
		this.create = create;
	}

	public List<ActivityObject> getActivityObjects() {
		return activityObjects;
	}

	public void setActvityObjects(List<ActivityObject> activityObjects) {
		this.activityObjects = activityObjects;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public EnterpriseUser getUser() {
		return user;
	}

	public void setUser(EnterpriseUser user) {
		this.user = user;
	}

	public void setActivityObjects(List<ActivityObject> activityObjects) {
		this.activityObjects = activityObjects;
	}
	
}
