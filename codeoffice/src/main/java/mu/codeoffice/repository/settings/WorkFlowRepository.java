package mu.codeoffice.repository.settings;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.WorkFlow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkFlowRepository extends JpaRepository<WorkFlow, Long> {
	
	@Query("SELECT wf FROM WorkFlow wf WHERE wf.enterprise = :enterprise")
	public List<WorkFlow> getWorkFlows(@Param("enterprise") Enterprise enterprise);

	@Query("SELECT wf FROM WorkFlow wf WHERE wf.enterprise = :enterprise AND wf.name = :name")
	public WorkFlow getWorkFlow(@Param("enterprise") Enterprise enterprise, @Param("name") String name);

	@Query("SELECT wf FROM WorkFlow wf WHERE wf.enterprise = :enterprise AND LOWER(wf.name) = :name AND wf.id <> :id")
	public boolean isNameAvailable(@Param("enterprise") Enterprise enterprise, @Param("name") String name, @Param("id") Long id);
	
}
