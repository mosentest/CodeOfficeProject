package mu.codeoffice.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "office_worknote")
public class WorkNote implements Serializable {

	private static final long serialVersionUID = 4704097564463879200L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(cascade = CascadeType.REMOVE, optional = false)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;
	
	@ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_id")
	private EnterpriseUser creator;
	
	@Column(name = "summary")
	private String summary;

	@Column(name = "content")
	private String content;

	@Column(name = "create_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date create;
	
	@Column(name = "update_time")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date update;

	@Column(name = "visibility")
	private int visibility;
	
	@Transient
	private Visibility visibilitybType;
	
	public WorkNote() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreate() {
		return create;
	}

	public void setCreate(Date create) {
		this.create = create;
	}

	public Date getUpdate() {
		return update;
	}

	public void setUpdate(Date update) {
		this.update = update;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
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

	public Visibility getVisibilitybType() {
		return visibilitybType;
	}

	public void setVisibilitybType(Visibility visibilitybType) {
		this.visibilitybType = visibilitybType;
	}
	
}
