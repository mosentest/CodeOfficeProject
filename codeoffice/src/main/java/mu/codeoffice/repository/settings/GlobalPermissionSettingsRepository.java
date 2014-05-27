package mu.codeoffice.repository.settings;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.GlobalPermissionSettings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GlobalPermissionSettingsRepository extends JpaRepository<GlobalPermissionSettings, Long> {

	@Query("SELECT gp FROM GlobalPermissionSettings gp WHERE gp.enterprise = :enterprise ORDER BY gp.globalPermission")
	public List<GlobalPermissionSettings> getGlobalPermissionSettings(@Param("enterprise") Enterprise enterprise);
	
}
