package site.bbichul.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GoalRequestDto {

    private Date end_date;
    private Date start_date;
    private int goal_hour;

}
