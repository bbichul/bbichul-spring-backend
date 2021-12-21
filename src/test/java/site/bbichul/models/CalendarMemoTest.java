package site.bbichul.models;

import org.junit.jupiter.api.*;
import org.springframework.core.annotation.Order;
import site.bbichul.dto.CalendarMemoDto;
import site.bbichul.exception.BbichulErrorCode;
import site.bbichul.exception.BbichulException;

import static org.junit.jupiter.api.Assertions.*;

class CalendarMemoTest {

    private UserCalendar userCalendar;
    private CalendarMemoDto calendarMemoDto;
    private String dateData;
    private String contents;
    private User testUser;

    @Nested
    @DisplayName("메모 생성")
    class CreateCalendarMemo{

        @BeforeEach
        void setup() {
            testUser = new User();
            testUser.setId(1L);
            testUser.setUsername("테스트입니다");

            calendarMemoDto = new CalendarMemoDto();
            contents = "가나다라";
        }

        @Test
        @DisplayName("생성 성공")
        void success_createCalendarMemo() {
            //given
            userCalendar = new UserCalendar();
            userCalendar.setUser(testUser);
            userCalendar.setCalendarName("버스타조 캘린더");
            userCalendar.setIsPrivate(true);

            dateData = "2021Y12M17";

            calendarMemoDto.setDateData(dateData);
            calendarMemoDto.setContents(contents);

            //when
            CalendarMemo calendarMemo = new CalendarMemo(calendarMemoDto, userCalendar);

            //then
            assertEquals(dateData, calendarMemo.getDateData());
            assertEquals(contents, calendarMemo.getContents());
            assertEquals(userCalendar, calendarMemo.getUserCalendar());

        }

        @Nested
        @DisplayName("생성 실패")
        class FailCase{

            @Test
            @DisplayName("유저캘린더 미생성")
            void fail_case_userCalendar_null() {
                //given
                dateData = "2021Y12M17";

                calendarMemoDto.setDateData(dateData);
                calendarMemoDto.setContents(contents);

                //when
                BbichulException bbichulException = assertThrows(BbichulException.class, () -> new CalendarMemo(calendarMemoDto, userCalendar));

                //then
                assertEquals(BbichulErrorCode.CANT_VERIFY_CALENDAR.getMessage(), bbichulException.getDetailMessage());

            }


            @Test
            @DisplayName("날짜 형식 미준수")
            void fail_case_dateData_BrokenFormat() {

                //given
                userCalendar = new UserCalendar();
                userCalendar.setUser(testUser);
                userCalendar.setCalendarName("버스타조 캘린더");
                userCalendar.setIsPrivate(true);

                dateData = "2021년12월17";

                calendarMemoDto.setDateData(dateData);
                calendarMemoDto.setContents(contents);

                //when
                BbichulException bbichulException = assertThrows(BbichulException.class, () -> new CalendarMemo(calendarMemoDto, userCalendar));

                //then
                assertEquals(BbichulErrorCode.BROKEN_FORMAT_DATEDATA.getMessage(), bbichulException.getDetailMessage());

            }

        }


    }

    @Nested
    @DisplayName("메모 업데이트")
    class UpdateMemo {

        private CalendarMemo calendarMemo;

        @BeforeEach
        void setup() {
            testUser = new User();
            testUser.setId(1L);
            testUser.setUsername("테스트입니다");

            dateData = "2021Y12M17";
            contents = "가나다라";

            userCalendar = new UserCalendar();
            userCalendar.setUser(testUser);
            userCalendar.setCalendarName("버스타조 캘린더");
            userCalendar.setIsPrivate(true);

            calendarMemoDto = new CalendarMemoDto();
            calendarMemoDto.setDateData(dateData);
            calendarMemoDto.setContents(contents);

            calendarMemo = new CalendarMemo(calendarMemoDto, userCalendar);
        }

        @Test
        @DisplayName("메모 업데이트 성공")
        void success_Updating() {
            //given
            dateData = "2021Y12M17";
            contents = "1234";

            calendarMemoDto.setDateData(dateData);
            calendarMemoDto.setContents(contents);

            //when
            calendarMemo.updateMemo(calendarMemoDto);

            //then
            assertEquals(dateData, calendarMemo.getDateData());
            assertEquals(contents, calendarMemo.getContents());

        }

        @Test
        @DisplayName("날짜 형식 미준수 업데이트 실패")
        void fail_updating_dateData_Format_Broken() {
            //given
            dateData = "22년1월300일";
            contents = "1234";

            calendarMemoDto.setDateData(dateData);
            calendarMemoDto.setContents(contents);

            //when
            BbichulException bbichulException = assertThrows(BbichulException.class, () -> new CalendarMemo(calendarMemoDto, userCalendar));

            //then
            assertEquals(BbichulErrorCode.BROKEN_FORMAT_DATEDATA.getMessage(), bbichulException.getDetailMessage());

        }

    }

}