package mu.codeoffice.repository.settings;

import mu.codeoffice.entity.settings.GeneralProjectSettings;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneralProjectSettingsRepository extends JpaRepository<GeneralProjectSettings, Long> {

}
