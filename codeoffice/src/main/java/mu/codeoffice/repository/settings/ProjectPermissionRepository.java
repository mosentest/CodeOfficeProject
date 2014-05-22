package mu.codeoffice.repository.settings;

import mu.codeoffice.entity.settings.ProjectPermission;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectPermissionRepository extends JpaRepository<ProjectPermission, Long> {

}
