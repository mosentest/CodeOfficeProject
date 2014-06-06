package mu.codeoffice.repository.settings;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.ProjectTemplate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectTemplateRepository extends JpaRepository<ProjectTemplate, Long> {
	
	@Query("SELECT t FROM ProjectTemplate t WHERE t.enterprise = :enterprise")
	public List<ProjectTemplate> getProjectTemplates(@Param("enterprise") Enterprise enterprise);

	@Query("SELECT t FROM ProjectTemplate t WHERE t.enterprise = :enterprise AND t.id = :id")
	public ProjectTemplate getProjectTemplate(@Param("enterprise") Enterprise enterprise, @Param("id") Long id);

	@Query("SELECT t FROM ProjectTemplate t WHERE t.enterprise = :enterprise AND t.name = :name")
	public ProjectTemplate getProjectTemplate(@Param("enterprise") Enterprise enterprise, @Param("name") String name);

	@Query("SELECT COUNT(t) = 0 FROM ProjectTemplate t WHERE t.enterprise = :enterprise AND LOWER(t.name) = :name AND t.id <> :id")
	public boolean isNameAvailable(@Param("enterprise") Enterprise enterprise, @Param("name") String name, @Param("id") Long id);

}
