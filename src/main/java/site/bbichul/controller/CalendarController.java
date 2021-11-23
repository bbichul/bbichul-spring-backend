package site.bbichul.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.bbichul.domain.CalendarMemo;
import site.bbichul.dto.CalendarMemoDto;
import site.bbichul.service.CalendarService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/memo")
    public CalendarMemo getMemoClickedDay(@RequestParam String dateData){
        return calendarService.getMemoClickedDay(dateData);
    }

    @PutMapping("/memo")
    public void updateCalendarMemo(@RequestBody CalendarMemoDto calendarMemoDto){
        calendarService.updateMemo(calendarMemoDto);
    }


}
