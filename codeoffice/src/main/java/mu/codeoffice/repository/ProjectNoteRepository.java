package mu.codeoffice.repository;

import java.util.List;

import mu.codeoffice.entity.ProjectNote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectNoteRepository extends JpaRepository<ProjectNote, Long> {

	@Query("SELECT n FROM ProjectNote n WHERE n.projectObject.id = :project")
	public List<ProjectNote> getProjectNotes(@Param("project") Long project);
	
}
