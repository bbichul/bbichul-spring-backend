package site.bbichul.utills;

public class TimeValidator {

    public static void validateCreateTime(int year, int month, int day, int weekday, int studyTIme){
        if(year == 0 || month == 0 || day == 0 || weekday ==0){
            throw new IllegalArgumentException("날짜가 올바르지 않습니다");
        }
        if(studyTIme == 0){
            throw new IllegalArgumentException("저장 될 공부시간이 없습니다");
        }
        if(studyTIme > 86400){
            throw new IllegalArgumentException("저장 될 공부시간을 초과 했습니다");
        }
    }
}
