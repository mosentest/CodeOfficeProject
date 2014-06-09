package mu.codeoffice.repository.settings;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.TimeTrackingSettings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TimeTrackingSettingsRepository extends JpaRepository<TimeTrackingSettings, Long> {

	@Query("SELECT t FROM TimeTrackingSettings t WHERE t.enterprise = :enterprise")
	public TimeTrackingSettings getTimeTrackingSettings(@Param("enterprise") Enterprise enterprise);
	
}
