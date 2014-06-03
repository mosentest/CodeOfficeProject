package mu.codeoffice.repository.settings;

import mu.codeoffice.entity.settings.WorkFlowTransition;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkFlowTransitionRepository extends JpaRepository<WorkFlowTransition, Long> {

}
