package mu.codeoffice.repository;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	
	@Query("SELECT u FROM User u WHERE u.enterprise = :enterprise AND u.id = :id")
	public User findById(@Param("enterprise") Enterprise enterprise, @Param("id") Long id);

	@Query("SELECT u FROM User u WHERE u.account = :account OR u.email = :email")
	public User findByAccount(@Param("account") String account, @Param("email") String email);

	@Query("SELECT u FROM User u WHERE u.enterprise = :enterprise AND u.id IN :idList")
	public List<User> getUsers(@Param("enterprise") Enterprise enterprise, @Param("idList") List<Long> idList);

}
