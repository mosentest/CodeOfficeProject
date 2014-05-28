package mu.codeoffice.repository.settings;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.User;
import mu.codeoffice.entity.UserGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long>, JpaSpecificationExecutor<UserGroup> {

	@Query("SELECT COUNT(ug) = 0 FROM UserGroup ug WHERE ug.enterprise = :enterprise AND ug.name = :name")
	public boolean isNameAvailable(@Param("enterprise") Enterprise enterprise, @Param("name") String name);
	
	@Query("SELECT ug FROM UserGroup ug WHERE ug.enterprise = :enterprise")
	public List<UserGroup> getUserGroups(@Param("enterprise") Enterprise enterprise);

	@Query("SELECT ug FROM UserGroup ug WHERE ug.enterprise = :enterprise AND ug.name = :userGroupName")
	public UserGroup getUserGroup(@Param("enterprise") Enterprise enterprise, @Param("userGroupName") String userGroupName);

	@Query("SELECT ug FROM UserGroup ug JOIN ug.users u WHERE ug.enterprise = :enterprise AND u = :user")
	public List<UserGroup> getUserGroups(@Param("enterprise") Enterprise enterprise, @Param("user") User user);
	
}
