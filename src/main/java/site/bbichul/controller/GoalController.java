package site.bbichul.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.bbichul.dto.GoalRequestDto;
import site.bbichul.security.UserDetailsImpl;
import site.bbichul.service.GoalService;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class GoalController {

    private final GoalService goalService;

    @Operation(description = "개인 목표 설정", method = "PUT")
    @PutMapping("/users/goal")
    public Map<String, String> updateGoal(@RequestBody GoalRequestDto goalRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("[USER : {}] Request PUT /api/users/goal HTTP/1.1", userDetails.getUsername());
        return goalService.updateGoal(goalRequestDto, userDetails.getUser());
    }

    @Operation(description = "개인 목표 조회", method = "GET")
    @GetMapping("/users/goal")
    public Map<String,Object> getGoal(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("[USER : {}] Request GET /api/users/goal HTTP/1.1", userDetails.getUsername());
        return goalService.getGoal(userDetails.getUser());
    }
}