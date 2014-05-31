package mu.codeoffice.repository.settings;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.IssuePriority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IssuePriorityRepository extends JpaRepository<IssuePriority, Long> {

	@Query("SELECT p FROM IssuePriority p WHERE p.enterprise = :enterprise AND p.name = :name")
	public IssuePriority getIssuePriority(@Param("enterprise") Enterprise enterprise, @Param("name") String name);

	@Query("SELECT COUNT(p) = 0 FROM IssuePriority p WHERE p.enterprise = :enterprise AND p.name = :name AND p.id <> :id")
	public boolean isNameAvailable(@Param("enterprise") Enterprise enterprise, @Param("name") String name, @Param("id") Long id);

	@Query("SELECT p FROM IssuePriority p WHERE p.enterprise = :enterprise ORDER By p.priorityOrder")
	public List<IssuePriority> getIssuePriorities(@Param("enterprise") Enterprise enterprise);
	
}
