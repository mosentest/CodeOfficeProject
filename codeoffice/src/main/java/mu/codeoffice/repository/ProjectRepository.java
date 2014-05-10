package mu.codeoffice.repository;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.EnterpriseUser;
import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.ProjectCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Long> {

	@Query("SELECT DISTINCT p FROM Project p")
	public List<Project> getCurrentProjects(EnterpriseUser user);
	
	@Query("SELECT p FROM Project p WHERE p.completed = FALSE AND p.removed = FALSE AND p.enterprise = :enterprise")
	public List<Project> getActiveProjects(@Param("enterprise") Enterprise enterprise);

	@Query("SELECT p FROM Project p WHERE p.category = :category")
	public List<Project> getProjectByCategory(@Param("category") ProjectCategory category);
	
}