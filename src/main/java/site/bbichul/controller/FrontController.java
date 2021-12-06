package site.bbichul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontController {

    @GetMapping("/calendars")
    public String moveCalendarPage(){
        return "calendar_page";
    }

}