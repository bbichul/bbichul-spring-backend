package site.bbichul.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ResolutionRequestDto {
    private String content;
    private Date end_date;
    private Date start_date;
    private int goal_hour;
}