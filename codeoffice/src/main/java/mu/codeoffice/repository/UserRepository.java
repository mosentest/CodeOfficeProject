package mu.codeoffice.repository;

import mu.codeoffice.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>  {

	@Query("SELECT u FROM User u WHERE u.password = :password AND (u.account = :account OR u.email = :email)")
	public User login(@Param("account") String account, @Param("email") String email, @Param("password") String password);

	@Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email")
	public boolean isEmailAvailable(@Param("email") String email);

	@Query("SELECT COUNT(u) > 0 FROM User u WHERE u.account = :account")
	public boolean isAccountAvailable(@Param("account") String account);
	
}
