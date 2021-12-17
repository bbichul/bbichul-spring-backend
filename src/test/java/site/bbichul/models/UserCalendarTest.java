package site.bbichul.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import site.bbichul.dto.CalendarDto;

import static org.junit.jupiter.api.Assertions.*;

class UserCalendarTest {

    @Nested
    @DisplayName("유저캘린더 객체 생성")
    class CreateUesrCalendarTest {

        @Nested
        @DisplayName("생성 성공")
        class SuccessCreatingUserCalendar{

            @Test
            @DisplayName("유저 캘린더 생성_성공케이스")
            void createPrivateUserCalendar() {

                //given
                User user = new User();
                user.setUsername("테스트용");
                user.setId(20L);

                String calendarName = "테스트 캘린더 1";

                //when
                UserCalendar userCalendar = new UserCalendar(user, true, calendarName);


                //then
                assertNull(userCalendar.getId());
                assertNull(userCalendar.getTeam());
                assertEquals(user, userCalendar.getUser());
                assertEquals("테스트용", userCalendar.getUser().getUsername());
                assertEquals(20L, userCalendar.getUser().getId());
                assertEquals("테스트 캘린더 1", userCalendar.getCalendarName());
            }


            @Test
            @DisplayName("팀 캘린더 생성_성공케이스")
            void createTeamUserCalendar() {

                //given
                User user = new User();
                user.setUsername("테스트용");
                user.setId(20L);

                Team team = new Team();
                team.setTeamname("버스타조");
                team.setId(1L);

                user.setTeam(team);

                String calendarName = "테스트 팀 캘린더 1";

                //when
                UserCalendar teamCalendar = new UserCalendar(team, false, calendarName);


                //then
                assertNull(teamCalendar.getId());
                assertEquals(user.getTeam(), teamCalendar.getTeam());
                assertEquals("버스타조", teamCalendar.getTeam().getTeamname());
                assertEquals(1L, teamCalendar.getTeam().getId());
                assertEquals("테스트 팀 캘린더 1", teamCalendar.getCalendarName());
            }

        }
    }




}