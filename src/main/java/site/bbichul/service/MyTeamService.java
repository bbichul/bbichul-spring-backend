package site.bbichul.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.bbichul.dto.ResolutionRequestDto;
import site.bbichul.models.User;
import site.bbichul.models.UserInfo;
import site.bbichul.repository.TeamRepository;
import site.bbichul.repository.UserInfoRepository;
import site.bbichul.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MyTeamService {

    private final UserRepository userRepository;

    public Map<String, Object> getTeam(User user) {
//        User user1 = userRepository.findById(user.getId()).orElseThrow(
//                () -> new NullPointerException("INVALID USER")
//        );
        String myTeam = "";
        try {
            myTeam = user.getTeam().getTeamname();
        } catch (NullPointerException e) {
            myTeam = "";
        }

        Map<String, Object> map = new HashMap();    //<키 자료형, 값 자료형>
        map.put("myTeam", myTeam);
        System.out.println(map);
        return map;
    }
}