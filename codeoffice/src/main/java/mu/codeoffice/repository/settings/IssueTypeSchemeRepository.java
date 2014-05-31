package mu.codeoffice.repository.settings;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.IssueTypeScheme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IssueTypeSchemeRepository extends JpaRepository<IssueTypeScheme, Long> {

	@Query("SELECT s FROM IssueTypeScheme s WHERE s.enterprise = :enterprise AND s.name = :name")
	public IssueTypeScheme getIssueTypeScheme(@Param("enterprise") Enterprise enterprise, @Param("name") String name);

	@Query("SELECT COUNT(s) = 0 FROM IssueTypeScheme s WHERE s.enterprise = :enterprise AND s.name = :name AND s.id <> :id")
	public boolean isNameAvailable(@Param("enterprise") Enterprise enterprise, @Param("name") String name, @Param("id") Long id);

	@Query("SELECT s FROM IssueTypeScheme s WHERE s.enterprise = :enterprise")
	public List<IssueTypeScheme> getIssueTypeSchemes(@Param("enterprise") Enterprise enterprise);
	
}
