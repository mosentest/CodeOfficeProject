package mu.codeoffice.entity.settings;

import java.io.Serializable;
import java.util.Properties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mu.codeoffice.entity.Enterprise;

@Entity
@Table(name = "settings_attachment")
public class AttachmentSettings implements SettingsEntity, Serializable {

	private static final long serialVersionUID = -1831132373213073918L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;
	
	@Column(name = "attachment_path")
	private String attachmentPath;

	@Column(name = "attachment_size")
	private long attachmentSize;

	@Column(name = "enable_thumbnails")
	private boolean enableThumbnails;

	@Column(name = "enable_zip_support")
	private boolean enableZipSupport;
	
	public AttachmentSettings() {}

	@Override
	public void setDefaultSettings(Properties properties) {
		attachmentPath = properties.getProperty("settings.attachment.default.attachmentPath").replace("{enterprise}", enterprise.getCode());
		attachmentSize = Long.parseLong(properties.getProperty("settings.attachment.default.attachmentSize"));
		enableThumbnails = Boolean.parseBoolean(properties.getProperty("settings.attachment.default.enableThumbnails"));
		enableZipSupport = Boolean.parseBoolean(properties.getProperty("settings.attachment.default.enableZipSupport"));
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

	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	public long getAttachmentSize() {
		return attachmentSize;
	}

	public void setAttachmentSize(long attachmentSize) {
		this.attachmentSize = attachmentSize;
	}

	public boolean isEnableThumbnails() {
		return enableThumbnails;
	}

	public void setEnableThumbnails(boolean enableThumbnails) {
		this.enableThumbnails = enableThumbnails;
	}

	public boolean isEnableZipSupport() {
		return enableZipSupport;
	}

	public void setEnableZipSupport(boolean enableZipSupport) {
		this.enableZipSupport = enableZipSupport;
	}
	
}
