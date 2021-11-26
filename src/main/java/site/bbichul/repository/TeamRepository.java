package site.bbichul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.bbichul.models.Team;
import site.bbichul.models.User;

import javax.transaction.Transactional;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, String> {

//    @Transactional
//    Optional<User> findByUsernameAndTeamId(String username, Long teamId);
}
