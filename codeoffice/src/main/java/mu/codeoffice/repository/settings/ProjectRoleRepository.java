package mu.codeoffice.repository.settings;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.ProjectRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRoleRepository extends JpaRepository<ProjectRole, Long> {

	@Query("SELECT r FROM ProjectRole r WHERE r.enterprise = :enterprise AND r.name = :name")
	public ProjectRole getProjectRole(@Param("enterprise") Enterprise enterprise, @Param("name") String name);

	@Query("SELECT r FROM ProjectRole r WHERE r.enterprise = :enterprise")
	public List<ProjectRole> getProjectRoles(@Param("enterprise") Enterprise enterprise);

	@Query("SELECT r FROM ProjectRole r WHERE r.enterprise = :enterprise AND r.id IN :idList")
	public List<ProjectRole> getProjectRoles(@Param("enterprise") Enterprise enterprise, @Param("idList") List<Long> idList);
	
}
