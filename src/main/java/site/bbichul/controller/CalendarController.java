package site.bbichul.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.bbichul.dto.CalendarMemoDto;
import site.bbichul.dto.CalendarMemoResponseDto;
import site.bbichul.models.CalendarMemo;
import site.bbichul.models.UserCalendar;
import site.bbichul.security.UserDetailsImpl;
import site.bbichul.service.CalendarService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendars")
@Slf4j
public class CalendarController {

    private final CalendarService calendarService;


    @GetMapping
    public List<UserCalendar> getCalendarInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("GET /info HTTP/1.1");

        String username = userDetails.getUsername();
        return calendarService.getUserInfo(username);
    }


    @PutMapping("/calendar/memo")
    public void updateCalendarMemo(@RequestBody CalendarMemoDto calendarMemoDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        log.info("PUT /memo HTTP/1.1");
        calendarService.updateMemo(calendarMemoDto);
    }



    @GetMapping("/calendar/memo")
    public CalendarMemoResponseDto getMemoClickedDay(@RequestParam("id") Long calendarId, @RequestParam("date") String dateData , @AuthenticationPrincipal UserDetailsImpl userDetails){
        log.info("GET /memo HTTP/1.1");

        return calendarService.getMemoClickedDay(calendarId, dateData);
    }



    @GetMapping("/calendar")
    public List<CalendarMemo> getMemo(@RequestParam("id") Long calendarId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("GET /option HTTP/1.1");

        return calendarService.getTypeAllMemo(calendarId);
    }


//    @PostMapping("/calendar")
//    public String addCalendar(@RequestBody CalenderDto calenderDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        log.info("POST /option HTTP/1.1");
//
//        String username = userDetails.getUsername();
//        boolean isPrivate = calenderDto.getIsPrivate();
//        calendarService.addCalendar(isPrivate, username);
//        return "캘린더가 추가되었습니다 !";
//    }

}
