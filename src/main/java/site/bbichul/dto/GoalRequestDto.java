package site.bbichul.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GoalRequestDto {

    private Date endDate;
    private Date startDate;
    private int goalHour;

}
