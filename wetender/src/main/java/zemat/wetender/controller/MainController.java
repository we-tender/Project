package zemat.wetender.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        if (authentication != null) {
            UserDetails memberDetail = (UserDetails) authentication.getPrincipal();
            model.addAttribute("sessionMember", memberDetail);
        } else {
            model.addAttribute("sessionMember", null);
        }
        return "fragment/main";
    }


}