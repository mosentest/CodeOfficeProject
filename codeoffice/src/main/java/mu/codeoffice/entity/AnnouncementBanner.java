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

@Entity
@Table(name = "settings_announcement_banner")
public class AnnouncementBanner implements Serializable {

	private static final long serialVersionUID = -680703454866696090L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;
	
	@Column(name = "accouncement")
	private String accouncement;

	@Column(name = "enable_public_mode")
	private boolean enablePublicMode;
	
	public AnnouncementBanner() {}

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

	public String getAccouncement() {
		return accouncement;
	}

	public void setAccouncement(String accouncement) {
		this.accouncement = accouncement;
	}

	public boolean isEnablePublicMode() {
		return enablePublicMode;
	}

	public void setEnablePublicMode(boolean enablePublicMode) {
		this.enablePublicMode = enablePublicMode;
	}
	
}
