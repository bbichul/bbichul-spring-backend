package site.bbichul.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import site.bbichul.dto.CalendarMemoResponseDto;
import site.bbichul.models.User;
import site.bbichul.models.UserCalendar;
import site.bbichul.repository.CalendarMemoRepository;
import site.bbichul.repository.UserCalendarRepository;
import site.bbichul.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalendarServiceTest {

    @Mock
    CalendarMemoRepository calendarMemoRepository;

    @Mock
    UserCalendarRepository userCalendarRepository;

    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("캘린더 서비스 생성")
    void createCalendarService() {
        CalendarService calendarService = new CalendarService(userRepository, userCalendarRepository, calendarMemoRepository);

        assertNotNull(calendarService);
    }


    @Test
    @DisplayName("팀이 없는 유저가 캘린더를 가지고 있지 않은 경우")
    void getUserInfoWhenDontHaveCalendar() {

        User user = new User();

        user.setUsername("나무");
        user.setId(1L);

        CalendarService calendarService = new CalendarService(userRepository, userCalendarRepository, calendarMemoRepository);

        ArgumentCaptor<UserCalendar> captor = ArgumentCaptor.forClass(UserCalendar.class);


        List<UserCalendar> notFoundCalendar = new ArrayList<>();

        //Mockito.lenient().
        when(userRepository.findByUsername("나무")).thenReturn(Optional.of(user));
        when(userCalendarRepository.findAllByUserId(1L)).thenReturn(notFoundCalendar);


        //when
        List<UserCalendar> resultCalendarList = calendarService.getUserInfo(user.getUsername());


        //then
        verify(userCalendarRepository, times(1))
                .save(captor.capture());
        UserCalendar result = captor.getValue();

        assertEquals(result.getUser().getId(), 1L);
        assertEquals(result.getUser().getUsername(), "나무");
        assertEquals(result.getIsPrivate(), true);
        assertEquals(result.getCalendarName(), "나무의 캘린더 1");

    }

    @Test
    @DisplayName("팀에 아직 캘린더가 없는 경우")
    void getUserInfoNotFoundTeamCalendar() {

    }



    @Test
    void updateMemo() {
    }

    @Test
    void getMemoClickedDay() {
    }

    @Test
    void getTypeAllMemo() {
    }

    @Test
    void addCalendar() {
    }

    @Test
    void deleteCalendar() {
    }

    @Test
    void renameCalendar() {
    }

}