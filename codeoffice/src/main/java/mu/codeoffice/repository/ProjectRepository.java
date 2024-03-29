package mu.codeoffice.repository;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.User;
import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.ProjectCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

	@Query("SELECT DISTINCT p FROM Project p")
	public List<Project> getCurrentProjects(User user);

	@Query("SELECT p FROM Project p WHERE p.category = :category")
	public List<Project> getProjectByCategory(@Param("category") ProjectCategory category);
	
	@Query("SELECT p FROM Project p WHERE p.enterprise = :enterprise AND p.category IS NULL")
	public List<Project> getNoneCategorizedProjects(@Param("enterprise") Enterprise enterprise);

	@Query("SELECT p FROM Project p WHERE p.key = :key AND p.enterprise = :enterprise")
	public Project getProject(@Param("key") String key, @Param("enterprise") Enterprise enterprise);
	
}