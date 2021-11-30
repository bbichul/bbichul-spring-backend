package site.bbichul.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.bbichul.dto.UserInfoRequestDto;
import site.bbichul.models.UserInfo;
//import site.bbichul.service.ResolutionService;
import site.bbichul.repository.UserInfoRepository;
import site.bbichul.security.UserDetailsImpl;
import site.bbichul.service.ResolutionService;

@RequiredArgsConstructor
@RestController
public class ResolutionController {

    private final UserInfoRepository userInfoRepository;
    private final ResolutionService resolutionService;

    @PutMapping("/resolution")
    public String updateResolution(@RequestBody UserInfoRequestDto userInfoRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return resolutionService.updateResolution(userInfoRequestDto, userDetails.getUser());

    }

    @GetMapping("/resolution")
    public UserInfo getResolution(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        return resolutionService.getResolution(userDetails.getUser());
    }


}

