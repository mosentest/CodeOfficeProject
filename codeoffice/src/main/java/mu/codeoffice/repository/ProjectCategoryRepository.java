package mu.codeoffice.repository;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.ProjectCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectCategoryRepository extends JpaRepository<ProjectCategory, Long> {
	
	@Query("SELECT pc FROM ProjectCategory pc WHERE pc.enterprise = :enterprise")
	public List<ProjectCategory> getProjectCategories(@Param("enterprise") Enterprise enterprise);

	@Query("SELECT pc FROM ProjectCategory pc WHERE pc.enterprise = :enterprise AND pc.id = :category")
	public ProjectCategory getProjectCategory(@Param("category") Long category, @Param("enterprise") Enterprise enterprise);
	
	@Query("SELECT COUNT(pc) > 0 FROM ProjectCategory pc WHERE pc.enterprise = :enterprise AND pc.name = :name")
	public boolean doesCategoryNameExist(@Param("name") String name, @Param("enterprise") Enterprise enterprise);
	
}
