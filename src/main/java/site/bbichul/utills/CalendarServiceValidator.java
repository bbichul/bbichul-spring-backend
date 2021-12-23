package site.bbichul.utills;

import site.bbichul.exception.BbichulErrorCode;
import site.bbichul.exception.BbichulException;

public class CalendarServiceValidator{

    private static String pattern = CalendarValidatingOption.getPattern();

    public static void validateServiceDateData(String dateDate) {
        if (!dateDate.matches(pattern)){
            throw new BbichulException(BbichulErrorCode.BROKEN_FORMAT_DATEDATA);
        }
    }


}
