package site.bbichul.utills;


public class CalendarValidatingOption {
    private final static String pattern = "\\d{4,4}Y\\d{1,2}M\\d{1,2}";

    public static String getPattern() {
        return pattern;
    }
}
