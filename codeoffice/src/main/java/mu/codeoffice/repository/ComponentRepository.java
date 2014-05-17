package mu.codeoffice.repository;

import java.util.List;

import mu.codeoffice.entity.Component;
import mu.codeoffice.entity.Enterprise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ComponentRepository extends JpaRepository<Component, Long> {

	@Query("SELECT c FROM Component c WHERE c.project.id = :project")
	public List<Component> getProjectComponents(@Param("project") Long project);
	
	@Query("SELECT c FROM Component c WHERE c.enterprise = :enterprise AND c.project.code = :project")
	public List<Component> getProjectComponents(@Param("enterprise") Enterprise enterprise, @Param("project") String project);
	
	@Query("SELECT c FROM Component c WHERE c.enterprise = :enterprise AND c.project.code = :project AND c.code = :component")
	public Component getProjectComponent(@Param("project") String project, @Param("component") String component, @Param("enterprise") Enterprise enterprise);
	
	@Query("SELECT c FROM Component c WHERE c.project.id = :project AND c.code = :component")
	public Component getProjectComponent(@Param("project") Long project, @Param("component") String component);
	
	@Query("SELECT c FROM Component c WHERE c.enterprise = :enterprise AND c.project.code = :project AND c.code <> :component AND c.code IN (:components)")
	public List<Component> getComponents(@Param("enterprise") Enterprise enterprise, @Param("project") String project, 
			@Param("component") String component, @Param("components") String[] components);
	
	@Query("SELECT COUNT(c) = 0 FROM Component c WHERE c.enterprise = :enterprise AND c.project.code = :project AND c.code = :code AND c.id <> :id")
	public boolean isCodeAvailable(@Param("project") String project, @Param("code") String code, @Param("enterprise") Enterprise enterprise, @Param("id") Long id);
	
	@Query("SELECT COUNT(c) = 1 FROM Component c WHERE c.enterprise = :enterprise AND c.project.code = :project AND c.id = :id AND c.code = :code")
	public boolean isSameObject(@Param("enterprise") Enterprise enterprise, @Param("project") String project, @Param("code") String code, @Param("id") Long id);
	
}
