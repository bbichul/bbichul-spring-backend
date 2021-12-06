package site.bbichul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.bbichul.models.Team;
import site.bbichul.models.TeamTask;
import site.bbichul.models.User;

import java.util.List;
import java.util.Optional;

public interface TeamTaskRepository extends JpaRepository<TeamTask, Long> {
    List<TeamTask> findAllByTeamId(Long id);

    Long countByTeamIdAndDone(Long id, Boolean done);
}
