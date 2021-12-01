package site.bbichul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.bbichul.models.Time;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeRepository extends JpaRepository<Time,Long> {

    Optional<Time> findByUserIdAndYearAndMonthAndDay(Long userId, int year, int month , int day );
    List<Time> findAllByUserIdAndYearAndMonth(Long user_id, int year, int month);

}
