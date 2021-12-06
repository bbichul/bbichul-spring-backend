package site.bbichul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.bbichul.models.Team;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, String> {
    Optional<Team> findByTeamname(String teamname);
}
