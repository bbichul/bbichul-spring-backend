package site.bbichul.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ResolutionRequestDto {
    private String content;
    private Date endDate;
    private Date startDate;
    private int goalHour;
}