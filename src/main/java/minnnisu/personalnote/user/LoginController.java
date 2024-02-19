package minnnisu.personalnote.user;

import minnnisu.personalnote.constant.AuthenticationType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "failure", required = false) String failure,
                            Model model) {
        if(failure != null) {
            model.addAttribute("failure", AuthenticationType.valueOf(failure).getErrorMessage());
        }

        return "login";
    }
}