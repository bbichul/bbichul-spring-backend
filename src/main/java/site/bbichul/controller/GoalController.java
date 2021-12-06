package site.bbichul.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.bbichul.dto.GoalRequestDto;
import site.bbichul.security.UserDetailsImpl;
import site.bbichul.service.GoalService;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class GoalController {

    private final GoalService goalService;

    @PutMapping("/goal")
    public Map<String, String> updateGoal(@RequestBody GoalRequestDto goalRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return goalService.updateGoal(goalRequestDto, userDetails.getUser());
    }

    @GetMapping("/goal")
    public Map<String,Object> getGoal(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return goalService.getGoal(userDetails.getUser());
    }
}