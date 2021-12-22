package site.bbichul.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.bbichul.dto.CalendarMemoDto;
import site.bbichul.dto.CalendarMemoResponseDto;
import site.bbichul.dto.CalendarDto;
import site.bbichul.models.CalendarMemo;
import site.bbichul.models.UserCalendar;
import site.bbichul.security.UserDetailsImpl;
import site.bbichul.service.CalendarService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calendars")
@Slf4j
public class CalendarController {

    private final CalendarService calendarService;

    @Operation(description = "달력 정보 가져오기", method = "GET")
    @GetMapping("/info")
    public List<UserCalendar> getCalendarInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("GET /api/calendars/info HTTP/1.1");

        String username = userDetails.getUsername();
        return calendarService.getUserInfo(username);
    }

    @Operation(description = "메모 추가하기", method = "PUT")
    @PutMapping("/calendar/memo")
    public void updateCalendarMemo(@RequestBody CalendarMemoDto calendarMemoDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        log.info("PUT /api/calendars/memo HTTP/1.1");
        calendarService.updateMemo(calendarMemoDto);
    }


    @Operation(description = "메모 불러오기", method = "GET")
    @GetMapping("/calendar/memo")
    public CalendarMemoResponseDto getMemoClickedDay(@RequestParam("id") Long calendarId, @RequestParam("date") String dateData , @AuthenticationPrincipal UserDetailsImpl userDetails){
        log.info("GET /api/calendars/memo HTTP/1.1");

        return calendarService.getMemoClickedDay(calendarId, dateData);
    }

    @Operation(description = "달력 변경하고 메모 가져오기", method = "GET")
    @GetMapping("/calendar")
    public List<CalendarMemo> getMemo(@RequestParam("id") Long calendarId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("GET /api/calendars/option HTTP/1.1");

        return calendarService.getTypeAllMemo(calendarId);
    }

    @Operation(description = "달력 추가하기", method = "POST")
    @PostMapping("/calendar")
    public String addCalendar(@RequestBody CalendarDto calendarDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("POST /api/calendars/option HTTP/1.1");

        String username = userDetails.getUsername();
        calendarService.addCalendar(calendarDto, username);
        return "캘린더가 추가되었습니다 !";
    }

    @Operation(description = "달력 삭제하기", method = "DELETE")
    @DeleteMapping("/calendar")
    public String deleteCalendar(@RequestParam("id") Long calendarId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("DELETE /api/calendars/calendar HTTP/1.1");
        calendarService.deleteCalendar(calendarId);

        return "선택한 캘린더가 삭제되었습니다.";
    }

    @Operation(description = "달력 이름 변경하기", method = "PATCH")
    @PatchMapping("/calendar")
    public String renameCalendar(@RequestBody CalendarDto calendarDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("PATCH /api/calendars/calendar HTTP/1.1");
        calendarService.renameCalendar(calendarDto);

        return "캘린더 이름이 " + calendarDto.getCalendarName() + "으로 변경되었습니다.";
    }


}