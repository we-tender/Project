package zemat.wetender.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("sessionMember", null);
        } else {
            UserDetails principalDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("sessionMember", principalDetails);
        }
        addMembers(model);
        return "fragment/main";
    }

    public void addMembers(Model model) {
        List<MemberDto> list = new ArrayList<>();
        list.add(new MemberDto("ABC1", 10));
        list.add(new MemberDto("ABC2", 20));
        list.add(new MemberDto("ABC3", 30));
        list.add(new MemberDto("ABC4", 40));
        list.add(new MemberDto("ABC5", 50));
        list.add(new MemberDto("ABC6", 60));
        list.add(new MemberDto("ABC7", 70));
        list.add(new MemberDto("ABC8", 80));
        list.add(new MemberDto("ABC9", 90));
        model.addAttribute("members", list);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberName;
        private int memberAge;

    }
}