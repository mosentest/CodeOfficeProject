package mu.codeoffice.entity.settings;

import java.io.Serializable;
import java.util.Locale;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import mu.codeoffice.entity.Enterprise;

@Entity
@Table(name = "settings_internationalization")
public class InternationalizationSettings implements Serializable {

	private static final long serialVersionUID = 4720358394692523462L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@Column(name = "default_language")
	private String defaultLanguage;

	@Column(name = "default_timezone_id")
	private String defaultTimeZoneID;

	@Transient
	private TimeZone defaultTimeZone;
	
	@Column(name = "default_locale")
	private String defaultLocaleString;
	
	@Transient
	private Locale defaultLocale;
	
	public InternationalizationSettings() {}

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

	public String getDefaultLanguage() {
		return defaultLanguage;
	}

	public void setDefaultLanguage(String defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	public String getDefaultTimeZoneID() {
		return defaultTimeZoneID;
	}

	public void setDefaultTimeZoneID(String defaultTimeZoneID) {
		this.defaultTimeZoneID = defaultTimeZoneID;
	}

	public Locale getDefaultLocale() {
		return defaultLocale;
	}

	public void setDefaultLocale(Locale defaultLocale) {
		this.defaultLocale = defaultLocale;
	}

	public String getDefaultLocaleString() {
		return defaultLocaleString;
	}

	public void setDefaultLocaleString(String defaultLocaleString) {
		this.defaultLocaleString = defaultLocaleString;
	}

	public TimeZone getDefaultTimeZone() {
		return defaultTimeZone;
	}

	public void setDefaultTimeZone(TimeZone defaultTimeZone) {
		this.defaultTimeZone = defaultTimeZone;
	}
	
}
