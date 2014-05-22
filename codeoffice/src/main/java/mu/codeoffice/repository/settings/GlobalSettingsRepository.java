package mu.codeoffice.repository.settings;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.GlobalSettings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GlobalSettingsRepository extends JpaRepository<GlobalSettings, Long> {

	@Query("SELECT g FROM GlobalSettings g WHERE g.enterprise = :enterprise")
	public GlobalSettings getEnterpriseGlobalSettings(@Param("enterprise") Enterprise enterprise);
	
}
