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

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.Enterprise;

@Entity
@Table(name = "settings_time_tracking")
public class TimeTrackingSettings implements SettingsEntity, Serializable {
	
	private static final long serialVersionUID = -6441299912952667018L;
	
	public static final String[] TIMETRACKING_UNIT = {
		"Day", "Hour", "Minute", "Second"
	};
	
	public static final String[] DISPLAY_FORMAT = {
		"dd", "HH", "mm", "ss"
	};
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@Column(name = "enabled")
	private boolean enabled;

	@Column(name = "work_hours_per_day")
	private int workHoursPerDay;

	@Column(name = "work_days_per_week")
	private int workDaysPerWeek;

	@Column(name = "time_estimates_display_format")
	private String timeEstimatesDisplayFormat;

	@Column(name = "default_time_tracking_unit")
	private String defaultTimeTrackingUnit;
	
	public TimeTrackingSettings() {}
	
	public static void validateDisplayFormat(String format) throws InformationException {
		String[] values = format.split(",");
		for (String value : values) {
			boolean valid = false;
			for (String string : DISPLAY_FORMAT) {
				if (string.equals(value.trim())) {
					valid = true;
					break;
				}
			}
			if (!valid) {
				throw new InformationException("Display Format is invalid");
			}
		}
	}
	
	public static void validateTimeTrackingUnit(String unit) throws InformationException {
		for (String string : TIMETRACKING_UNIT) {
			if (string.equals(unit)) {
				return;
			}
		}
		throw new InformationException("Invalid Time Tracking Unit.");
	}

	@Override
	public void setDefaultSettings(Properties properties) {
		enabled = Boolean.parseBoolean(properties.getProperty("settings.timetracking.default.enabled"));
		workHoursPerDay = Integer.parseInt(properties.getProperty("settings.timetracking.default.workHoursPerDay"));
		workDaysPerWeek = Integer.parseInt(properties.getProperty("settings.timetracking.default.workDaysPerWeek"));
		timeEstimatesDisplayFormat = properties.getProperty("settings.timetracking.default.timeEstimatesDisplayFormat");
		defaultTimeTrackingUnit = properties.getProperty("settings.timetracking.default.defaultTimeTrackingUnit");
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getWorkHoursPerDay() {
		return workHoursPerDay;
	}

	public void setWorkHoursPerDay(int workHoursPerDay) {
		this.workHoursPerDay = workHoursPerDay;
	}

	public int getWorkDaysPerWeek() {
		return workDaysPerWeek;
	}

	public void setWorkDaysPerWeek(int workDaysPerWeek) {
		this.workDaysPerWeek = workDaysPerWeek;
	}

	public String getTimeEstimatesDisplayFormat() {
		return timeEstimatesDisplayFormat;
	}

	public void setTimeEstimatesDisplayFormat(String timeEstimatesDisplayFormat) {
		this.timeEstimatesDisplayFormat = timeEstimatesDisplayFormat;
	}

	public String getDefaultTimeTrackingUnit() {
		return defaultTimeTrackingUnit;
	}

	public void setDefaultTimeTrackingUnit(String defaultTimeTrackingUnit) {
		this.defaultTimeTrackingUnit = defaultTimeTrackingUnit;
	}
	
}
