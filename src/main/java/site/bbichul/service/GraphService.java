package site.bbichul.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import site.bbichul.dto.GoalRequestDto;
import site.bbichul.dto.GraphRequestDto;
import site.bbichul.dto.TimeRequestDto;
import site.bbichul.models.Time;
import site.bbichul.models.User;
import site.bbichul.models.UserInfo;
import site.bbichul.repository.TimeRepository;

import javax.transaction.Transactional;
import java.time.Year;
import java.util.*;

@RequiredArgsConstructor
@Service
public class GraphService {

    private final TimeRepository timeRepository;

    @Transactional
    public Map<String, Object> drawLineGraph(String type, Integer year, Integer month, User user) {
        if (Objects.equals(type, "line")) {

            List<Time> time = timeRepository.findAllByUserIdAndYearAndMonthOrderByDayDesc(user.getId(), year, month);


            Calendar cal = Calendar.getInstance();

            cal.set(year, month - 1, 1);

            int lastDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

            ArrayList dayList = new ArrayList();
            ArrayList dayTimeList = new ArrayList();

            for (int i = 0; i <= lastDayOfMonth; i++) {
                dayList.add(i);
                dayTimeList.add(0);
            }


            for (Time day : time) {
                dayTimeList.set(day.getDay(), day.getStudyTime());
            }


            Map<String, Object> map = new HashMap();    //<키 자료형, 값 자료형>
            map.put("dayList", dayList);
            map.put("dayTimeList", dayTimeList);

            return map;
        } else if (Objects.equals(type, "bar")) {


//    @Transactional
//    public Map<String, Object> drawBarGraph(GraphRequestDto graphRequestDto, User user) {
//        int year = graphRequestDto.getYear();
//        int month = graphRequestDto.getMonth();

            ArrayList weekdayAvgStudyTimeList = new ArrayList();

            for (int weekday = 0; weekday < 7; weekday++) {
                List<Time> weeklyUserData = timeRepository.findAllByUserIdAndYearAndMonthAndWeekDay(user.getId(), year, month, weekday);
                weekdayAvgStudyTimeList.add(0);

                int weekdaySum = 0;
                int dayCount = 0;

                for (Time day : weeklyUserData) {
                    weekdaySum += day.getStudyTime();
                    dayCount += 1;
                }
                int weekdayAvgStudyTime = 1;
                try {
                    weekdayAvgStudyTime = weekdaySum / dayCount;
                } catch (ArithmeticException e) {
                    weekdayAvgStudyTime = 0;
                }


                weekdayAvgStudyTimeList.set(weekday, weekdayAvgStudyTime);

            }

            Map<String, Object> map = new HashMap();    //<키 자료형, 값 자료형>
            map.put("monday", weekdayAvgStudyTimeList.get(0));
            map.put("tuesday", weekdayAvgStudyTimeList.get(1));
            map.put("wednesday", weekdayAvgStudyTimeList.get(2));
            map.put("thursday", weekdayAvgStudyTimeList.get(3));
            map.put("friday", weekdayAvgStudyTimeList.get(4));
            map.put("saturday", weekdayAvgStudyTimeList.get(5));
            map.put("sunday", weekdayAvgStudyTimeList.get(6));

            return map;
        } else {
            Map<String, Object> map = new HashMap();
            return map;
        }

    }
}
