package site.bbichul.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import site.bbichul.dto.SignupRequestDto;
import site.bbichul.dto.UserDto;
import site.bbichul.models.Team;
import site.bbichul.models.User;
import site.bbichul.models.UserInfo;
import site.bbichul.models.UserRole;
import site.bbichul.repository.UserInfoRepository;
import site.bbichul.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";
    private final PasswordEncoder passwordEncoder;

    public User registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();

        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }

        // 패스워드 인코딩
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 사용자 ROLE 확인
        UserRole role = UserRole.USER;
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRole.ADMIN;
        }

        UserInfo userInfo = new UserInfo();
        userInfoRepository.save(userInfo);

        User user = new User(username, password, role, userInfo);
        userRepository.save(user);

        return user;
    }
    // 공부 중 유무 체크
    @Transactional
    public void setStudy(UserDto userDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new NullPointerException("그럴리가 없쥬")
        );
        user.updateStudy(userDto);
    }

    // 닉네임 중복 체크
    public String checkUser(UserDto userDto){
        String username = userDto.getUsername();
        String message;
        Optional<User> name = userRepository.findByUsername(username);
        if(name.isPresent()){
            message = "중복되는 닉네임입니다. 다시 입력해주세요.";
        }else {
            message = "사용할 수 있는 닉네임입니다.";
        }
        return message;
    }

    // 회원 상태 체크
    @Transactional
    public void setStatus(UserDto userDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new NullPointerException("그럴리가 없쥬")
        );

        if (user.getTeam() != null) {
            user.setTeam(null);
        }
        user.updateStatus(userDto);
    }

}
