package mu.codeoffice.repository;

import java.util.List;

import mu.codeoffice.entity.Label;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LabelRepository extends JpaRepository<Label, Long> {

	@Query("SELECT l FROM Label l WHERE l.project.id = :project")
	public List<Label> getProjectLabels(@Param("project") Long project);
	
}
