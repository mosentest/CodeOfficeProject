package mu.codeoffice.repository;

import mu.codeoffice.entity.Component;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentRepository extends JpaRepository<Component, Long> {

}
