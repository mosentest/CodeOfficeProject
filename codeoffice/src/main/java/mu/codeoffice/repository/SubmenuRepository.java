package mu.codeoffice.repository;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.Submenu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubmenuRepository extends JpaRepository<Submenu, Long> {

	@Query("SELECT sm FROM Submenu sm WHERE sm.enterprise = :enterprise AND sm.user.id = :user ORDER BY sm.priority ASC")
	public List<Submenu> getSubmenus(@Param("enterprise") Enterprise enterprise, @Param("user") Long user);
	
}
