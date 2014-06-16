package mu.codeoffice.repository.settings;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.ProjectPermissionScheme;
import mu.codeoffice.entity.settings.ProjectRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectPermissionSchemeRepository extends JpaRepository<ProjectPermissionScheme, Long> {

	@Query("SELECT COUNT(s) = 0 FROM ProjectPermissionScheme s WHERE s.enterprise = :enterprise AND LOWER(s.name) = :schemeName AND s.id <> :id")
	public boolean isNameAvailable(@Param("enterprise") Enterprise enterprise, @Param("schemeName") String schemeName, @Param("id") Long id);

	@Query("SELECT s FROM ProjectPermissionScheme s WHERE s.enterprise = :enterprise")
	public List<ProjectPermissionScheme> getProjectPermissionSchemes(@Param("enterprise") Enterprise enterprise);

	@Query("SELECT s FROM ProjectPermissionScheme s WHERE s.enterprise = :enterprise AND s.id = :id")
	public ProjectPermissionScheme getProjectPermissionScheme(@Param("enterprise") Enterprise enterprise, @Param("id") Long id);

	@Query("SELECT s FROM ProjectPermissionScheme s WHERE s.enterprise = :enterprise AND s.name = :schemeName")
	public ProjectPermissionScheme getProjectPermissionScheme(@Param("enterprise") Enterprise enterprise, @Param("schemeName") String schemeName);
	
	@Query("SELECT COUNT(s) > 0 FROM ProjectPermissionScheme s JOIN s.projectPermissionSettings se JOIN se.projectRoles r WHERE s.enterprise = :enterprise AND r = :projectRole")
	public boolean isProjectRoleInUse(@Param("enterprise") Enterprise enterprise, @Param("projectRole") ProjectRole projectRole);
	
}
