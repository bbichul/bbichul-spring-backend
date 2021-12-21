package site.bbichul.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.bbichul.dto.GoalRequestDto;
import site.bbichul.models.Time;
import site.bbichul.models.User;
import site.bbichul.models.UserCalendar;
import site.bbichul.models.UserInfo;
import site.bbichul.repository.*;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GoalServiceTest {
    @Mock
    CalendarMemoRepository calendarMemoRepository;

    @Mock
    GoalRequestDto goalRequestDto;

    @Mock
    TimeRepository timeRepository;

    @Mock
    UserInfoRepository userInfoRepository;

    @InjectMocks
    GoalService goalService;

    @Test
    @DisplayName("목표설정 성공케이스")
    void updateGoal() throws ParseException {
        //given
        User user = new User();
        UserInfo userInfo = new UserInfo();
        user.setUsername("최대환");
        user.setId(1L);
        user.setPassword("123456");
        user.setStatus(true);

        userInfo.setId(1L);
        user.setUserInfo(userInfo);

        goalRequestDto.setGoalHour(40);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse("2021-12-20");

        goalRequestDto.setEndDate(format.parse("2021-12-20"));
        goalRequestDto.setEndDate(format.parse("2021-12-25"));

        given(userInfoRepository.findById(1L)).willReturn(Optional.of(userInfo));

//        when
        Map<String, String> result = goalService.updateGoal(goalRequestDto, user);

        //then
        assertEquals(result.get("msg"), "SUCCESS");
    }


    @Test
    @DisplayName("목표 조회 성공 케이스")
    void getGoal() throws ParseException {
        //given
        User user = new User();
        UserInfo userInfo = new UserInfo();
        Time time = new Time();
        user.setUsername("최대환");
        user.setId(1L);
        user.setPassword("123456");
        user.setStatus(true);
        time.setUser(user);
        time.setStudyTime(53535);
        time.setYear(2021);
        time.setMonth(12);
        time.setDay(24);

        userInfo.setId(1L);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        userInfo.setStartDate(format.parse("2021-12-24"));
        userInfo.setEndDate(format.parse("2021-12-24"));
        userInfo.setGoalHour(40);
        user.setUserInfo(userInfo);


        given(userInfoRepository.findById(1L)).willReturn(Optional.of(userInfo));

        given(timeRepository.findByUserIdAndYearAndMonthAndDay(1L,2021, 12, 24)).willReturn(Optional.of(time));

//        when
        Map<String, Object> result = goalService.getGoal(user);


        //then
        assertEquals(result.get("goalHour"), 40);
        assertEquals(result.get("endDate"), format.parse("2021-12-24"));
        assertEquals(result.get("dDay"), 0);
        assertEquals(result.get("doneHour"), 53535 / 3600);
        assertEquals(result.get("percent"), (int) Math.round(((double) (53535 / 3600) / 40) * 100));
        assertEquals(result.get("startDate"), format.parse("2021-12-24"));

    }
}
