package mu.codeoffice.repository;

import mu.codeoffice.entity.Version;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionRepository extends JpaRepository<Version, Long> {

}
