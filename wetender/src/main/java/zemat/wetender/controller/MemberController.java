package zemat.wetender.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.domain.cocktail.CocktailLikes;
import zemat.wetender.domain.member.Member;
import zemat.wetender.dto.cocktailDto.CocktailMainDto;
import zemat.wetender.dto.liquorDto.LiquorDto;
import zemat.wetender.dto.memberDto.MemberDto;
import zemat.wetender.dto.memberDto.MemberJoinForm;
import zemat.wetender.dto.memberDto.MemberLoginForm;
import zemat.wetender.service.MemberService;
import zemat.wetender.service.cocktail.CocktailLikesService;
import zemat.wetender.service.cocktail.CocktailService;
import zemat.wetender.service.liquor.LiquorLikesService;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final CocktailLikesService cocktailLikesService;
    private final LiquorLikesService liquorLikesService;
    private final MemberService memberService;


    @ModelAttribute("sessionMember")
    public UserDetails getSessionMember() {
        return memberService.getSessionMember();
    }

    @Value("${cocktailFile.dir}")
    private String cocktailFileDir;

    @Value("${liquorFile.dir}")
    private String liquorFileDir;


    @GetMapping("/joinForm")
    public String joinForm(@ModelAttribute MemberJoinForm memberJoinForm) {
        return "member/joinForm";
    }

    @PostMapping("/member/join")
    public String join(@Validated  @ModelAttribute MemberJoinForm memberJoinForm, BindingResult bindingResult) {
        System.out.println("form = " + memberJoinForm);

        // 에러인 경우 원래 페이지
        if(bindingResult.hasErrors()) {
            return "member/joinForm";
        }

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
    public String loginError(@Validated @ModelAttribute MemberLoginForm memberLoginForm,
                             BindingResult bindingResult,
                             Model model) {

        if(bindingResult.hasErrors()) {
            return "member/loginForm";
        }

        model.addAttribute("errorMessage", "아이디/패스워드가 올바르지 않습니다.");
        return "member/loginForm";
    }

    @GetMapping("/member/mypage")
    public String myPage(Model model) {
        Member findMember = memberService.findByMemberIdName(getSessionMember().getUsername());
        model.addAttribute("member", findMember);

        return "member/myPage";
    }

    @GetMapping("/member/list")
    public String memberList(Model model) {
        return "member/memberlist";
    }

    // 멤버 좋아요 페이지
    @GetMapping("/member/likesPage")
    public String likesPage(Model model,
                            @RequestParam(required = true) Long memberId,
                            @PageableDefault(size = 5) Pageable pageable
                            ) {

        Member member = memberService.findByMemberId(memberId);
        MemberDto memberDto = new MemberDto(member);

        // 칵테일
        Page<CocktailMainDto> cocktailDtos = cocktailLikesService.findMemberId(memberId, pageable);
        int cocktailStartPage = Math.max(1, cocktailDtos.getPageable().getPageNumber() - 4);
        int cocktailEndPage = Math.min(cocktailDtos.getTotalPages(), cocktailDtos.getPageable().getPageNumber() + 4);
        if(cocktailEndPage == 0) cocktailStartPage = 0;


        // 주류
        Page<LiquorDto> liquorDtos = liquorLikesService.findMemberId(memberId, pageable);
        int liquorStartPage = Math.max(1, liquorDtos.getPageable().getPageNumber() - 4);
        int liquorEndPage = Math.min(liquorDtos.getTotalPages(), liquorDtos.getPageable().getPageNumber() + 4);
        if(liquorEndPage == 0) liquorStartPage = 0;



        model.addAttribute("memberDto", memberDto);

        model.addAttribute("cocktailDtos", cocktailDtos);
        model.addAttribute("cocktailStartPage", cocktailStartPage);
        model.addAttribute("cocktailEndPage", cocktailEndPage);

        model.addAttribute("liquorDtos", liquorDtos);
        model.addAttribute("liquorStartPage", liquorStartPage);
        model.addAttribute("liquorEndPage", liquorEndPage);

        model.addAttribute("sessionMember", memberService.getSessionMember());

        return "member/likesPage";
    }

    // 칵테일 이미지 보이게 설정
    @ResponseBody
    @GetMapping("/cocktailLikesImages/{year}/{month}/{day}/{filename}")
    public Resource cocktailDownloadImage(@PathVariable String year,
                                          @PathVariable String month,
                                          @PathVariable String day,
                                          @PathVariable String filename) throws MalformedURLException {

        return new UrlResource("file:" + cocktailFileDir + year + "/"+ month + "/" + day + "/" + filename);
    }

    // 주류 이미지 보이게 설정
    @ResponseBody
    @GetMapping("/liquorLikesImages/{year}/{month}/{day}/{filename}")
    public Resource liquorDownloadImage(@PathVariable String year,
                                        @PathVariable String month,
                                        @PathVariable String day,
                                        @PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + liquorFileDir + year + "/"+ month + "/" + day + "/" + filename);
    }


    // 칵테일 좋아요 페이지 이동 Ajax
    @RequestMapping(value = "/member/cocktailMovePage", method = RequestMethod.GET)
    public String cocktailMovePage(Model model,
                           @PageableDefault(size = 5) Pageable pageable,
                           Long memberId) {

        Member member = memberService.findByMemberId(memberId);
        MemberDto memberDto = new MemberDto(member);

        // 칵테일
        Page<CocktailMainDto> cocktailDtos = cocktailLikesService.findMemberId(memberId, pageable);
        int cocktailStartPage = Math.max(1, cocktailDtos.getPageable().getPageNumber() - 4);
        int cocktailEndPage = Math.min(cocktailDtos.getTotalPages(), cocktailDtos.getPageable().getPageNumber() + 4);
        if(cocktailEndPage == 0) cocktailStartPage = 0;


        model.addAttribute("memberDto", memberDto);
        model.addAttribute("cocktailDtos", cocktailDtos);
        model.addAttribute("cocktailStartPage", cocktailStartPage);
        model.addAttribute("cocktailEndPage", cocktailEndPage);
        model.addAttribute("sessionMember", memberService.getSessionMember());

        return "/member/likesPage :: #cocktailLikes";
    }


    // 주류 좋아요 페이지 이동 Ajax
    @RequestMapping(value = "/member/liquorMovePage", method = RequestMethod.GET)
    public String liquorMovePage(Model model,
                           @PageableDefault(size = 5) Pageable pageable,
                           Long memberId) {

        Member member = memberService.findByMemberId(memberId);
        MemberDto memberDto = new MemberDto(member);

        // 주류
        Page<LiquorDto> liquorDtos = liquorLikesService.findMemberId(memberId, pageable);
        int liquorStartPage = Math.max(1, liquorDtos.getPageable().getPageNumber() - 4);
        int liquorEndPage = Math.min(liquorDtos.getTotalPages(), liquorDtos.getPageable().getPageNumber() + 4);
        if(liquorEndPage == 0) liquorStartPage = 0;



        model.addAttribute("memberDto", memberDto);
        model.addAttribute("liquorDtos", liquorDtos);
        model.addAttribute("liquorStartPage", liquorStartPage);
        model.addAttribute("liquorEndPage", liquorEndPage);
        model.addAttribute("sessionMember", memberService.getSessionMember());

        return "/member/likesPage :: #liquorLikes";
    }



}
