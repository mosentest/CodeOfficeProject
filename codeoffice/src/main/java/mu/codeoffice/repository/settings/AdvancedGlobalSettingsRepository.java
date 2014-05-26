package mu.codeoffice.repository.settings;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.AdvancedGlobalSettings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdvancedGlobalSettingsRepository extends JpaRepository<AdvancedGlobalSettings, Long> {

	@Query("SELECT a FROM AdvancedGlobalSettings a WHERE a.enterprise = :enterprise")
	public AdvancedGlobalSettings getEnterpriseAdvancedGlobalSettings(@Param("enterprise") Enterprise enterprise);
	
}
