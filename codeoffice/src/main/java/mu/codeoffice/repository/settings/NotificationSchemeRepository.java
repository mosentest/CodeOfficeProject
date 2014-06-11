package mu.codeoffice.repository.settings;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.NotificationScheme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationSchemeRepository extends JpaRepository<NotificationScheme, Long> {

	@Query("SELECT s FROM NotificationScheme s WHERE s.enterprise = :enterprise AND s.id = :id")
	public NotificationScheme getNotificationScheme(@Param("enterprise") Enterprise enterprise, @Param("id") Long id);

	@Query("SELECT s FROM NotificationScheme s WHERE s.enterprise = :enterprise AND s.name = :name")
	public NotificationScheme getNotificationScheme(@Param("enterprise") Enterprise enterprise, @Param("name") String name);
	
	@Query("SELECT COUNT(s) = 0 FROM NotificationScheme s WHERE s.enterprise = :enterprise AND s.name = :name AND s.id <> :id")
	public boolean isNameAvailable(@Param("enterprise") Enterprise enterprise, @Param("name") String name, @Param("id") Long id);

	@Query("SELECT s FROM NotificationScheme s WHERE s.enterprise = :enterprise")
	public List<NotificationScheme> getNotificationSchemes(@Param("enterprise") Enterprise enterprise);
	
}
