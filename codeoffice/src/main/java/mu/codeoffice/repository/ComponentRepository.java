package mu.codeoffice.repository;

import java.util.List;

import mu.codeoffice.entity.Component;
import mu.codeoffice.entity.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ComponentRepository extends JpaRepository<Component, Long> {

	@Query("SELECT c FROM Component c WHERE c.project = :project")
	public List<Component> getProjectComponents(@Param("project") Project project);
	
}
