package mu.codeoffice.repository.settings;

import java.util.List;
import java.util.Set;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.User;
import mu.codeoffice.entity.settings.ProjectRole;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRoleRepository extends JpaRepository<ProjectRole, Long> {
	
	@Query("SELECT COUNT(r) = 0 FROM ProjectRole r WHERE r.enterprise = :enterprise AND r.name = :name AND r.id <> :id")
	public boolean isNameAvailable(@Param("enterprise") Enterprise enterprise, @Param("name") String name, @Param("id") Long id);

	@Query("SELECT r FROM ProjectRole r WHERE r.enterprise = :enterprise AND r.id = :id")
	public ProjectRole getProjectRole(@Param("enterprise") Enterprise enterprise, @Param("id") Long id);

	@Query("SELECT r FROM ProjectRole r WHERE r.enterprise = :enterprise AND r.name = :name")
	public ProjectRole getProjectRole(@Param("enterprise") Enterprise enterprise, @Param("name") String name);

	@Query("SELECT r FROM ProjectRole r WHERE r.enterprise = :enterprise")
	public List<ProjectRole> getProjectRoles(@Param("enterprise") Enterprise enterprise);

	@Query("SELECT r FROM ProjectRole r WHERE r.enterprise = :enterprise AND r.id IN :idSet")
	public List<ProjectRole> getProjectRoles(@Param("enterprise") Enterprise enterprise, @Param("idSet") Set<Long> idSet);

	@Query("SELECT u FROM ProjectRole r JOIN r.defaultMembers u WHERE r.enterprise = :enterprise AND r.id = :id")
	public Page<User> getUsers(@Param("enterprise") Enterprise enterprise, @Param("id") Long id, Pageable pageable);

	@Query("SELECT u FROM ProjectRole r JOIN r.defaultMembers u WHERE r.enterprise = :enterprise AND r.id = :id AND (u.firstName LIKE :query OR u.lastName LIKE :query OR u.email LIKE :query)")
	public Page<User> getUsers(@Param("enterprise") Enterprise enterprise, @Param("id") Long id, @Param("query") String query, Pageable pageable);
	
}
