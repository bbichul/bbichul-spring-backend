package site.bbichul.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import site.bbichul.models.User;
import site.bbichul.service.TeamService;

@RequiredArgsConstructor
@RestController
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/team_page/{username}")
    public User teamCheck(@PathVariable String username) {
        System.out.println(username);
        return teamService.teamCheck(username);

//    @GetMapping("/team/{teamname}")
//    public Team createTeam(@RequestBody TeamRequestDto requestDto) {
//        Team team = new Team(requestDto);
//        String response = teamService.teamCheck(team);
//        return response;
    }
}
