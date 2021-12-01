package site.bbichul.controller;

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

    // 명언 뿌려주기
    @GetMapping(value = "/wise")
    public List<Wise> getWise(){
        return wiseService.getWises();
    }
}
