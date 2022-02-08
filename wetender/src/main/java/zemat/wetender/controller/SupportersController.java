package zemat.wetender.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/supporters")
public class SupportersController {

    @GetMapping("/main")
    public String main(Model model) {

        return "supporters/main";
    }

}