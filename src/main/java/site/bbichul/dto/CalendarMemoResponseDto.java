package site.bbichul.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CalendarMemoResponseDto {

    private Long CalendarId;
    private String dateData;
    private String contents;
}
