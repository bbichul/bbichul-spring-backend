package site.bbichul.models;

import org.junit.jupiter.api.*;
import site.bbichul.dto.CalendarDto;
import site.bbichul.exception.BbichulErrorCode;
import site.bbichul.exception.BbichulException;

import static org.junit.jupiter.api.Assertions.*;

class UserCalendarTest {

    private User testUser;
    private Team testTeam;
    private String calendarName;

    @Nested
    @DisplayName("유저캘린더 객체 생성")
    class CreateUserCalendarTest {

        @Nested
        @DisplayName("생성 성공")
        class SuccessCreatingUserCalendar {

            @BeforeEach
            void successSetup() {
                testUser = new User();
                testUser.setUsername("테스트용");
                testUser.setId(20L);
            }

            @Test
            @DisplayName("유저 캘린더 생성 성공")
            void createPrivateUserCalendar() {

                //given
                calendarName = "테스트 캘린더 1";

                //when
                UserCalendar userCalendar = new UserCalendar(testUser, true, calendarName);


                //then
                assertNull(userCalendar.getId());
                assertNull(userCalendar.getTeam());
                assertEquals(testUser, userCalendar.getUser());
                assertEquals("테스트용", userCalendar.getUser().getUsername());
                assertEquals(20L, userCalendar.getUser().getId());
                assertEquals("테스트 캘린더 1", userCalendar.getCalendarName());
            }


            @Test
            @DisplayName("팀 캘린더 생성 성공")
            void createTeamUserCalendar() {

                //given
                testTeam = new Team();
                testTeam.setTeamname("버스타조");
                testTeam.setId(1L);
                testUser.setTeam(testTeam);
                calendarName = "테스트 팀 캘린더 1";


                //when
                UserCalendar teamCalendar = new UserCalendar(testTeam, false, calendarName);


                //then
                assertNull(teamCalendar.getId());
                assertEquals(testUser.getTeam(), teamCalendar.getTeam());
                assertEquals("버스타조", teamCalendar.getTeam().getTeamname());
                assertEquals(1L, teamCalendar.getTeam().getId());
                assertEquals("테스트 팀 캘린더 1", teamCalendar.getCalendarName());
            }

        }

        @Nested
        @DisplayName("생성 실패")
        class FailCreateUserCalendar {

            @Test
            @DisplayName("유저 캘린더 생성 실패 유저 누락")
            void failCreateUserCalendar_UserNull() {
                //given
                calendarName = "테스트 캘린더 1";

                //when
                BbichulException bbichulException = assertThrows(
                        BbichulException.class, () ->
                                new UserCalendar(testUser, true, calendarName));

                //then
                assertEquals(BbichulErrorCode.CANT_VERIFY_USER.getMessage(), bbichulException.getDetailMessage());
            }

            @Test
            @DisplayName("유저 캘린더 생성 실패 Private 미체크")
            void failCreateUserCalendar_NotCheckPrivate() {
                //given
                testUser = new User();
                testUser.setUsername("테스트용");
                testUser.setId(20L);

                calendarName = "테스트 캘린더 1";

                //when
                BbichulException bbichulException = assertThrows(
                        BbichulException.class, () ->
                                new UserCalendar(testUser, false, calendarName));

                //then
                assertEquals(BbichulErrorCode.NOT_CHECKED_PRIVATE_USER_CALENDAR.getMessage(), bbichulException.getDetailMessage());
            }

            @Test
            @DisplayName("유저 캘린더 생성 실패 이름공백")
            void failCreateUserCalendar_CalendarNameVacuum() {
                //given
                testUser = new User();
                testUser.setUsername("테스트용");
                testUser.setId(20L);

                calendarName = "";

                //when
                BbichulException bbichulException = assertThrows(
                        BbichulException.class, () ->
                                new UserCalendar(testUser, true, calendarName));

                //then
                assertEquals(BbichulErrorCode.CANT_INPUT_VACUUM.getMessage(), bbichulException.getDetailMessage());

            }


            @Test
            @DisplayName("팀 캘린더 생성 실패 팀 누락")
            void failCreateTeamCalendar_TeamNull() {
                //given
                calendarName = "테스트 캘린더 1";

                //when
                BbichulException bbichulException = assertThrows(
                        BbichulException.class, () ->
                                new UserCalendar(testTeam, false, calendarName));

                //then
                assertEquals(BbichulErrorCode.CANT_VERIFY_TEAM.getMessage(), bbichulException.getDetailMessage());
            }


            @Test
            @DisplayName("팀 캘린더 생성 실패 Private 체크")
            void failCreateTeamCalendar_CheckedPrivate() {
                //given
                testTeam = new Team();
                testTeam.setTeamname("버스타조");
                testTeam.setId(1L);

                calendarName = "테스트 팀 캘린더 1";

                //when
                BbichulException bbichulException = assertThrows(
                        BbichulException.class, () ->
                                new UserCalendar(testTeam, true, calendarName));

                //then
                assertEquals(BbichulErrorCode.CHECKED_PRIVATE_TEAM_CALENDAR.getMessage(), bbichulException.getDetailMessage());
            }

            @Test
            @DisplayName("팀 캘린더 생성 실패 이름공백")
            void failCreateTeamCalendar_CalendarNameVacuum() {
                //given
                testTeam = new Team();
                testTeam.setTeamname("버스타조");
                testTeam.setId(1L);

                calendarName = "";

                //when
                BbichulException bbichulException = assertThrows(
                        BbichulException.class, () ->
                                new UserCalendar(testTeam, false, calendarName));

                //then
                assertEquals(BbichulErrorCode.CANT_INPUT_VACUUM.getMessage(), bbichulException.getDetailMessage());

            }
        }
    }


}