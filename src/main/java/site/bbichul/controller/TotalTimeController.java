package site.bbichul.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.bbichul.dto.ResolutionRequestDto;
import site.bbichul.models.TeamTask;
import site.bbichul.repository.UserInfoRepository;
import site.bbichul.security.UserDetailsImpl;
import site.bbichul.service.ResolutionService;
import site.bbichul.service.TotalTimeService;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TotalTimeController {

    private final UserInfoRepository userInfoRepository;

    private final TotalTimeService totalTimeService;

    @Operation(description = "총 공부시간 조회", method = "GET")
    @GetMapping("/users/time")
    public Map<String, Integer> getTotalTime(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return totalTimeService.getTotalTime(userDetails.getUser());
    }
}
