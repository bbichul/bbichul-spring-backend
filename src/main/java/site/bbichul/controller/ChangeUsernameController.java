package site.bbichul.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.bbichul.security.UserDetailsImpl;
import site.bbichul.service.ChangeUsernameService;

import java.util.Map;


@RequiredArgsConstructor
@RestController
public class ChangeUsernameController {

    private final ChangeUsernameService changeUsernameService;

    @Operation(description = "회원 닉네임 조회", method = "GET")
    @GetMapping("/api/nickname-modal")
    public Map<String,Object> getUsername(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return changeUsernameService.getUsername(userDetails.getUser());
    }

}

