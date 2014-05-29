package mu.codeoffice.repository.settings;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.User;
import mu.codeoffice.entity.settings.GlobalPermissionSettings;
import mu.codeoffice.security.GlobalPermission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GlobalPermissionSettingsRepository extends JpaRepository<GlobalPermissionSettings, Long> {

	@Query("SELECT gp FROM GlobalPermissionSettings gp WHERE gp.enterprise = :enterprise ORDER BY gp.globalPermission")
	public List<GlobalPermissionSettings> getGlobalPermissionSettings(@Param("enterprise") Enterprise enterprise);

	@Query("SELECT gp FROM GlobalPermissionSettings gp WHERE gp.enterprise = :enterprise AND gp.globalPermission = :globalPermission")
	public GlobalPermissionSettings getGlobalPermissionSettings(
			@Param("enterprise") Enterprise enterprise, @Param("globalPermission") GlobalPermission globalPermission);

	@Query("SELECT COUNT(DISTINCT gp) > 0 FROM GlobalPermissionSettings gp JOIN gp.userGroups ug JOIN ug.users u WHERE gp.enterprise = :enterprise AND gp.globalPermission = :globalPermission AND u = :user")
	public boolean isUserInGroup(@Param("enterprise") Enterprise enterprise, @Param("globalPermission") GlobalPermission globalPermission,
			@Param("user") User user);

	@Query("SELECT COUNT(DISTINCT gp) > 0 FROM GlobalPermissionSettings gp JOIN gp.users u WHERE gp.enterprise = :enterprise AND gp.globalPermission = :globalPermission AND u = :user")
	public boolean isUserInUsers(@Param("enterprise") Enterprise enterprise, @Param("globalPermission") GlobalPermission globalPermission,
			@Param("user") User user);
	
}
