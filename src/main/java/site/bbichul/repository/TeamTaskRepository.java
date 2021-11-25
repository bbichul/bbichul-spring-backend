package site.bbichul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.bbichul.models.Team;
import site.bbichul.models.TeamTask;
import site.bbichul.models.User;

import java.util.Optional;

public interface TeamTaskRepository extends JpaRepository<TeamTask, String> {
    Optional<TeamTask> findById(Long id);
}
