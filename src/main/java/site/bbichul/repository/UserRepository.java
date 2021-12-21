package site.bbichul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.bbichul.models.User;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndStatus(String username, boolean status);

    List<User> findAllByStatusAndModifiedAtBetween(boolean status, LocalDateTime start, LocalDateTime end);

    @Transactional
    Optional<User>findById(Long id);

    List<User>findAllByTeamId(Long id);


}
