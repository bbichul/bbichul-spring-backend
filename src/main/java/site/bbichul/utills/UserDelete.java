package site.bbichul.utills;


import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import site.bbichul.models.User;
import site.bbichul.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Service
@Component
@AllArgsConstructor
public class UserDelete {

    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 22 * * *")
    public void deleteUser() {

        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.of(0,0,0));
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.of(23,59,59));

        List<User> found = userRepository.findAllByStatusAndModifiedAtBetween(false,startDatetime,endDatetime);

        for (User user: found){
            userRepository.deleteById(user.getId());
        }

    }

}

