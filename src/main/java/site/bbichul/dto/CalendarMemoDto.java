package site.bbichul.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CalendarMemoDto {

    private Long idx;
    private String dateData;
    private String contents;
}