package site.bbichul.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import site.bbichul.dto.CalendarMemoDto;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

public class CalendarMemoTest {

    @Nested
    @DisplayName("CalendarMemo 객체 만들기")
    class CreateCalendarMemo {
        Long id;
        String dateData;
        String contents;
        UserCalendar userCalendar;


        @BeforeEach
        void setup() {
            id = 1L;
            dateData = "2021Y12M17";
            contents = "memomemo";
            userCalendar = null;
        }

        @Test
        @DisplayName("normal")
        void createNormal() {
            CalendarMemoDto calendarMemoDto = new CalendarMemoDto();
            calendarMemoDto.setCalendarId(id);

        }

    }
}
