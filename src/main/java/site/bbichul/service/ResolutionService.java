package site.bbichul.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.bbichul.dto.ResolutionRequestDto;
import site.bbichul.models.User;
import site.bbichul.models.UserInfo;
import site.bbichul.repository.UserInfoRepository;
import site.bbichul.repository.UserRepository;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class ResolutionService {

    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;

    @Transactional
    public String updateResolution(ResolutionRequestDto userInfoRequestDto, User user) {

        UserInfo userInfo = userInfoRepository.findById(user.getUserInfo().getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다.")
        );
        userInfo.resolutionUpdate(userInfoRequestDto);

        return "성공";
    }
    public UserInfo getResolution(User user) {

        return userInfoRepository.findById(user.getUserInfo().getId()).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
    }
}
