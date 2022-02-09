package zemat.wetender.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import zemat.wetender.dto.memberDto.MemberJoinForm;
import zemat.wetender.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/joinForm")
    public String joinForm(@ModelAttribute MemberJoinForm memberJoinForm) {
        return "member/joinForm";
    }

    @PostMapping("/member/join")
    public String join(@ModelAttribute MemberJoinForm memberJoinForm) {
        System.out.println("form = " + memberJoinForm);
        memberService.join(memberJoinForm);
        return "redirect:/";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "member/loginForm";
    }

    @GetMapping("/member/list")
    public String memberList() {
        return "member/memberlist";
    }
}
