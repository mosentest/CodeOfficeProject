package mu.codeoffice.repository.settings;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.GeneralProjectSettings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GeneralProjectSettingsRepository extends JpaRepository<GeneralProjectSettings, Long> {

	@Query("SELECT g FROM GeneralProjectSettings g WHERE g.enterprise = :enterprise")
	public GeneralProjectSettings getEnterpriseGeneralProjectSettings(@Param("enterprise") Enterprise enterprise);
	
}
