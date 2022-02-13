package zemat.wetender.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import zemat.wetender.dto.memberDto.MemberJoinForm;
import zemat.wetender.dto.memberDto.MemberLoginForm;
import zemat.wetender.service.MemberService;

import javax.servlet.http.HttpServletRequest;

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
    public String loginForm(@ModelAttribute MemberLoginForm memberLoginForm, HttpServletRequest request) {
        String prevUrl = request.getHeader("Referer");
        request.getSession().setAttribute("prevUrl", prevUrl);
        return "member/loginForm";
    }

    @GetMapping("/loginError")
    public String loginError(Model model) {
        model.addAttribute("errorMessage", "아이디/패스워드가 올바르지 않습니다.");
        return "member/loginForm";
    }

    @GetMapping("/member/list")
    public String memberList() {
        return "member/memberlist";
    }
}
