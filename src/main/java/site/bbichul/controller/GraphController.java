package site.bbichul.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.bbichul.security.UserDetailsImpl;
import site.bbichul.service.GraphService;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class GraphController {

    private final GraphService graphService;

    @Operation(description = "공부시간 그래프 조회", method = "GET")
    @GetMapping("/api/users/graph")
    public Map<String, Object> drawLineGraph(@RequestParam String type,@RequestParam Integer year, @RequestParam Integer month, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("[USER : {}] Request GET /api/users/graph HTTP/1.1", userDetails.getUsername());
        return graphService.drawLineGraph(type, year, month, userDetails.getUser());

    }

}
