package mu.codeoffice.repository.settings;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.IssueLinkType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IssueLinkTypeRepository  extends JpaRepository<IssueLinkType, Long> {

	@Query("SELECT t FROM IssueLinkType t WHERE t.enterprise = :enterprise AND t.name = :name")
	public IssueLinkType getIssueLinkType(@Param("enterprise") Enterprise enterprise, @Param("name") String name);
	
	@Query("SELECT COUNT(t) = 0 FROM IssueLinkType t WHERE t.enterprise = :enterprise AND t.name = :name AND t.id <> :id")
	public boolean isNameAvailable(@Param("enterprise") Enterprise enterprise, @Param("name") String name, @Param("id") Long id);

	@Query("SELECT t FROM IssueLinkType t WHERE t.enterprise = :enterprise")
	public List<IssueLinkType> getIssueLinkTypes(@Param("enterprise") Enterprise enterprise);
	
}
