package site.bbichul.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.bbichul.dto.TimeRequestDto;
import site.bbichul.models.Time;
import site.bbichul.security.UserDetailsImpl;
import site.bbichul.service.TimeService;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
public class TimeController {

    private final TimeService timeService;

    @PostMapping("/time")
    public Time createTime(@RequestBody TimeRequestDto timeRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        // 로그인이 되어 있는 ID
        Long userId = userDetails.getUser().getId();
        LocalDate localDate = LocalDate.now();


        int year = localDate.getYear();
        timeRequestDto.setYear(year);

        int month = localDate.getMonthValue();
        timeRequestDto.setMonth(month);

        int day = localDate.getDayOfMonth();
        timeRequestDto.setDay(day);

        int weekday = localDate.getDayOfWeek().getValue();
        timeRequestDto.setWeekday(weekday);

        Time time = timeService.createTime(timeRequestDto, userId);

        return time;


    }
}