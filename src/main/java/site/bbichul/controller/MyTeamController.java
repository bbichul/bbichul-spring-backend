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
import site.bbichul.service.MyTeamService;
import site.bbichul.service.ResolutionService;

//package site.bbichul.controller;

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

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MyTeamController {

    private final MyTeamService myTeamService;

    @Operation(description = "자기 팀 조회", method = "GET")
    @GetMapping("/users/team")
    public Map<String, Object> getTeam(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("[USER : {}] Request GET /api/users/resolution HTTP/1.1", userDetails.getUsername());
        return myTeamService.getTeam(userDetails.getUser());
    }


}
