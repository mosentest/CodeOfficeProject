package mu.codeoffice.repository.settings;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.Announcement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

	@Query("SELECT a FROM Announcement a WHERE a.enterprise = :enterprise")
	public Announcement getAnnouncement(@Param("enterprise") Enterprise enterprise);
	
}
