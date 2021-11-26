package site.bbichul.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.bbichul.dto.CalendarMemoDto;
import site.bbichul.models.CalendarMemo;
import site.bbichul.security.UserDetailsImpl;
import site.bbichul.service.CalendarService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
@Slf4j
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/memo")
    public CalendarMemo getMemoClickedDay(@RequestParam String dateData, @AuthenticationPrincipal UserDetailsImpl userDetails){
        log.info("POST /memo HTTP/1.1");

        String username= userDetails.getUsername();
        return calendarService.getMemoClickedDay(dateData, username);
    }

    @PutMapping("/memo")
    public void updateCalendarMemo(@RequestBody CalendarMemoDto calendarMemoDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        log.info("PUT /memo HTTP/1.1");
        String username= userDetails.getUsername();
        calendarService.updateMemo(calendarMemoDto, username);
    }


}
