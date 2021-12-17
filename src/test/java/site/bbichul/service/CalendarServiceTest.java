package site.bbichul.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.bbichul.dto.CalendarMemoDto;
import site.bbichul.exception.BbichulErrorCode;
import site.bbichul.exception.BbichulException;
import site.bbichul.models.CalendarMemo;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    @DisplayName("캘린더1 자동 생성 성공")
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
    @DisplayName("팀 캘린더 자동 생성 성공")
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
    @DisplayName("dateDate 형식 미준수 업데이트 실패")
    void updateMemo_BrokenFormat_dateData() {

        //given
        CalendarMemoDto calendarMemoDto = new CalendarMemoDto();
        calendarMemoDto.setCalendarId(1L);
        calendarMemoDto.setContents("가나다라");
        calendarMemoDto.setDateData("2021Y12M230");


        //when
        BbichulException bbichulException = assertThrows(BbichulException.class, () -> calendarService.updateMemo(calendarMemoDto));

        //then
        assertEquals(BbichulErrorCode.BROKEN_FORMAT_DATEDATA.getMessage(), bbichulException.getDetailMessage());
    }


    @Test
    @DisplayName("메모 찾지 못해 새로 저장")
    void updateMemo_NotFoundMemo_SaveMemo() {
        //given
        CalendarMemoDto calendarMemoDto = new CalendarMemoDto();
        calendarMemoDto.setCalendarId(1L);
        calendarMemoDto.setContents("가나다라");
        calendarMemoDto.setDateData("2021Y12M17");

        given(calendarMemoRepository.findByUserCalendarIdAndDateData(
                calendarMemoDto.getCalendarId(),
                calendarMemoDto.getDateData())
        ).willThrow(BbichulException.class);

        User user = new User();
        user.setUsername("나무");
        user.setId(1L);

        UserCalendar userCalendar = new UserCalendar(user, true, "업데이트테스트");

        given(userCalendarRepository.getById(calendarMemoDto.getCalendarId())).willReturn(userCalendar);

        ArgumentCaptor<CalendarMemo> captor = ArgumentCaptor.forClass(CalendarMemo.class);


        //when
        calendarService.updateMemo(calendarMemoDto);

        //then
        verify(calendarMemoRepository, times(1)).
                save(captor.capture());
        CalendarMemo calendarMemo = captor.getValue();

        assertEquals(calendarMemo.getContents(), calendarMemoDto.getContents());
        assertEquals(calendarMemo.getDateData(), calendarMemoDto.getDateData());
        assertEquals(userCalendar, calendarMemo.getUserCalendar());
    }





    @Test
    void getMemoClickedDay() {
    }


    @Test
    void addCalendar() {
    }

    @Test
    void renameCalendar() {
    }

}