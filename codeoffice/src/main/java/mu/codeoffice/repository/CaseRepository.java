package mu.codeoffice.repository;

import mu.codeoffice.entity.Case;
import mu.codeoffice.entity.Enterprise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CaseRepository extends JpaRepository<Case, Long> {

	@Query("SELECT c FROM Case c WHERE c.enterprise = :enterprise AND c.id = :caseId")
	public Case getOne(Enterprise enterprise, Long caseId);

}