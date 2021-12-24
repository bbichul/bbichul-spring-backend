package site.bbichul.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SlackAlarmController {
    @GetMapping("/alarm")
    public String test() {
        log.error("Slack error alarm");
        log.info("Slack log info");
        return "test";
    }
}
