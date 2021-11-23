package site.bbichul.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.bbichul.domain.CalendarMemo;

import java.util.Optional;

@Repository
public interface CalendarRepository extends JpaRepository<CalendarMemo, Long> {
    CalendarMemo findByDateData(String dateData);
}
