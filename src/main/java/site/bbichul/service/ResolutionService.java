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
import site.bbichul.repository.UserRepository;
import site.bbichul.security.UserDetailsImpl;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class ResolutionService {

    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;

    @Transactional
    public String updateResolution(UserInfoRequestDto userInfoRequestDto, User user) {

        UserInfo userInfo = userInfoRepository.findById(user.getUserInfo().getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다.")
        );
        userInfo.update(userInfoRequestDto);

        return "성공";
    }
    public UserInfo getResolution(User user) {

        return userInfoRepository.findById(user.getUserInfo().getId()).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
    }
}
