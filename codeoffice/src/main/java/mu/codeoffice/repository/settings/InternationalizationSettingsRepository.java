package mu.codeoffice.repository.settings;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.InternationalizationSettings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InternationalizationSettingsRepository extends JpaRepository<InternationalizationSettings, Long> {

	@Query("SELECT i FROM InternationalizationSettings i WHERE i.enterprise = :enterprise")
	public InternationalizationSettings getEnterpriseInternationalizationSettings(@Param("enterprise") Enterprise enterprise);
	
}
