package site.bbichul.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import site.bbichul.dto.TeamRequestDto;
import site.bbichul.models.Team;
import site.bbichul.models.User;
import site.bbichul.repository.TeamRepository;
import site.bbichul.repository.UserRepository;
import site.bbichul.security.UserDetailsImpl;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public String teamCheck(User user) {
        if (user.getTeam() == null) {
            return "아직 소속된 팀이 없습니다.";
        }

        return user.getTeam().getTeamname();
    }

    @Transactional
    public Team createTeam(TeamRequestDto teamRequestDto, User user) {
        String teamname = teamRequestDto.getTeamname();

        Optional<Team> found = teamRepository.findByTeamname(teamname);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 팀명이 존재합니다.");
        }

        Team team = new Team(teamname);
        teamRepository.save(team);

        user.setTeam(team); // 영속성
        userRepository.save(user);
        return team;
    }

}
