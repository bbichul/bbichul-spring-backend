package site.bbichul.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.bbichul.dto.TeamProgressbarResponseDto;
import site.bbichul.dto.TeamRequestDto;
import site.bbichul.dto.TeamTaskRequestDto;
import site.bbichul.models.TeamTask;
import site.bbichul.models.User;
import site.bbichul.security.UserDetailsImpl;
import site.bbichul.service.TeamService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TeamController {

    private final TeamService teamService;

    @Operation(description = "팀 소속 여부 확인", method = "GET")
    @GetMapping("/teams")
    public String checkTeam(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return teamService.checkTeam(userDetails.getUser());
    }

    @Operation(description = "팀 만들기", method = "POST")
    @PostMapping("/teams")
    public String createTeam(@RequestBody TeamRequestDto teamRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return teamService.createTeam(teamRequestDto, userDetails.getUser());
    }

    @Operation(description = "to do list의 task 저장", method = "POST")
    @PostMapping("/teams/task")
    public TeamTask addTask(@RequestBody TeamTaskRequestDto teamTaskRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return teamService.addTask(teamTaskRequestDto, userDetails.getUser());
    }

    @Operation(description = "to do list의 task 내용 변경", method = "PUT")
    @PutMapping("/teams/task")
    public void updateTask(@RequestBody TeamTaskRequestDto teamTaskRequestDto) {
        teamService.updateTask(teamTaskRequestDto);
    }

    @Operation(description = "to do list의 task 조회", method = "GET")
    @GetMapping("/teams/task")
    public List<TeamTask> showTask(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return teamService.showTask(userDetails.getUser());
    }

    @Operation(description = "to do list의 task 삭제", method = "DELETE")
    @DeleteMapping("/teams/task")
    public void deleteTask(@RequestBody TeamTaskRequestDto teamTaskRequestDto) {
        teamService.deleteTask(teamTaskRequestDto);
    }

    @Operation(description = "to do list의 task 상태 변경", method = "PUT")
    @PutMapping("/teams/tasks/status")
    public void changeTask(@RequestBody TeamTaskRequestDto teamTaskRequestDto) {
        teamService.changeTask(teamTaskRequestDto);
    }

    @Operation(description = "팀원 출결 현황 조회", method = "GET")
    @GetMapping("/teams/status")
    public List<User> checkStatus(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return teamService.checkStatus(userDetails.getUser());
    }

    @Operation(description = "팀 가입", method = "POST")
    @PostMapping("/teams/signup")
    public String signupTeam(@RequestBody TeamRequestDto teamRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return teamService.signupTeam(teamRequestDto, userDetails.getUser());
    }

    @Operation(description = "팀명 중복 조회", method = "POST")
    @PostMapping("/teams/checkname")
    public String checkName(@RequestBody TeamRequestDto teamRequestDto) {
        return teamService.checkName(teamRequestDto);
    }

    @Operation(description = "to do list의 전체 진행상황 그래프 조회", method = "POST")
    @PostMapping("/teams/task/graph")
    public TeamProgressbarResponseDto getTeamProgressbar(TeamProgressbarResponseDto teamProgressbarResponseDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return teamService.getTeamProgressbar(teamProgressbarResponseDto, userDetails.getUser());
    }
}