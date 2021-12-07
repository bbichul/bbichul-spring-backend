package site.bbichul.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@JsonAutoDetect
@Setter
@Getter
@Data
public class TimeRequestDto {
    private Long id;
    private int year;
    private int month;
    private int day;
    private int weekDay;
    private int studyTime;
    private boolean isStudying;
    private int yesterdayTime;
}
