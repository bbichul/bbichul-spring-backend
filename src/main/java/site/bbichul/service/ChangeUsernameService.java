package site.bbichul.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.bbichul.dto.GoalRequestDto;
import site.bbichul.dto.UserDto;
import site.bbichul.dto.UsernameDto;
import site.bbichul.models.User;
import site.bbichul.models.UserInfo;
import site.bbichul.repository.TimeRepository;
import site.bbichul.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ChangeUsernameService {
    private final UserRepository userRepository;

    @Transactional
    public Map<String, Object> changeUsername(UsernameDto usernameDto, User user) {

        if (userRepository.findByUsername(usernameDto.getUsername()).isPresent()) {
            Map<String, Object> map = new HashMap();	//<키 자료형, 값 자료형>
            map.put("msg", "중복된 닉네임");
            return map;
        }

        user.usernameUpdate(usernameDto);


        Map<String, Object> map = new HashMap();	//<키 자료형, 값 자료형>
        map.put("msg", "성공");

        return map;
    }
}
