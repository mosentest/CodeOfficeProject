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
@Table(name = "history")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "history_type", discriminatorType = DiscriminatorType.STRING)
public class History implements Serializable {

	private static final long serialVersionUID = 30254826000132860L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@ManyToOne(optional = false)
	@JoinColumn(name = "creator_id")
	protected EnterpriseUser creator;

	@Column(name = "create_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date create;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "history")
	protected List<HistoryElement> historyElements;

	public History() {
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

	public EnterpriseUser getCreator() {
		return creator;
	}

	public void setCreator(EnterpriseUser creator) {
		this.creator = creator;
	}

	public Date getCreate() {
		return create;
	}

	public void setCreate(Date create) {
		this.create = create;
	}

	public List<HistoryElement> getHistoryElements() {
		return historyElements;
	}

	public void setHistoryElements(List<HistoryElement> historyElements) {
		this.historyElements = historyElements;
	}

}
