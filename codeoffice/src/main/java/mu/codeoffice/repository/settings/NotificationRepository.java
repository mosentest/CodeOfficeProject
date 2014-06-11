package mu.codeoffice.repository.settings;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.Notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	@Query("SELECT n FROM Notification n WHERE n.enterprise = :enterprise AND n.id = :id")
	public Notification getNotification(@Param("enterprise") Enterprise enterprise, @Param("id") Long id);

	@Query("SELECT n FROM Notification n WHERE n.enterprise = :enterprise AND n.notificationScheme.id = :scheme")
	public List<Notification> getNotifications(@Param("enterprise") Enterprise enterprise, @Param("scheme") Long scheme);
	
}
