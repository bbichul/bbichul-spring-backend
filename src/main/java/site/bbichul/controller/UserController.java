package site.bbichul.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import site.bbichul.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    // 회원 로그인 페이지
//    @GetMapping("/user/login")
//    public String login() {
//        return "index";
//    }


    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(String code) {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        userService.kakaoLogin(code);

        return "redirect:/";
    }
}
