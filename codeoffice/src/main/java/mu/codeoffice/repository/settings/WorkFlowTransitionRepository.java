package mu.codeoffice.repository.settings;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.IssueStatus;
import mu.codeoffice.entity.settings.WorkFlowTransition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkFlowTransitionRepository extends JpaRepository<WorkFlowTransition, Long> {

	@Query("SELECT t FROM WorkFlowTransition t WHERE t.enterprise = :enterprise")
	public List<WorkFlowTransition> getWorkFlowTransitions(@Param("enterprise") Enterprise enterprise);

	@Query("SELECT t FROM WorkFlowTransition t WHERE t.enterprise = :enterprise AND t.id = :id")
	public WorkFlowTransition getWorkFlowTransition(@Param("enterprise") Enterprise enterprise, @Param("id") Long id);

	@Query("SELECT COUNT(t) = 0 FROM WorkFlowTransition t WHERE t.enterprise = :enterprise AND t.workFlow.id = :workFlow AND t.transition = :transition AND t.from = :from AND t.to = :to")
	public boolean isInUse(@Param("enterprise") Enterprise enterprise, @Param("workFlow") Long workFlow, @Param("transition") String transition, 
			@Param("from") IssueStatus from, @Param("to") IssueStatus to);
}
