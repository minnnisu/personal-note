package minnnisu.personalnote.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController {
    @GetMapping
    public String signup() {
        log.info("GET /signup");
        return "signup";
    }
}
