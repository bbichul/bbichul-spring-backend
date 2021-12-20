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

// UserDetailsService : DB에 저장된 회원의 아이디, 비밀번호와 비교함
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + username));

        User user1 = userRepository.findByUsernameAndStatus(username, false).orElseThrow(
                () -> new UsernameNotFoundException("아이다가 탈퇴 상태 입니다")
        );

        return new UserDetailsImpl(user1);

    }
}


