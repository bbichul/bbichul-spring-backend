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


    @PostMapping("/nickname-modal")
    public Map<String,Object> changeUsername(@RequestBody UsernameDto usernameDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return changeUsernameService.changeUsername(usernameDto, userDetails.getUser());
    }

//    @GetMapping("/nickname-modal")
//    public Map<String,Object> changeUsername(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return changeUsernameService.getUsername(userDetails.getUser());
//    }
}
