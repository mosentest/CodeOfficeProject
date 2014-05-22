package mu.codeoffice.repository.settings;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.GlobalAdvancedSettings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GlobalAdvancedSettingsRepository extends JpaRepository<GlobalAdvancedSettings, Long> {

	@Query("SELECT g FROM GlobalAdvancedSettings g WHERE g.enterprise = :enterprise")
	public GlobalAdvancedSettings getEnterpriseGlobalAdvancedSettings(@Param("enterprise") Enterprise enterprise);
	
}
