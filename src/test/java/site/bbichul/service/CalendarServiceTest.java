package site.bbichul.service;

import org.junit.jupiter.api.BeforeEach;
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

    private final Team defaultTeam = new Team("테스트팀");

    private final User defaultUser = new User();

    private final UserCalendar defaultUserCalendar = new UserCalendar();

    @BeforeEach
    void setup() {
        defaultUser.setUsername("테스트용");
        defaultUser.setId(1L);

        defaultTeam.setId(1L);

        defaultUserCalendar.setId(1L);
        defaultUserCalendar.setCalendarName("서비스테스트캘린더");
    }




    @Test
    @DisplayName("유저캘린더_자동 생성_성공")
    void getUserInfo_UserCalendar_AutoSave() {

        //given

        ArgumentCaptor<UserCalendar> captor = ArgumentCaptor.forClass(UserCalendar.class);

        List<UserCalendar> notFoundCalendar = new ArrayList<>();

        given(userRepository.findByUsername("테스트용")).willReturn(Optional.of(defaultUser));
        given(userCalendarRepository.findAllByUserId(1L)).willReturn(notFoundCalendar);

        //when
        calendarService.getUserInfo(defaultUser);


        //then
        verify(userCalendarRepository, times(1))
                .save(captor.capture());
        UserCalendar result = captor.getValue();

        assertEquals(result.getUser().getId(), 1L);
        assertEquals(result.getUser().getUsername(), "테스트용");
        assertEquals(result.getIsPrivate(), true);
        assertEquals(result.getCalendarName(), "테스트용의 캘린더 1");

    }

    @Test
    @DisplayName("팀 캘린더_자동 생성_성공")
    void getUserInfo_TeamCalendar_AutoCreate() {
        //given

        defaultUser.setTeam(defaultTeam);

        ArgumentCaptor<UserCalendar> captor = ArgumentCaptor.forClass(UserCalendar.class);

        List<UserCalendar> haveDataCalendar = new ArrayList<>();
        UserCalendar testCalendar = new UserCalendar();
        haveDataCalendar.add(testCalendar);

        List<UserCalendar> notFoundCalendar = new ArrayList<>();

        given(userRepository.findByUsername("테스트용")).willReturn(Optional.of(defaultUser));
        given(userCalendarRepository.findAllByUserId(1L)).willReturn(haveDataCalendar);
        given(userCalendarRepository.findAllByTeamId(1L)).willReturn(notFoundCalendar);


        //when
        List<UserCalendar> resultCalendarList = calendarService.getUserInfo(defaultUser);

        //then
        verify(userCalendarRepository, times(1))
                .save(captor.capture());

        UserCalendar resultT = captor.getValue();

        assertEquals(resultT.getTeam().getId(), defaultTeam.getId());
        assertEquals(resultT.getTeam().getTeamname(), defaultTeam.getTeamname());
        assertEquals(resultT.getCalendarName(), "팀 테스트팀의 캘린더 1");

    }

    @Test
    @DisplayName("dateDate 형식 미준수_업데이트_실패")
    void updateMemo_BrokenFormat_dateData() {

        //given
        CalendarMemoDto calendarMemoDto = new CalendarMemoDto();
        calendarMemoDto.setCalendarId(1L);
        calendarMemoDto.setContents("가나다라");
        calendarMemoDto.setDateData("2021Y12M230");

        defaultUserCalendar.setUser(defaultUser);
        defaultUserCalendar.setIsPrivate(true);

        given(userRepository.findByUsername("테스트용")).willReturn(Optional.of(defaultUser));
        given(userCalendarRepository.findByCalendarId(1L)).willReturn(Optional.of(defaultUserCalendar));


        //when
        BbichulException bbichulException = assertThrows(BbichulException.class,
                () -> calendarService.updateMemo(calendarMemoDto, defaultUser));

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

        defaultUserCalendar.setUser(defaultUser);
        defaultUserCalendar.setIsPrivate(true);


        given(userRepository.findByUsername("테스트용")).willReturn(Optional.of(defaultUser));
        given(calendarMemoRepository.findByUserCalendarIdAndDateData(
                calendarMemoDto.getCalendarId(),
                calendarMemoDto.getDateData())
        ).willThrow(BbichulException.class);

        given(userCalendarRepository.findByCalendarId(calendarMemoDto.getCalendarId())).willReturn(Optional.of(defaultUserCalendar));
        given(userCalendarRepository.getById(calendarMemoDto.getCalendarId())).willReturn(defaultUserCalendar);

        ArgumentCaptor<CalendarMemo> captor = ArgumentCaptor.forClass(CalendarMemo.class);


        //when
        calendarService.updateMemo(calendarMemoDto, defaultUser);

        //then
        verify(calendarMemoRepository, times(1)).
                save(captor.capture());
        CalendarMemo testResult = captor.getValue();

        assertEquals(calendarMemoDto.getContents(), testResult.getContents());
        assertEquals(calendarMemoDto.getDateData(), testResult.getDateData());
        assertEquals(defaultUserCalendar, testResult.getUserCalendar());
    }


    @Test
    @DisplayName("dateDate 형식 미준수_메모 불러오기_실패")
    void fail_getMemoClickedDay_Broken_dateData() {
        //given

        Long calendarId = 1L;
        String dateData = "2023년 12월 31일";

        given(userRepository.findByUsername("테스트용")).willReturn(Optional.of(defaultUser));
        given(userCalendarRepository.findByCalendarId(calendarId)).willReturn(Optional.of(defaultUserCalendar));

        //when
        BbichulException bbichulException = assertThrows(BbichulException.class,
                () -> calendarService.getMemoClickedDay(calendarId, dateData, defaultUser));

        //then
        assertEquals(BbichulErrorCode.BROKEN_FORMAT_DATEDATA, bbichulException.getBbichulErrorCode());
        assertEquals(BbichulErrorCode.BROKEN_FORMAT_DATEDATA.getMessage(), bbichulException.getDetailMessage());

    }

    @Test
    @DisplayName("메모 불러오기_빈 메모_성공")
    void success_getMemoClickedDay_EmptyMemo() {
        //given

        CalendarMemoDto calendarMemoDto = new CalendarMemoDto();
        calendarMemoDto.setContents("하나둘셋");
        calendarMemoDto.setDateData("2021Y12M17");

        CalendarMemo calendarMemo = new CalendarMemo(calendarMemoDto, defaultUserCalendar);

        given(userRepository.findByUsername("테스트용")).willReturn(Optional.of(defaultUser));
        given(userCalendarRepository.findByCalendarId(1L)).willReturn(Optional.of(defaultUserCalendar));
        given(calendarMemoRepository.findByUserCalendarIdAndDateData(1L, "2021Y12M17"))
                .willThrow(new BbichulException(BbichulErrorCode.NOT_FOUND_MEMO));
        //when
        CalendarMemoResponseDto calendarMemoResponseDto =
                calendarService.getMemoClickedDay(defaultUserCalendar.getId(), calendarMemoDto.getDateData(), defaultUser);

        //then
        assertEquals("", calendarMemoResponseDto.getContents());
        assertEquals(calendarMemoDto.getDateData(), calendarMemoResponseDto.getDateData());


    }

    @Test
    @DisplayName("메모 불러오기_기입력 메모_성공")
    void success_getMemoClickedDay_Memo() {

        //given

        CalendarMemoDto calendarMemoDto = new CalendarMemoDto();
        calendarMemoDto.setContents("하나둘셋");
        calendarMemoDto.setDateData("2021Y12M17");

        CalendarMemo calendarMemo = new CalendarMemo(calendarMemoDto, defaultUserCalendar);

        given(userRepository.findByUsername("테스트용")).willReturn(Optional.of(defaultUser));
        given(userCalendarRepository.findByCalendarId(1L)).willReturn(Optional.of(defaultUserCalendar));
        given(calendarMemoRepository.findByUserCalendarIdAndDateData(1L, "2021Y12M17"))
                .willReturn(Optional.of(calendarMemo));
        //when
        CalendarMemoResponseDto calendarMemoResponseDto = calendarService.getMemoClickedDay(defaultUserCalendar.getId(), calendarMemoDto.getDateData(), defaultUser);

        //then
        assertEquals(calendarMemoDto.getContents(), calendarMemoResponseDto.getContents());
        assertEquals(calendarMemoDto.getDateData(), calendarMemoResponseDto.getDateData());


    }


    @Test
    @DisplayName("개인 캘린더 생성_성공")
    void addUserCalendar() {
        //given

        CalendarDto calendarDto = new CalendarDto();
        calendarDto.setCalendarId(1L);
        calendarDto.setCalendarName("유저캘린더테스트용");
        calendarDto.setIsPrivate(true);

        given(userRepository.findByUsername("테스트용")).willReturn(Optional.of(defaultUser));

        ArgumentCaptor<UserCalendar> captor = ArgumentCaptor.forClass(UserCalendar.class);

        //when
        calendarService.addCalendar(calendarDto, defaultUser);

        //then
        verify(userCalendarRepository, times(1)).save(captor.capture());
        UserCalendar userCalendar = captor.getValue();

        assertEquals(defaultUser, userCalendar.getUser());
        assertEquals(calendarDto.getCalendarName(), userCalendar.getCalendarName());
        assertEquals(true, userCalendar.getIsPrivate());

    }

    @Test
    @DisplayName("팀 캘린더 생성팀_성공")
    void addTeamCalendar() {
        //given
        defaultUser.setTeam(defaultTeam);

        CalendarDto calendarDto = new CalendarDto();
        calendarDto.setCalendarId(1L);
        calendarDto.setCalendarName("팀캘린더테스트용");
        calendarDto.setIsPrivate(false);

        given(userRepository.findByUsername("테스트용")).willReturn(Optional.of(defaultUser));

        ArgumentCaptor<UserCalendar> captor = ArgumentCaptor.forClass(UserCalendar.class);

        //when
        calendarService.addCalendar(calendarDto, defaultUser);

        //then
        verify(userCalendarRepository, times(1)).save(captor.capture());
        UserCalendar userCalendar = captor.getValue();

        assertEquals(defaultTeam, userCalendar.getTeam());
        assertEquals(calendarDto.getCalendarName(), userCalendar.getCalendarName());
        assertEquals(false, userCalendar.getIsPrivate());
    }

}