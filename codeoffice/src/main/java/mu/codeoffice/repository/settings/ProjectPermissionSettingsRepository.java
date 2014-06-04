package mu.codeoffice.repository.settings;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.User;
import mu.codeoffice.entity.settings.ProjectPermissionSettings;
import mu.codeoffice.security.ProjectPermission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectPermissionSettingsRepository extends JpaRepository<ProjectPermissionSettings, Long> {

	@Query("SELECT COUNT(DISTINCT pp) > 0 FROM ProjectPermissionSettings pp JOIN pp.userGroups ug JOIN pp.users u WHERE pp.enterprise = :enterprise AND s.projectPermissionScheme.name = :scheme AND pp.projectPermission = :projectPermission AND u = :user")
	public boolean isUserInGroup(@Param("enterprise") Enterprise enterprise, @Param("scheme") String scheme, @Param("projectPermission") ProjectPermission globalPermission,
			@Param("user") User user);

	@Query("SELECT COUNT(DISTINCT pp) > 0 FROM ProjectPermissionSettings pp JOIN pp.users u WHERE pp.enterprise = :enterprise AND s.projectPermissionScheme.name = :scheme AND pp.projectPermission = :projectPermission AND u = :user")
	public boolean isUserInUsers(@Param("enterprise") Enterprise enterprise, @Param("scheme") String scheme, @Param("projectPermission") ProjectPermission projectPermission,
			@Param("user") User user);

	@Query("SELECT COUNT(DISTINCT pp) > 0 FROM ProjectPermissionSettings pp JOIN pp.projectRoles pr JOIN pr.defaultMembers u WHERE s.projectPermissionScheme.name = :scheme AND pp.enterprise = :enterprise AND pp.pp.projectPermission = :projectPermission AND u = :user")
	public boolean isUserInRole(@Param("enterprise") Enterprise enterprise, @Param("scheme") String scheme, @Param("projectPermission") ProjectPermission projectPermission,
			@Param("user") User user);
	
	@Query("SELECT s FROM ProjectPermissionSettings s WHERE s.enterprise = :enterprise AND s.projectPermissionScheme.name = :scheme AND s.projectPermission = :projectPermission")
	public ProjectPermissionSettings getProjectPermissionSettings(@Param("enterprise") Enterprise enterprise, @Param("scheme") String scheme, @Param("projectPermission") ProjectPermission projectPermission);
	
}
