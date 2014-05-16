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
	
	@Query("SELECT c FROM Component c WHERE c.enterprise = :enterprise AND c.project.code = :project AND c.code = :component")
	public Component getProjectComponent(@Param("project") String project, @Param("component") String component, @Param("enterprise") Enterprise enterprise);
	
	@Query("SELECT c FROM Component c WHERE c.project.id = :project AND c.code = :component")
	public Component getProjectComponent(@Param("project") Long project, @Param("component") String component);
	
}
