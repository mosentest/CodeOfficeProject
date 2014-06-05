package mu.codeoffice.repository.settings;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.User;
import mu.codeoffice.entity.settings.ProjectPermissionSettings;
import mu.codeoffice.security.ProjectPermission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectPermissionSettingsRepository extends JpaRepository<ProjectPermissionSettings, Long> {

	@Query("SELECT COUNT(DISTINCT s) > 0 FROM ProjectPermissionSettings s JOIN s.userGroups g JOIN s.users u WHERE s.enterprise = :enterprise AND s.projectPermissionScheme.name = :scheme AND s.projectPermission = :projectPermission AND u = :user")
	public boolean isUserInGroup(@Param("enterprise") Enterprise enterprise, @Param("scheme") String scheme, @Param("projectPermission") ProjectPermission globalPermission,
			@Param("user") User user);

	@Query("SELECT COUNT(DISTINCT s) > 0 FROM ProjectPermissionSettings s JOIN s.users u WHERE s.enterprise = :enterprise AND s.projectPermissionScheme.name = :scheme AND s.projectPermission = :projectPermission AND u = :user")
	public boolean isUserInUsers(@Param("enterprise") Enterprise enterprise, @Param("scheme") String scheme, @Param("projectPermission") ProjectPermission projectPermission,
			@Param("user") User user);

	@Query("SELECT COUNT(DISTINCT s) > 0 FROM ProjectPermissionSettings s JOIN s.projectRoles r JOIN r.defaultMembers u WHERE s.projectPermissionScheme.name = :scheme AND s.enterprise = :enterprise AND s.projectPermission = :projectPermission AND u = :user")
	public boolean isUserInRole(@Param("enterprise") Enterprise enterprise, @Param("scheme") String scheme, @Param("projectPermission") ProjectPermission projectPermission,
			@Param("user") User user);
	
	@Query("SELECT s FROM ProjectPermissionSettings s WHERE s.enterprise = :enterprise AND s.projectPermissionScheme.name = :scheme AND s.projectPermission = :projectPermission")
	public ProjectPermissionSettings getProjectPermissionSettings(@Param("enterprise") Enterprise enterprise, @Param("scheme") String scheme, @Param("projectPermission") ProjectPermission projectPermission);
	
}
