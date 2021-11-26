package site.bbichul.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.bbichul.models.User;
import site.bbichul.repository.TeamRepository;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public User teamCheck(String username) {
        return teamRepository.findByUsername(username).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
    }

//    public Team teamCheck(String username){
//        return teamRepository.findByusername(username).orElseThrow(
//                () -> new NullPointerException("")
//        )
//    }

}
