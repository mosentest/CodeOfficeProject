package mu.codeoffice.repository;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.ProjectRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRoleRepository extends JpaRepository<ProjectRole, Long> {
	
	@Query("SELECT r FROM ProjectRole r WHERE r.enterprise = :enterprise")
	public List<ProjectRole> getRoles(@Param("enterprise") Enterprise enterprise);
	
}
