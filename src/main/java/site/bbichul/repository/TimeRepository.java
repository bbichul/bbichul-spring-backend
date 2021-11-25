package site.bbichul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.bbichul.models.Time;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface TimeRepository extends JpaRepository<Time,Long> {
    // DB에 있는 정보 찾기
    @Transactional
    Optional<Time> findByUserIdAndYearAndMonthAndDay(Long userId, int year, int month , int day );

}
