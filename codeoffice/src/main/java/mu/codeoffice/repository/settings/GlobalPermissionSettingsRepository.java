package mu.codeoffice.repository.settings;

import mu.codeoffice.entity.settings.GlobalPermissionSettings;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalPermissionSettingsRepository extends JpaRepository<GlobalPermissionSettings, Long> {

}
