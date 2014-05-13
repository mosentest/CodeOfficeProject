package mu.codeoffice.repository;

import java.util.List;

import mu.codeoffice.entity.EnterpriseUser;
import mu.codeoffice.entity.RoleGroup;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleGroupRepository extends JpaRepository<RoleGroup, Long> {

	@Query("SELECT r FROM RoleGroup r WHERE r.project.id = :project")
	public List<RoleGroup> getProjectRoleGroups(@Param("project") Long project);

	@Query("SELECT r.role FROM RoleGroup r WHERE r.project.id = :project AND :user MEMBER OF r.users")
	@Cacheable(value = "projectRole", key = "#project + '_' + #user.id")
	public Role getRole(@Param("project") Long project, @Param("user") EnterpriseUser user);
	
}
