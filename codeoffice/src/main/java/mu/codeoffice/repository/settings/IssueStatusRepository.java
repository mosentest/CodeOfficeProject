package mu.codeoffice.repository.settings;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.IssueStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IssueStatusRepository extends JpaRepository<IssueStatus, Long> {

	@Query("SELECT t FROM IssueStatus t WHERE t.enterprise = :enterprise AND t.name = :name")
	public IssueStatus getIssueStatus(@Param("enterprise") Enterprise enterprise, @Param("name") String name);

	@Query("SELECT COUNT(t) = 0 FROM IssueStatus t WHERE t.enterprise = :enterprise AND t.name = :name AND t.id <> :id")
	public boolean isNameAvailable(@Param("enterprise") Enterprise enterprise, @Param("name") String name, @Param("id") Long id);

	@Query("SELECT t FROM IssueStatus t WHERE t.enterprise = :enterprise ORDER BY t.statusOrder")
	public List<IssueStatus> getIssueStatus(@Param("enterprise") Enterprise enterprise);
	
}
