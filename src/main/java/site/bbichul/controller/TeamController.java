package site.bbichul.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.bbichul.dto.TeamRequestDto;
import site.bbichul.dto.TeamTaskRequestDto;
import site.bbichul.models.TeamTask;
import site.bbichul.models.User;
import site.bbichul.security.UserDetailsImpl;
import site.bbichul.service.TeamService;

import java.util.List;

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

    @PostMapping("/team/task")
    public TeamTask addTask(@RequestBody TeamTaskRequestDto teamTaskRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return teamService.addTask(teamTaskRequestDto, userDetails.getUser());
    }

    @GetMapping("/team/task")
    public List<TeamTask> showTask(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return teamService.showTask(userDetails.getUser());
    }

    @PostMapping("/team/deletetask")
    public void deleteTask(@RequestBody TeamTaskRequestDto teamTaskRequestDto) {
        teamService.deleteTask(teamTaskRequestDto);
    }

    @PostMapping("/team/changetask")
    public void changeTask(@RequestBody TeamTaskRequestDto teamTaskRequestDto) {
        teamService.changeTask(teamTaskRequestDto);
    }

    @GetMapping("/team/status")
    public List<User> checkStatus(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return teamService.checkStatus(userDetails.getUser());
    }

    @PostMapping("/team/signup")
    public String signupTeam(@RequestBody TeamRequestDto teamRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return teamService.signupTeam(teamRequestDto, userDetails.getUser());
    }
}
