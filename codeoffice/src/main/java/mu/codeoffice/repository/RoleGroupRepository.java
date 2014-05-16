package mu.codeoffice.repository;

import java.util.List;

import mu.codeoffice.entity.EnterpriseUser;
import mu.codeoffice.entity.ProjectRole;
import mu.codeoffice.entity.RoleGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleGroupRepository extends JpaRepository<RoleGroup, Long> {

	@Query("SELECT r FROM RoleGroup r WHERE r.project.id = :project")
	public List<RoleGroup> getProjectRoleGroups(@Param("project") Long project);

	@Query("SELECT r.role FROM RoleGroup r WHERE r.project.code = :project AND :user MEMBER OF r.users")
	public ProjectRole getProjectRole(@Param("user") EnterpriseUser user, @Param("project") String project);

	@Query("SELECT DISTINCT u FROM RoleGroup r JOIN r.users u WHERE r.project.id = :project")
	public List<EnterpriseUser> getUsers(@Param("project") Long project);
	
	@Query("SELECT r FROM RoleGroup r WHERE r.project.id = :project AND bitwise_and(r.role.value, :authority) <> 0 ORDER BY r.role.value DESC")
	public List<RoleGroup> getAuthorizedGroups(@Param("project") Long project, @Param("authority") int authority);
	
}
