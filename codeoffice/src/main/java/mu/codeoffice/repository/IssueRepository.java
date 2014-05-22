package mu.codeoffice.repository;

import java.util.List;

import mu.codeoffice.entity.Issue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IssueRepository extends JpaRepository<Issue, Long>, JpaSpecificationExecutor<Issue> {
	
	@Query("SELECT i FROM Issue i WHERE i.project.id = :project")
	public List<Issue> getIssues(@Param("project") Long project);

}