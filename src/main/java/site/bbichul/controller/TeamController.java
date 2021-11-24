//package site.bbichul.controller;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import site.bbichul.domain.Team;
//import site.bbichul.dto.TeamRequestDto;
//import site.bbichul.service.TeamService;
//
//public class TeamController {
//    private final TeamService teamService;
//
//    @GetMapping("/team/{teamname}")
//    public Team createTeam(@RequestBody TeamRequestDto requestDto) {
//        Team team = new Team(requestDto);
//        String response = teamService.teamCheck(team);
//        return response;
//    }
//}
