package mu.codeoffice.repository.settings;

import mu.codeoffice.entity.settings.GlobalSettings;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalSettingsRepository extends JpaRepository<GlobalSettings, Long> {

}
