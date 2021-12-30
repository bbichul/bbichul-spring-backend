package site.bbichul.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;


@JsonAutoDetect
@Data
@AllArgsConstructor

public class TimeRequestDto {
    private Long id;
    private int year;
    private int month;
    private int day;
    private int weekDay;
    private int studyTime;
    private boolean studying;
    private int yesterdayTime;

}
