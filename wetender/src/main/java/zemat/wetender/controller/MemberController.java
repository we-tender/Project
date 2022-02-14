package zemat.wetender.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    public String loginForm(@ModelAttribute MemberLoginForm memberLoginForm, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        String prevUrl = request.getHeader("Referer");
        request.getSession().setAttribute("prevUrl", prevUrl);
        return "member/loginForm";
    }

    @PostMapping("/loginError")
    public String loginError(@ModelAttribute MemberLoginForm memberLoginForm, Model model) {
        model.addAttribute("errorMessage", "아이디/패스워드가 올바르지 않습니다.");
        return "member/loginForm";
    }

    @GetMapping("/member/mypage")
    public String myPage(Model model) {
        MainController.getSessionMember(model);
        return "member/myPage";
    }

    @GetMapping("/member/list")
    public String memberList() {
        return "member/memberlist";
    }
}
