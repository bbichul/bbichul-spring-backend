package site.bbichul.security;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.bbichul.models.User;
import site.bbichul.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 로그인 닉네임 있는 확인
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + username));
        // status 까지 판별
        User user1 = userRepository.findByUsernameAndStatus(username, true).orElseThrow(
                () -> new UsernameNotFoundException("아이다가 탈퇴 상태 입니다")
        );

        return new UserDetailsImpl(user1);

    }
}
