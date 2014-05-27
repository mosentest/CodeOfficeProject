package mu.codeoffice.repository;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.EnterpriseUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnterpriseUserRepository extends JpaRepository<EnterpriseUser, Long>, JpaSpecificationExecutor<EnterpriseUser> {
	
	@Query("SELECT eu FROM EnterpriseUser eu WHERE eu.enterprise = :enterprise AND eu.id = :id")
	public EnterpriseUser findById(@Param("enterprise") Enterprise enterprise, @Param("id") Long id);

	@Query("SELECT eu FROM EnterpriseUser eu WHERE eu.account = :account OR eu.email = :email")
	public EnterpriseUser findByAccount(@Param("account") String account, @Param("email") String email);

}
