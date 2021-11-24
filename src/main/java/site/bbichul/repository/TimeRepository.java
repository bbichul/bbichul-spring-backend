package site.bbichul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.bbichul.models.Time;

@Repository
public interface TimeRepository extends JpaRepository<Time,Long> {

}