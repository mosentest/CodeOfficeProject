package mu.codeoffice.repository;

import java.util.List;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.Version;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VersionRepository extends JpaRepository<Version, Long> {
	
	@Query("SELECT v FROM Version v WHERE v.project.code = :project AND v.code = :version AND v.enterprise = :enterprise")
	public Version getProjectVersion(@Param("enterprise") Enterprise enterprise, @Param("project") String project, @Param("version") String version);

	@Query("SELECT v FROM Version v WHERE v.project.id = :project")
	public List<Version> getProjectVersions(@Param("project") Long project);

	@Query("SELECT v FROM Version v WHERE v.project = :project AND v.released = TRUE")
	public List<Version> getUnreleasedVersions(@Param("project") Project project);

}
