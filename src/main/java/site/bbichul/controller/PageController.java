package site.bbichul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/team_page")
    public String team() {
        return "team_page";
    }
}
