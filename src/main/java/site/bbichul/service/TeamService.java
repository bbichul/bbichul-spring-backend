package site.bbichul.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.bbichul.dto.UserDto;
import site.bbichul.models.Team;
import site.bbichul.repository.TeamRepository;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public Team teamCheck(String username){
        return teamRepository.findByusername(username).orElseThrow(
                () -> new NullPointerException("")
        )
    }

}
