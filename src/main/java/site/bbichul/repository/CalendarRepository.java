package site.bbichul.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.bbichul.models.CalendarMemo;

import java.util.Optional;

@Repository
public interface CalendarRepository extends JpaRepository<CalendarMemo, Long> {
    Optional<CalendarMemo> findByDateData(String dateData);
}
