package site.bbichul.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import site.bbichul.domain.CalendarMemo;
import site.bbichul.dto.CalendarMemoDto;
import site.bbichul.service.CalendarService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
@Slf4j
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/memo")
    public CalendarMemo getMemoClickedDay(@RequestParam String dateData){
        log.info("POST /memo HTTP/1.1");
        return calendarService.getMemoClickedDay(dateData);
    }

    @PutMapping("/memo")
    public void updateCalendarMemo(@RequestBody CalendarMemoDto calendarMemoDto){
        log.info("PUT /memo HTTP/1.1");
        calendarService.updateMemo(calendarMemoDto);
    }


}
