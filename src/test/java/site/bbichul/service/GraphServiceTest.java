package site.bbichul.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import site.bbichul.models.*;
import site.bbichul.repository.CalendarMemoRepository;
import site.bbichul.repository.TimeRepository;
import site.bbichul.repository.UserCalendarRepository;
import site.bbichul.repository.UserRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class GraphServiceTest {
    @Mock
    UserCalendarRepository userCalendarRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    TimeRepository timeRepository;

    @InjectMocks
    GraphService graphService;

    @Test
    @DisplayName("주별 시간그래프 성공케이스")
    void getLineGraph() {
        //given
        User user = new User();
        user.setUsername("최대환");
        user.setId(1L);
        user.setPassword("123456");
        user.setStatus(true);

        Time time = new Time();
        time.setId(1L);
        time.setDay(20);
        time.setStudyTime(53535);
        time.setMonth(12);
        time.setWeekDay(0);
        time.setYear(2021);
        time.setUser(user);

        List<Time> timeList = new ArrayList<>();
        timeList.add(time);

        given(userRepository.findByUsername("최대환")).willReturn(Optional.of(user));
        given(timeRepository.findById(1L)).willReturn(Optional.of(time));
        given(timeRepository.findAllByUserIdAndYearAndMonthOrderByDayDesc(user.getId(), 2021, 12)).willReturn(timeList);


//        when
        Map<String, Object> result = graphService.drawLineGraph("line",2021, 12, user);
        System.out.println(result);

        //then
        System.out.println(result.get("dayTimeList"));

        ArrayList dayTimeList = new ArrayList();
        Calendar cal = Calendar.getInstance();
        cal.set(2021, 12 - 1, 1);
        int lastDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 0; i <= lastDayOfMonth; i++) {
            dayTimeList.add(0);
        }
        dayTimeList.set(20, 53535 / 60);
        System.out.println(dayTimeList);

        assertEquals(result.get("dayTimeList"), dayTimeList);

    }

    @Test
    @DisplayName("주별 시간그래프 성공케이스")
    void getbarGraph() {
        //given
        User user = new User();
        user.setUsername("최대환");
        user.setId(1L);
        user.setPassword("123456");
        user.setStatus(true);

        Time time = new Time();
        time.setId(1L);
        time.setDay(20);
        time.setStudyTime(53535);
        time.setMonth(12);
        time.setWeekDay(0);
        time.setYear(2021);
        time.setUser(user);

        List<Time> timeList = new ArrayList<>();
        timeList.add(time);

        given(userRepository.findByUsername("최대환")).willReturn(Optional.of(user));
        given(timeRepository.findById(1L)).willReturn(Optional.of(time));
        given(timeRepository.findAllByUserIdAndYearAndMonthAndWeekDay(user.getId(), 2021, 12, 0)).willReturn(timeList);


//        when
        Map<String, Object> result = graphService.drawLineGraph("bar",2021, 12, user);

        //then
        assertEquals(result.get("monday"), 53535 / 60);
        assertEquals(result.get("tuesday"), 0);
        assertEquals(result.get("wednesday"), 0);
        assertEquals(result.get("thursday"), 0);
        assertEquals(result.get("friday"), 0);
        assertEquals(result.get("saturday"), 0);
        assertEquals(result.get("sunday"), 0);


    }
}
