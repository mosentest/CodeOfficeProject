package mu.codeoffice.repository.settings;

import mu.codeoffice.entity.settings.GlobalPermission;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalPermissionRepository extends JpaRepository<GlobalPermission, Long> {

}
