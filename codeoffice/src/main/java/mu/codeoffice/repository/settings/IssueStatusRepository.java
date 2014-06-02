package mu.codeoffice.repository.settings;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.IssueStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IssueStatusRepository extends JpaRepository<IssueStatus, Long> {

	@Query("SELECT s FROM IssueStatus s WHERE s.enterprise = :enterprise AND s.id = :id")
	public IssueStatus getIssueStatus(@Param("enterprise") Enterprise enterprise, @Param("id") Long id);

	@Query("SELECT s FROM IssueStatus s WHERE s.enterprise = :enterprise AND s.name = :name")
	public IssueStatus getIssueStatus(@Param("enterprise") Enterprise enterprise, @Param("name") String name);

	@Modifying
	@Query("UPDATE IssueStatus s SET s.order = s.order - 1 WHERE s.enterprise = :enterprise AND s.order > :order")
	public int resetOrder(@Param("enterprise") Enterprise enterprise, @Param("order") int order);

	@Query("SELECT COUNT(s) = 0 FROM IssueStatus s WHERE s.enterprise = :enterprise AND LOWER(s.name) = :name AND s.id <> :id")
	public boolean isNameAvailable(@Param("enterprise") Enterprise enterprise, @Param("name") String name, @Param("id") Long id);

	@Query("SELECT s FROM IssueStatus s WHERE s.enterprise = :enterprise ORDER BY s.order")
	public List<IssueStatus> getIssueStatus(@Param("enterprise") Enterprise enterprise);
	
	@Query("SELECT COUNT(s) FROM IssueStatus s WHERE s.enterprise = :enterprise")
	public int getOrder(@Param("enterprise") Enterprise enterprise);
	
}
