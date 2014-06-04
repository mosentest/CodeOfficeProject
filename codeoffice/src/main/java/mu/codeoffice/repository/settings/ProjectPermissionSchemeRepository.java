package mu.codeoffice.repository.settings;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.ProjectPermissionScheme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectPermissionSchemeRepository extends JpaRepository<ProjectPermissionScheme, Long> {

	@Query("SELECT COUNT(pps) = 0 FROM ProjectPermissionScheme pps WHERE pps.enterprise = :enterprise AND LOWER(pps.name) = :schemeName AND pps.id <> :id")
	public boolean isNameAvailable(@Param("enterprise") Enterprise enterprise, @Param("schemeName") String schemeName, @Param("id") Long id);

	@Query("SELECT pps FROM ProjectPermissionScheme pps WHERE pps.enterprise = :enterprise")
	public List<ProjectPermissionScheme> getProjectPermissionSchemes(@Param("enterprise") Enterprise enterprise);

	@Query("SELECT pps FROM ProjectPermissionScheme pps WHERE pps.enterprise = :enterprise AND pps.name = :schemeName")
	public ProjectPermissionScheme getProjectPermissionScheme(@Param("enterprise") Enterprise enterprise, @Param("schemeName") String schemeName);
	
}
