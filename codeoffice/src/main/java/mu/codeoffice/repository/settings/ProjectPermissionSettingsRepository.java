package mu.codeoffice.repository.settings;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.ProjectPermissionSettings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectPermissionSettingsRepository extends JpaRepository<ProjectPermissionSettings, Long> {

	@Query("SELECT pp FROM ProjectPermissionSettings pp WHERE pp.enterprise = :enterprise ORDER BY pp.projectPermission")
	public List<ProjectPermissionSettings> getProjectPermissionSettings(@Param("enterprise") Enterprise enterprise);
	
}
