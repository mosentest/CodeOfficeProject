package mu.codeoffice.repository;

import java.util.List;

import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.RoleGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleGroupRepository extends JpaRepository<RoleGroup, Long> {

	@Query("SELECT r FROM RoleGroup r WHERE r.project = :project")
	public List<RoleGroup> getProjectRoleGroups(@Param("project") Project project);
	
}
