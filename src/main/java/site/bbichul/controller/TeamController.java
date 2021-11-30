package site.bbichul.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.bbichul.dto.TeamRequestDto;
import site.bbichul.security.UserDetailsImpl;
import site.bbichul.service.TeamService;

@RequiredArgsConstructor
@RestController
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/team")
    public String teamCheck(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return teamService.teamCheck(userDetails.getUser());
    }

    @PostMapping("/team")
    public String createTeam(@RequestBody TeamRequestDto teamRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return teamService.createTeam(teamRequestDto, userDetails.getUser());
    }
}
