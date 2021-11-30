package site.bbichul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.bbichul.models.CalendarMemo;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarMemoRepository extends JpaRepository<CalendarMemo, Long> {

    Optional<CalendarMemo> findByUserIdAndDateData(Long id, String dateDate);

    Optional<CalendarMemo> findByUserCalendarIdAndDateData(Long id, String dateData);

    List<CalendarMemo> findAllByUserCalendarId(Long calendarId);
}