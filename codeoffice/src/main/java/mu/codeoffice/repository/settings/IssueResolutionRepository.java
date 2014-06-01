package mu.codeoffice.repository.settings;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.IssueResolution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IssueResolutionRepository extends JpaRepository<IssueResolution, Long> {

	@Query("SELECT r FROM IssueResolution r WHERE r.enterprise = :enterprise AND r.name = :name")
	public IssueResolution getIssueResolution(@Param("enterprise") Enterprise enterprise, @Param("name") String name);

	@Query("SELECT COUNT(r) = 0 FROM IssueResolution r WHERE r.enterprise = :enterprise AND LOWER(r.name) = :name AND r.id <> :id")
	public boolean isNameAvailable(@Param("enterprise") Enterprise enterprise, @Param("name") String name, @Param("id") Long id);

	@Query("SELECT r FROM IssueResolution r WHERE r.enterprise = :enterprise ORDER By r.order")
	public List<IssueResolution> getIssueResolutions(@Param("enterprise") Enterprise enterprise);
	
	@Query("SELECT COUNT(r) FROM IssueResolution r WHERE r.enterprise = :enterprise")
	public int getOrder(@Param("enterprise") Enterprise enterprise);
	
}
