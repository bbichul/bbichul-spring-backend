package site.bbichul.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.bbichul.dto.TeamRequestDto;
import site.bbichul.models.Team;
import site.bbichul.models.User;
import site.bbichul.repository.TeamRepository;
import site.bbichul.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GoalService {

    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;

    public String SetGoal(User user) {
        if (user.getTeam() == null) {
            return "아직 소속된 팀이 없습니다.";
        }

        return user.getTeam().getTeamname();
    }

}