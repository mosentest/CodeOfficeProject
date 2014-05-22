package mu.codeoffice.repository.settings;

import mu.codeoffice.entity.settings.TimeTrackingSettings;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTrackingSettingsRepository extends JpaRepository<TimeTrackingSettings, Long> {

}
