package site.bbichul.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.bbichul.dto.TeamProgressbarResponseDto;
import site.bbichul.dto.TeamRequestDto;
import site.bbichul.dto.TeamTaskRequestDto;
import site.bbichul.models.Team;
import site.bbichul.models.TeamTask;
import site.bbichul.models.User;
import site.bbichul.security.UserDetailsImpl;
import site.bbichul.service.TeamService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/teams")
    public String checkTeam(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return teamService.checkTeam(userDetails.getUser());
    }

    @PostMapping("/teams")
    public String createTeam(@RequestBody TeamRequestDto teamRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return teamService.createTeam(teamRequestDto, userDetails.getUser());
    }

    @PostMapping("/teams/task")
    public TeamTask addTask(@RequestBody TeamTaskRequestDto teamTaskRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return teamService.addTask(teamTaskRequestDto, userDetails.getUser());
    }

    @GetMapping("/teams/task")
    public List<TeamTask> showTask(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return teamService.showTask(userDetails.getUser());
    }

    @DeleteMapping("/teams/task")
    public void deleteTask(@RequestBody TeamTaskRequestDto teamTaskRequestDto) {
        teamService.deleteTask(teamTaskRequestDto);
    }

    @PutMapping("/teams/task")
    public void changeTask(@RequestBody TeamTaskRequestDto teamTaskRequestDto) {
        teamService.changeTask(teamTaskRequestDto);
    }

    @GetMapping("/teams/status")
    public List<User> checkStatus(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return teamService.checkStatus(userDetails.getUser());
    }

    @PostMapping("/teams/signup")
    public String signupTeam(@RequestBody TeamRequestDto teamRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return teamService.signupTeam(teamRequestDto, userDetails.getUser());
    }

    @PostMapping("/teams/checkname")
    public String checkName(@RequestBody TeamRequestDto teamRequestDto) {
        return teamService.checkName(teamRequestDto);
    }

    @PostMapping("/teams/task/graph")
    public TeamProgressbarResponseDto getTeamProgressbar(TeamProgressbarResponseDto teamProgressbarResponseDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return teamService.getTeamProgressbar(teamProgressbarResponseDto, userDetails.getUser());
    }
}
