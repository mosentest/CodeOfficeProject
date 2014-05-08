package mu.codeoffice.repository;

import mu.codeoffice.entity.Vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoteRepository extends JpaRepository<Vote, Long> {

	@Query("SELECT v FROM Vote v WHERE v.uniqueId = :uniqueId")
	public Vote getByUniqueId(@Param("uniqueId") String uniqueId);
	
}
