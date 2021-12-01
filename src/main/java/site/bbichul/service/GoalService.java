package site.bbichul.service;

import jdk.nashorn.internal.objects.Global;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.bbichul.dto.GoalRequestDto;
import site.bbichul.models.Time;
import site.bbichul.models.User;
import site.bbichul.models.UserInfo;
import site.bbichul.repository.TimeRepository;
import site.bbichul.repository.UserInfoRepository;
import site.bbichul.repository.UserRepository;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RequiredArgsConstructor
@Service
public class GoalService {

    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;
    private final TimeRepository timeRepository;

    @Transactional
    public Map<String, String> updateGoal(GoalRequestDto goalRequestDto, User user) {

        UserInfo userInfo = userInfoRepository.findById(user.getUserInfo().getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다.")
        );
        userInfo.goalUpdate(goalRequestDto);


        Map<String, String> map = new HashMap();	//<키 자료형, 값 자료형>
        map.put("msg", "성공");

        return map;
    }

    public Map<String, Object> getGoal(User user) {

        UserInfo userInfo = userInfoRepository.findById(user.getUserInfo().getId()).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        if (userInfo.getStartDate() == null) {
            Map<String, Object> map = new HashMap();
            map.put("startDate", "");
            map.put("endDate", "");
            map.put("d_day", 0);
            map.put("percent", 0);
            map.put("goal_hour", 0);
            map.put("done_hour", 0);
            return map;
        }
        Map<String, Object> map = new HashMap();
        ArrayList dates = new ArrayList();
        Long btwDate = userInfo.getEndDate().getTime() - userInfo.getStartDate().getTime();
        Long btwDateDays = btwDate / ( 24*60*60*1000);

        int study_time_sum = 0;
        for (int i = 0; i < btwDateDays + 1L; i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(userInfo.getStartDate());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            cal.add(Calendar.DATE, i);
            dates.add(cal.getTime());
            String[] dateSplitList = df.format(dates.get(i)).split("-");
            int year = Integer.parseInt(dateSplitList[0]);
            int month = Integer.parseInt(dateSplitList[1]);
            int day = Integer.parseInt(dateSplitList[2]);

            Time time = timeRepository.findByUserIdAndYearAndMonthAndDay(user.getId(), year, month, day).orElse( null
            );

            if (time == null) {
                continue;
            }

            study_time_sum += time.getStudy_time();
        }
        int done_hour = study_time_sum / 3600;

        long calculate = userInfo.getEndDate().getTime() - userInfo.getStartDate().getTime();

        int d_day = (int) calculate / (24*60*60*1000);

        int percent = (int) Math.round(((double) done_hour / userInfo.getGoalHour()) * 100);

        if (done_hour == 0) {
            percent = 0;
        }

        map.put("startDate", userInfo.getStartDate());
        map.put("endDate", userInfo.getEndDate());
        map.put("d_day", d_day);
        map.put("percent", percent);
        map.put("goal_hour", userInfo.getGoalHour());
        map.put("done_hour", done_hour);

        return map;
    }

}