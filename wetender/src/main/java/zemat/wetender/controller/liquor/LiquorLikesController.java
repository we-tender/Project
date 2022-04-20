package zemat.wetender.controller.liquor;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.member.Member;
import zemat.wetender.dto.liquorDto.LiquorDto;
import zemat.wetender.dto.liquorDto.likes.LiquorLikesInsertOrDeleteDto;
import zemat.wetender.service.MemberService;
import zemat.wetender.service.liquor.LiquorLikesService;
import zemat.wetender.service.liquor.LiquorService;

@Controller
@RequestMapping("/liquorLikes")
@RequiredArgsConstructor
public class LiquorLikesController {

    private final LiquorService liquorService;
    private final LiquorLikesService liquorLikesService;
    private final MemberService memberService;

    @ModelAttribute("sessionMember")
    public UserDetails getSessionMember() {
        return memberService.getSessionMember();
    }

    // 좋아요 삽입, 삭제
    @RequestMapping(value = "/InsertOrDelete", method = RequestMethod.POST)
    public String insertOrDeleteAjax(Model model, LiquorLikesInsertOrDeleteDto dto) {

        Member member = memberService.findByMemberIdName(dto.getMemberName());
        Liquor liquor = liquorService.findById(dto.getLiquorId());

        LiquorDto liquorDto = liquorLikesService.insertOrDelete(dto);
        
        boolean likesCheck = liquorLikesService.likesCheck(member, liquor);

        model.addAttribute("likesCheck", likesCheck);
        model.addAttribute("liquorDto", liquorDto);

        return "/liquor/detail :: #likes";
    }


}
