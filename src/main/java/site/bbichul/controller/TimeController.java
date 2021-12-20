package site.bbichul.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.bbichul.dto.TimeRequestDto;
import site.bbichul.dto.UserDto;
import site.bbichul.models.Time;
import site.bbichul.security.UserDetailsImpl;
import site.bbichul.service.TimeService;
import site.bbichul.service.UserService;
import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TimeController {

    private final TimeService timeService;
    private final UserService userService;

    @Operation(description = "공부 시작시 check-in 기능", method = "POST")
    @PostMapping("/times/check-in")
    public void startStudy(@RequestBody UserDto userDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUsername();
        userService.setStudy(userDto, username);
    }

    @Operation(description = "공부시간 저장 및 check-out", method = "POST")
    @PostMapping("/times/check-out")
    public Time createTime(@RequestBody TimeRequestDto timeRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        LocalDate localDate = LocalDate.now();

        // 날짜 쪼개서 저장하기
        int year = localDate.getYear();
        timeRequestDto.setYear(year);

        int month = localDate.getMonthValue();
        timeRequestDto.setMonth(month);

        int day = localDate.getDayOfMonth();
        timeRequestDto.setDay(day);

        int weekday = localDate.getDayOfWeek().getValue();
        timeRequestDto.setWeekDay(weekday);

        int yesterdayTime = timeRequestDto.getYesterdayTime();

        Time time;
        if (yesterdayTime != 0){
             time = timeService.upsertTime(timeRequestDto, userDetails.getUser());

        }else {
             time = timeService.upsertStudy(timeRequestDto, userDetails.getUser());
        }

        return time;

    }
}