package site.bbichul.utills;


import lombok.extern.slf4j.Slf4j;
import site.bbichul.dto.CalendarDto;
import site.bbichul.exception.BbichulErrorCode;
import site.bbichul.exception.BbichulException;
import site.bbichul.models.Team;
import site.bbichul.models.User;

@Slf4j
public class UserCalendarValidator {

    public static void validateCreateUserCalendar(User user, boolean isPrivate, String calendarName) {
        if (user == null) {
            throw new BbichulException(BbichulErrorCode.CANT_VERIFY_USER);
        }

        if (!isPrivate) {
            throw new BbichulException(BbichulErrorCode.NOT_CHECKED_PRIVATE_USER_CALENDAR);
        }

        if (calendarName == null || calendarName.trim().length() == 0) {
            throw new BbichulException(BbichulErrorCode.CANT_INPUT_VACUUM);
        }
    }

    public static void validateCreateTeamCalendar(Team team, boolean isPrivate, String calendarName) {
        if (team == null) {
            throw new BbichulException(BbichulErrorCode.CANT_VERIFY_TEAM);
        }

        if (isPrivate) {
            throw new BbichulException(BbichulErrorCode.CHECKED_PRIVATE_TEAM_CALENDAR);
        }

        if (calendarName == null || calendarName.trim().length() == 0) {
            throw new BbichulException(BbichulErrorCode.CANT_INPUT_VACUUM);
        }
    }
}
