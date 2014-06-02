package mu.codeoffice.repository.settings;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.IssueLink;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IssueLinkRepository  extends JpaRepository<IssueLink, Long> {

	@Query("SELECT l FROM IssueLink l WHERE l.enterprise = :enterprise AND l.id = :id")
	public IssueLink getIssueLink(@Param("enterprise") Enterprise enterprise, @Param("id") Long id);

	@Query("SELECT l FROM IssueLink l WHERE l.enterprise = :enterprise AND l.name = :name")
	public IssueLink getIssueLink(@Param("enterprise") Enterprise enterprise, @Param("name") String name);
	
	@Query("SELECT COUNT(l) = 0 FROM IssueLink l WHERE l.enterprise = :enterprise AND l.name = :name AND l.id <> :id")
	public boolean isNameAvailable(@Param("enterprise") Enterprise enterprise, @Param("name") String name, @Param("id") Long id);

	@Query("SELECT l FROM IssueLink l WHERE l.enterprise = :enterprise")
	public List<IssueLink> getIssueLinks(@Param("enterprise") Enterprise enterprise);
	
}
