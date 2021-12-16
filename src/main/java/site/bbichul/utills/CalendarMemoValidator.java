package site.bbichul.utills;

import site.bbichul.dto.CalendarMemoDto;
import site.bbichul.exception.BbichulErrorCode;
import site.bbichul.exception.BbichulException;
import site.bbichul.models.UserCalendar;

public class CalendarMemoValidator {

    public static void validateCalendarMemo(CalendarMemoDto calendarMemoDto, UserCalendar userCalendar) {
        if (userCalendar == null) {
            throw new BbichulException(BbichulErrorCode.CANT_VERIFY_CALENDAR);
        }

    }
}
