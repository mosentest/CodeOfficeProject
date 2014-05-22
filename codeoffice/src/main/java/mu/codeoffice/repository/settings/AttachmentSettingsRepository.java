package mu.codeoffice.repository.settings;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.AttachmentSettings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AttachmentSettingsRepository extends JpaRepository<AttachmentSettings, Long> {

	@Query("SELECT a FROM AttachmentSettings a WHERE a.enterprise = :enterprise")
	public AttachmentSettings getEnterpriseAttachmentSettings(@Param("enterprise") Enterprise enterprise);
	
}
