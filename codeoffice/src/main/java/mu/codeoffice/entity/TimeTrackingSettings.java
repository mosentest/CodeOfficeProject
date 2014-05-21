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
@Table(name = "settings_time_tracking")
public class TimeTrackingSettings implements Serializable {
	
	private static final long serialVersionUID = -6441299912952667018L;

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

	@Column(name = "default_time_trakning_unit")
	private String defaultTimeTrakningUnit;
	
	public TimeTrackingSettings() {}

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

	public String getDefaultTimeTrakningUnit() {
		return defaultTimeTrakningUnit;
	}

	public void setDefaultTimeTrakningUnit(String defaultTimeTrakningUnit) {
		this.defaultTimeTrakningUnit = defaultTimeTrakningUnit;
	}
	
}
