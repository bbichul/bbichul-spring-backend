package site.bbichul.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.bbichul.dto.TimeRequestDto;
import site.bbichul.models.Time;
import site.bbichul.models.User;
import site.bbichul.models.UserInfo;
import site.bbichul.repository.TimeRepository;
import site.bbichul.repository.UserInfoRepository;
import site.bbichul.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class TotalTimeService {
    private final UserRepository userRepository;
    private final TimeRepository timeRepository;

    public Map<String, Integer> getTotalTime(User user) {

        List<Time> userTimeList = timeRepository.findAllByUserId(user.getId());


        int totalTime = 0;

        for (Time userTime : userTimeList) {;
            totalTime += userTime.getStudyTime();
        }
        int totalHour = (int) Math.round((double)totalTime / 3600);


        Map<String, Integer> map = new HashMap();
        map.put("totalHour", totalHour);

        return map;
    }
}
