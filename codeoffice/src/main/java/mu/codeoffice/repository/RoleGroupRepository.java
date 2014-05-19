package mu.codeoffice.repository;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.EnterpriseUser;
import mu.codeoffice.entity.ProjectRole;
import mu.codeoffice.entity.RoleGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleGroupRepository extends JpaRepository<RoleGroup, Long> {

	@Query("SELECT r FROM RoleGroup r WHERE r.enterprise = :enterprise AND r.project.code = :project")
	public List<RoleGroup> getProjectRoleGroups(@Param("enterprise") Enterprise enterprise, @Param("project") String project);

	@Query("SELECT r FROM RoleGroup rg JOIN rg.role r WHERE rg.enterprise = :enterprise AND rg.project.code = :project")
	public List<ProjectRole> getProjectRoles(@Param("enterprise") Enterprise enterprise, @Param("project") String project);

	@Query("SELECT r.role FROM RoleGroup r WHERE r.project.code = :project AND :user MEMBER OF r.users")
	public ProjectRole getProjectRole(@Param("user") EnterpriseUser user, @Param("project") String project);

	@Query("SELECT DISTINCT u FROM RoleGroup r JOIN r.users u WHERE r.project.id = :project")
	public List<EnterpriseUser> getUsers(@Param("project") Long project);

	@Query("SELECT DISTINCT u FROM RoleGroup r JOIN r.users u WHERE r.project.code = :project")
	public List<EnterpriseUser> getUsers(@Param("project") String project);
	
	@Query("SELECT r FROM RoleGroup r WHERE r.project.id = :project AND bitwise_and(r.role.value, :authority) <> 0 ORDER BY r.role.value DESC")
	public List<RoleGroup> getAuthorizedGroups(@Param("project") Long project, @Param("authority") int authority);
	
}
