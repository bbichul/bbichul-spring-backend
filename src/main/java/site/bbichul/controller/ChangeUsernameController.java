package site.bbichul.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.bbichul.dto.UserDto;
import site.bbichul.dto.UsernameDto;
import site.bbichul.security.UserDetailsImpl;
import site.bbichul.service.ChangeUsernameService;

import java.util.Map;


@RequiredArgsConstructor
@RestController
public class ChangeUsernameController {

    private final ChangeUsernameService changeUsernameService;


    @GetMapping("/nickname-modal")
    public Map<String,Object> getUsername(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return changeUsernameService.getUsername(userDetails.getUser());
    }

}
