package site.bbichul.dto;

import lombok.Data;

@Data
public class CalendarDto {
    private Long calendarId;
    private Boolean isPrivate;
    private String calendarName;
    private Integer version;
}
