package site.bbichul.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.bbichul.security.UserDetailsImpl;

@RestController
public class HomeController {

    @Operation(description = "서버 연결 확인", method = "GET")
    @GetMapping("/api/")
    public String home() {
        return "OK";
    }

}
