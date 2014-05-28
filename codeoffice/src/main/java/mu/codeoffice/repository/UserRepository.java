package mu.codeoffice.repository;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	
	@Query("SELECT eu FROM User eu WHERE eu.enterprise = :enterprise AND eu.id = :id")
	public User findById(@Param("enterprise") Enterprise enterprise, @Param("id") Long id);

	@Query("SELECT eu FROM User eu WHERE eu.account = :account OR eu.email = :email")
	public User findByAccount(@Param("account") String account, @Param("email") String email);
	
	@Query("SELECT eu FROM User eu WHERE eu.enterprise = :enterprise AND (eu.email LIKE :searchString or eu.firstName LIKE :searchString or eu.lastName LIKE :searchString) AND bitwise_and(eu.globalPermissionValue, :authority) = 0")
	public Page<User> findNonGlobalAuthorizedUsers(@Param("enterprise") Enterprise enterprise, @Param("searchString") String searchString,
			@Param("authority") int authority, Pageable pageable);

}
