package site.bbichul.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CalendarMemoDto {
    private String dateData;

    private String contents;
}