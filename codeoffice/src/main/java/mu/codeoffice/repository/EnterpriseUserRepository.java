package mu.codeoffice.repository;

import mu.codeoffice.entity.EnterpriseUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnterpriseUserRepository extends JpaRepository<EnterpriseUser, Long> {

	@Query("SELECT eu FROM EnterpriseUser eu WHERE eu.password = :password AND (eu.account = :account OR eu.email = :email)")
	public EnterpriseUser login(@Param("account") String account, @Param("email") String email, @Param("password") String password);

	@Query("SELECT eu FROM EnterpriseUser eu WHERE eu.account = :account OR eu.email = :email")
	public EnterpriseUser findByAccount(@Param("account") String account, @Param("email") String email);

	@Query("SELECT COUNT(eu) > 0 FROM EnterpriseUser eu WHERE eu.email = :email")
	public boolean isEmailAvailable(@Param("email") String email);

	@Query("SELECT COUNT(eu) > 0 FROM EnterpriseUser eu WHERE eu.account = :account")
	public boolean isAccountAvailable(@Param("account") String account);

}
