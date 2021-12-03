package site.bbichul.dto;

import lombok.Data;

@Data
public class TeamProgressbarResponseDto {
    private Long doneCount;
    private Integer percent;
}
