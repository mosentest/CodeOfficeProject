package mu.codeoffice.repository;

import mu.codeoffice.entity.ProjectRole;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<ProjectRole, Long> {

}
