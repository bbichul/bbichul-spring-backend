package site.bbichul.utills;

import site.bbichul.dto.CalendarMemoDto;
import site.bbichul.exception.BbichulErrorCode;
import site.bbichul.exception.BbichulException;
import site.bbichul.models.UserCalendar;

public class CalendarMemoValidator {

    private static String pattern = CalendarValidatingOption.getPattern();

    public static void validateCreateCalendarMemo(CalendarMemoDto calendarMemoDto, UserCalendar userCalendar) {
        if (userCalendar == null) {
            throw new BbichulException(BbichulErrorCode.CANT_VERIFY_CALENDAR);
        }

        if (!calendarMemoDto.getDateData().matches(pattern)) {
            throw new BbichulException(BbichulErrorCode.BROKEN_FORMAT_DATEDATA);
        }
    }

    public static void validateUpdateCalendarMemo(CalendarMemoDto calendarMemoDto) {
        if (!calendarMemoDto.getDateData().matches(pattern)) {
            throw new BbichulException(BbichulErrorCode.BROKEN_FORMAT_DATEDATA);
        }
    }
}
