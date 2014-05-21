package mu.codeoffice.repository;

import java.util.List;

import mu.codeoffice.entity.Issue;
import mu.codeoffice.entity.Enterprise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CaseRepository extends JpaRepository<Issue, Long>, JpaSpecificationExecutor<Issue> {

	@Query("SELECT c FROM Case c WHERE c.enterprise = :enterprise AND c.id = :caseId")
	public Issue getOne(Enterprise enterprise, Long caseId);
	
	@Query("SELECT c FROM Case c WHERE c.project.id = :project")
	public List<Issue> getCases(@Param("project") Long project);

}