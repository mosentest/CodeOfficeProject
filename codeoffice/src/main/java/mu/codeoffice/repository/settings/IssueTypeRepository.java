package mu.codeoffice.repository.settings;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.IssueType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IssueTypeRepository extends JpaRepository<IssueType, Long> {

	@Query("SELECT t FROM IssueType t WHERE t.enterprise = :enterprise AND t.name = :name")
	public IssueType getIssueType(@Param("enterprise") Enterprise enterprise, @Param("name") String name);

	@Query("SELECT COUNT(t) = 0 FROM IssueType t WHERE t.enterprise = :enterprise AND t.name = :name AND t.id <> :id")
	public boolean isNameAvailable(@Param("enterprise") Enterprise enterprise, @Param("name") String name, @Param("id") Long id);

	@Query("SELECT t FROM IssueType t WHERE t.enterprise = :enterprise AND t.isStandard = FALSE")
	public List<IssueType> getSubTaskTypes(@Param("enterprise") Enterprise enterprise);

	@Query("SELECT t FROM IssueType t WHERE t.enterprise = :enterprise AND t.isStandard = TRUE")
	public List<IssueType> getIssueTypes(@Param("enterprise") Enterprise enterprise);
	
}
