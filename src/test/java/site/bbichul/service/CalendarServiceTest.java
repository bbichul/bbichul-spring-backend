package site.bbichul.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.bbichul.models.Team;
import site.bbichul.models.User;
import site.bbichul.models.UserCalendar;
import site.bbichul.repository.CalendarMemoRepository;
import site.bbichul.repository.UserCalendarRepository;
import site.bbichul.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalendarServiceTest {

    @Mock
    CalendarMemoRepository calendarMemoRepository;

    @Mock
    UserCalendarRepository userCalendarRepository;

    @Mock
    UserRepository userRepository;


    @InjectMocks
    CalendarService calendarService;


    @Test
    @DisplayName("팀이 없는 유저가 캘린더를 가지고 있지 않은 경우")
    void getUserInfoWhenDontHaveCalendar() {

        //given
        User user = new User();

        user.setUsername("나무");
        user.setId(1L);

        ArgumentCaptor<UserCalendar> captor = ArgumentCaptor.forClass(UserCalendar.class);

        List<UserCalendar> notFoundCalendar = new ArrayList<>();

        given(userRepository.findByUsername("나무")).willReturn(Optional.of(user));
        given(userCalendarRepository.findAllByUserId(1L)).willReturn(notFoundCalendar);

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
        //given

        User user = new User();

        user.setUsername("나무");
        user.setId(1L);

        Team testTeam = new Team();
        testTeam.setId(1L);
        testTeam.setTeamname("버스타조");
        user.setTeam(testTeam);

        ArgumentCaptor<UserCalendar> captor = ArgumentCaptor.forClass(UserCalendar.class);

        List<UserCalendar> haveDataCalendar = new ArrayList<>();
        UserCalendar testCalendar = new UserCalendar();
        haveDataCalendar.add(testCalendar);

        List<UserCalendar> notFoundCalendar = new ArrayList<>();

        given(userRepository.findByUsername("나무")).willReturn(Optional.of(user));
        given(userCalendarRepository.findAllByUserId(1L)).willReturn(haveDataCalendar);
        given(userCalendarRepository.findAllByTeamId(1L)).willReturn(notFoundCalendar);


        //when
        List<UserCalendar> resultCalendarList = calendarService.getUserInfo(user.getUsername());

        //then
        verify(userCalendarRepository, times(1))
                .save(captor.capture());

        UserCalendar resultT = captor.getValue();

        assertEquals(resultT.getTeam().getId(), testTeam.getId());
        assertEquals(resultT.getTeam().getTeamname(), testTeam.getTeamname());
        assertEquals(resultT.getCalendarName(), "팀 버스타조의 캘린더 1");

    }



    @Test
    void updateMemoNotFoundMemo() {
        //given


        //when



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