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
                () -> new IllegalArgumentException("INVALID USER")
        );
        userInfo.resolutionUpdate(userInfoRequestDto);

        return "SUCCESS";
    }
    public UserInfo getResolution(User user) {

        return userInfoRepository.findById(user.getUserInfo().getId()).orElseThrow(
                () -> new NullPointerException("INVALID USER")
        );
    }
}
