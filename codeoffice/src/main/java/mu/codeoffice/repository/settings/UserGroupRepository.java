package mu.codeoffice.repository.settings;

import java.util.List;
import java.util.Set;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.User;
import mu.codeoffice.entity.UserGroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long>, JpaSpecificationExecutor<UserGroup> {

	@Query("SELECT COUNT(g) = 0 FROM UserGroup g WHERE g.enterprise = :enterprise AND g.name = :name")
	public boolean isNameAvailable(@Param("enterprise") Enterprise enterprise, @Param("name") String name);
	
	@Query("SELECT g FROM UserGroup g WHERE g.enterprise = :enterprise")
	public List<UserGroup> getUserGroups(@Param("enterprise") Enterprise enterprise);

	@Query("SELECT g FROM UserGroup g WHERE g.enterprise = :enterprise AND g.id IN :idSet")
	public List<UserGroup> getUserGroups(@Param("enterprise") Enterprise enterprise, @Param("idSet") Set<Long> idSet);
	
	@Query("SELECT g FROM UserGroup g WHERE g.enterprise = :enterprise")
	public Page<UserGroup> getUserGroups(@Param("enterprise") Enterprise enterprise, Pageable pageable);

	@Query("SELECT g FROM UserGroup g WHERE g.enterprise = :enterprise AND g.name LIKE :name")
	public Page<UserGroup> getUserGroups(@Param("enterprise") Enterprise enterprise, @Param("name") String name, Pageable pageable);

	@Query("SELECT g FROM UserGroup g WHERE g.enterprise = :enterprise AND g.id = :id")
	public UserGroup getUserGroup(@Param("enterprise") Enterprise enterprise, @Param("id") Long id);

	@Query("SELECT g FROM UserGroup g WHERE g.enterprise = :enterprise AND g.name = :userGroupName")
	public UserGroup getUserGroup(@Param("enterprise") Enterprise enterprise, @Param("userGroupName") String userGroupName);

	@Query("SELECT g FROM UserGroup g JOIN g.users u WHERE g.enterprise = :enterprise AND u = :user")
	public List<UserGroup> getUserGroups(@Param("enterprise") Enterprise enterprise, @Param("user") User user);
	
}
