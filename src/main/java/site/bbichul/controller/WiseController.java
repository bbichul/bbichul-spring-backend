package site.bbichul.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.bbichul.models.Wise;
import site.bbichul.service.WiseService;

import java.util.List;

@RestController
@AllArgsConstructor
public class WiseController {

    private final WiseService wiseService;

    @Operation(description = "명언 보여주기", method = "GET")
    @GetMapping(value = "/api/wise")
    public List<Wise> getWise(){
        return wiseService.getWises();
    }
}

