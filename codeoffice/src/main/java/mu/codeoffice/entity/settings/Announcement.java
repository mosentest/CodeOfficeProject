package mu.codeoffice.entity.settings;

import java.io.Serializable;
import java.util.Properties;

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

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.enums.AnnouncementLevel;

@Entity
@Table(name = "settings_announcement")
public class Announcement implements Serializable, SettingsEntity {

	private static final long serialVersionUID = -680703454866696090L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;
	
	@Column(name = "announcement")
	private String announcement;

	@Column(name = "enable_public_mode")
	private boolean enablePublicMode;

	@Column(name = "announcement_level")
	@Enumerated(EnumType.STRING)
	private AnnouncementLevel announcementLevel;

	@Column(name = "enabled")
	private boolean enabled;
	
	public Announcement() {}

	@Override
	public void setDefaultSettings(Properties properties) {
		announcementLevel = AnnouncementLevel.valueOf(properties.getProperty("settings.announcement.default.announcementLevel"));
	}
	
	public void copyInformationTo(Announcement announcement) {
		announcement.setAnnouncement(this.announcement);
		announcement.setEnabled(this.enabled);
		announcement.setEnablePublicMode(this.enablePublicMode);
		announcement.setAnnouncementLevel(this.announcementLevel);
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

	public String getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}

	public boolean isEnablePublicMode() {
		return enablePublicMode;
	}

	public void setEnablePublicMode(boolean enablePublicMode) {
		this.enablePublicMode = enablePublicMode;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public AnnouncementLevel getAnnouncementLevel() {
		return announcementLevel;
	}

	public void setAnnouncementLevel(AnnouncementLevel announcementLevel) {
		this.announcementLevel = announcementLevel;
	}
	
}
