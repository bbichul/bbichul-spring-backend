package site.bbichul.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.bbichul.dto.ResolutionRequestDto;
import site.bbichul.models.UserInfo;
import site.bbichul.repository.UserInfoRepository;
import site.bbichul.security.UserDetailsImpl;
import site.bbichul.service.ResolutionService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ResolutionController {

    private final UserInfoRepository userInfoRepository;
    private final ResolutionService resolutionService;

    @Operation(description = "각오 설정", method = "PUT")
    @PutMapping("/users/resolution")
    public String updateResolution(@RequestBody ResolutionRequestDto userInfoRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("PUT /api/users/resolution HTTP/1.1");
        return resolutionService.updateResolution(userInfoRequestDto, userDetails.getUser());
    }

    @Operation(description = "각오 조회", method = "GET")
    @GetMapping("/users/resolution")
    public UserInfo getResolution(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("GET /api/users/resolution HTTP/1.1");
        return resolutionService.getResolution(userDetails.getUser());
    }


}

