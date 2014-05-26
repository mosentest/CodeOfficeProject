package mu.codeoffice.entity.settings;

import java.io.Serializable;
import java.util.Locale;
import java.util.Properties;
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
public class InternationalizationSettings implements SettingsEntity, Serializable {

	private static final long serialVersionUID = 4720358394692523462L;
	
	public static final TimeZone[] SUPPORTED_TIMEZONE = {
		TimeZone.getTimeZone("CET"),
		TimeZone.getTimeZone("CTT"),
		TimeZone.getTimeZone("PST")
	};
	
	public static final Locale[] SUPPORTED_LOCALE = {
		Locale.US,
		Locale.CHINA,
		new Locale("sv", "SE")
	};

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@Column(name = "default_timezone_id")
	private String defaultTimeZoneID;

	@Transient
	private TimeZone defaultTimeZone;
	
	@Column(name = "default_locale")
	private String defaultLocaleString;
	
	@Transient
	private Locale defaultLocale;
	
	public InternationalizationSettings() {}
	
	public static boolean isSupportedLocale(String localeString) {
		for (Locale locale : SUPPORTED_LOCALE) {
			System.out.println(locale.toString());
			if (locale.toString().equals(localeString)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isSupportedTimeZone(String timeZoneID) {
		for (TimeZone timeZone : SUPPORTED_TIMEZONE) {
			if (timeZone.getID().equals(timeZoneID)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void setDefaultSettings(Properties properties) {
		defaultTimeZoneID = properties.getProperty("settings.i18n.default.defaultTimeZoneID");
		defaultLocaleString = properties.getProperty("settings.i18n.default.defaultLocaleString");
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
