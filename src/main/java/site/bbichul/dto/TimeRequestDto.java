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
    private int year;
    private int month;
    private int day;
    private int weekday;
    private int study_time;
    private boolean isstudying;
}
