package site.bbichul.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.bbichul.dto.GraphRequestDto;
import site.bbichul.dto.ResolutionRequestDto;
import site.bbichul.models.UserInfo;
import site.bbichul.security.UserDetailsImpl;
import site.bbichul.service.GraphService;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class GraphController {

    private final GraphService graphService;

    @PostMapping("/line-graph")
    public Map<String, Object> drawLineGraph(@RequestBody GraphRequestDto graphRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return graphService.drawLineGraph(graphRequestDto, userDetails.getUser());

    }


    @PostMapping("/bar-graph")
    public Map<String, Object> drawBarGraph(@RequestBody GraphRequestDto graphRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return graphService.drawBarGraph(graphRequestDto, userDetails.getUser());
    }
}
