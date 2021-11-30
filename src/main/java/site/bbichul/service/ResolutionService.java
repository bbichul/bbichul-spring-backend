package site.bbichul.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import site.bbichul.dto.UserInfoRequestDto;
import site.bbichul.models.User;
import site.bbichul.models.UserInfo;
import site.bbichul.repository.TeamRepository;
import site.bbichul.repository.UserInfoRepository;
import site.bbichul.security.UserDetailsImpl;

@RequiredArgsConstructor
@Service
public class ResolutionService {

    private final UserInfoRepository userInfoRepository;

    public UserInfo setResolution(UserInfoRequestDto userInfoRequestDto, User user) {

        UserInfo userInfo = new UserInfo(userInfoRequestDto, user);
        userInfo.setContent(userInfoRequestDto.getContent());
        userInfoRepository.save(userInfo);
        return userInfo;
    }

//    public Team teamCheck(String username){
//        return teamRepository.findByusername(username).orElseThrow(
//                () -> new NullPointerException("")
//        )
//    }

}
