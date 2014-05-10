package mu.codeoffice.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
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

import mu.codeoffice.enums.HistoryType;

@Entity
@Table(name = "office_history_object")
public class HistoryObject implements Serializable {

	private static final long serialVersionUID = -6490104947928417148L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	@ManyToOne(cascade = CascadeType.REMOVE, optional = false)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "history_id")
	private OfficeHistory history;

	@Column(length = 3)
	@Enumerated(EnumType.STRING)
	private HistoryType type;

	@Column(name = "old_value")
	private String oldValue;

	@Column(name = "new_value")
	private String newValue;
	
	public HistoryObject() {
		
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

	public OfficeHistory getHistory() {
		return history;
	}

	public void setHistory(OfficeHistory history) {
		this.history = history;
	}

	public HistoryType getType() {
		return type;
	}

	public void setType(HistoryType type) {
		this.type = type;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

}
