package site.bbichul.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.bbichul.models.UserCalendar;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCalendarRepository extends JpaRepository<UserCalendar, Long> {

    List<UserCalendar> findAllByUserId(Long userId);

    List<UserCalendar> findAllByTeamId(Long teamId);

    List<UserCalendar> findAllByUserIdOrTeamId(Long userId, Long teamId);

    Optional<UserCalendar> findById(Long id);
}
