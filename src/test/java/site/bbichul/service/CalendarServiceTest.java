package site.bbichul.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.bbichul.dto.CalendarDto;
import site.bbichul.dto.CalendarMemoDto;
import site.bbichul.dto.CalendarMemoResponseDto;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
    @DisplayName("유저캘린더_자동 생성_성공")
    void getUserInfo_UserCalendar_AutoSave() {

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
    @DisplayName("팀 캘린더_자동 생성_성공")
    void getUserInfo_TeamCalendar_AutoCreate() {
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
    @DisplayName("dateDate 형식 미준수_업데이트_실패")
    void updateMemo_BrokenFormat_dateData() {

        //given
        CalendarMemoDto calendarMemoDto = new CalendarMemoDto();
        calendarMemoDto.setCalendarId(1L);
        calendarMemoDto.setContents("가나다라");
        calendarMemoDto.setDateData("2021Y12M230");


        //when
        BbichulException bbichulException = assertThrows(BbichulException.class,
                () -> calendarService.updateMemo(calendarMemoDto));

        //then
        assertEquals(BbichulErrorCode.BROKEN_FORMAT_DATEDATA, bbichulException.getBbichulErrorCode());
        assertEquals(BbichulErrorCode.BROKEN_FORMAT_DATEDATA.getMessage(), bbichulException.getDetailMessage());
    }


    @Test
    @DisplayName("메모 새로 저장_메모 저장_성공")
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
    @DisplayName("dateDate 형식 미준수_메모 불러오기_실패")
    void fail_getMemoClickedDay_Broken_dateData() {
        //given

        Long calendarId = 1L;
        String dateData = "2023년 12월 31일";

        //when
        BbichulException bbichulException = assertThrows(BbichulException.class,
                () -> calendarService.getMemoClickedDay(calendarId, dateData));

        //then
        assertEquals(BbichulErrorCode.BROKEN_FORMAT_DATEDATA, bbichulException.getBbichulErrorCode());
        assertEquals(BbichulErrorCode.BROKEN_FORMAT_DATEDATA.getMessage(), bbichulException.getDetailMessage());

    }

    @Test
    @DisplayName("메모 불러오기_빈 메모_성공")
    void success_getMemoClickedDay_EmptyMemo() {
        //given
        UserCalendar userCalendar = new UserCalendar();
        userCalendar.setId(1L);
        userCalendar.setCalendarName("테스트 캘린더");

        CalendarMemoDto calendarMemoDto = new CalendarMemoDto();
        calendarMemoDto.setContents("하나둘셋");
        calendarMemoDto.setDateData("2021Y12M17");

        CalendarMemo calendarMemo = new CalendarMemo(calendarMemoDto, userCalendar);

        given(calendarMemoRepository.findByUserCalendarIdAndDateData(1L, "2021Y12M17"))
                .willThrow(new BbichulException(BbichulErrorCode.NOT_FOUND_MEMO));
        //when
        CalendarMemoResponseDto calendarMemoResponseDto = calendarService.getMemoClickedDay(userCalendar.getId(), calendarMemoDto.getDateData());

        //then
        assertEquals("", calendarMemoResponseDto.getContents());
        assertEquals(calendarMemoDto.getDateData(), calendarMemoResponseDto.getDateData());


    }

    @Test
    @DisplayName("메모 불러오기_기입력 메모_성공")
    void success_getMemoClickedDay_Memo() {

        //given
        UserCalendar userCalendar = new UserCalendar();
        userCalendar.setId(1L);
        userCalendar.setCalendarName("테스트 캘린더");

        CalendarMemoDto calendarMemoDto = new CalendarMemoDto();
        calendarMemoDto.setContents("하나둘셋");
        calendarMemoDto.setDateData("2021Y12M17");

        CalendarMemo calendarMemo = new CalendarMemo(calendarMemoDto, userCalendar);

        given(calendarMemoRepository.findByUserCalendarIdAndDateData(1L, "2021Y12M17"))
                .willReturn(Optional.of(calendarMemo));
        //when
        CalendarMemoResponseDto calendarMemoResponseDto = calendarService.getMemoClickedDay(userCalendar.getId(), calendarMemoDto.getDateData());

        //then
        assertEquals(calendarMemoDto.getContents(), calendarMemoResponseDto.getContents());
        assertEquals(calendarMemoDto.getDateData(), calendarMemoResponseDto.getDateData());


    }


    @Test
    @DisplayName("개인 캘린더 생성_성공")
    void addUserCalendar() {
        //given
        User user = new User();
        user.setId(1L);
        user.setUsername("테스트용");

        CalendarDto calendarDto = new CalendarDto();
        calendarDto.setCalendarId(1L);
        calendarDto.setCalendarName("유저캘린더테스트용");
        calendarDto.setIsPrivate(true);

        given(userRepository.findByUsername("테스트용")).willReturn(Optional.of(user));

        ArgumentCaptor<UserCalendar> captor = ArgumentCaptor.forClass(UserCalendar.class);

        //when
        calendarService.addCalendar(calendarDto, "테스트용");

        //then
        verify(userCalendarRepository, times(1)).save(captor.capture());
        UserCalendar userCalendar = captor.getValue();

        assertEquals(user, userCalendar.getUser());
        assertEquals(calendarDto.getCalendarName(), userCalendar.getCalendarName());
        assertEquals(true, userCalendar.getIsPrivate());

    }

    @Test
    @DisplayName("팀 캘린더 생성팀_성공")
    void addTeamCalendar() {
        //given
        User user = new User();
        user.setId(1L);
        user.setUsername("테스트용");

        Team team = new Team();
        team.setTeamname("팀캘린더");
        team.setId(1L);
        user.setTeam(team);

        CalendarDto calendarDto = new CalendarDto();
        calendarDto.setCalendarId(1L);
        calendarDto.setCalendarName("팀캘린더테스트용");
        calendarDto.setIsPrivate(false);

        given(userRepository.findByUsername("테스트용")).willReturn(Optional.of(user));

        ArgumentCaptor<UserCalendar> captor = ArgumentCaptor.forClass(UserCalendar.class);

        //when
        calendarService.addCalendar(calendarDto, "테스트용");

        //then
        verify(userCalendarRepository, times(1)).save(captor.capture());
        UserCalendar userCalendar = captor.getValue();

        assertEquals(team, userCalendar.getTeam());
        assertEquals(calendarDto.getCalendarName(), userCalendar.getCalendarName());
        assertEquals(false, userCalendar.getIsPrivate());
    }

}