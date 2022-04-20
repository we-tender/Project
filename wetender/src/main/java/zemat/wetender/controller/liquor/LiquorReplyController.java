package zemat.wetender.controller.liquor;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zemat.wetender.dto.liquorDto.LiquorDto;
import zemat.wetender.dto.liquorDto.reply.LiquorReplyDeleteDto;
import zemat.wetender.dto.liquorDto.reply.LiquorReplyEditDto;
import zemat.wetender.dto.liquorDto.reply.LiquorReplyInsertDto;
import zemat.wetender.service.liquor.LiquorReplyService;
import zemat.wetender.service.MemberService;

@Slf4j
@Controller
@RequestMapping("/liquorReply")
@RequiredArgsConstructor
public class LiquorReplyController {

    private final LiquorReplyService liquorReplyService;
    private final MemberService memberService;

    @ModelAttribute("sessionMember")
    public UserDetails getSessionMember() {
        return memberService.getSessionMember();
    }

    // 댓글 등록 Ajax
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String replyInsert(Model model, LiquorReplyInsertDto dto) {
        LiquorDto liquorDto = liquorReplyService.insert(dto);

        model.addAttribute("liquorDto", liquorDto);

        return "/liquor/detail :: #reply";
    }

    // 댓글 삭제 Ajax
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String replyDelete(Model model, LiquorReplyDeleteDto dto) {
        LiquorDto liquorDto = liquorReplyService.delete(dto);
        model.addAttribute("liquorDto", liquorDto);

        return "/liquor/detail :: #reply";
    }

    // 댓글 수정 Ajax
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String replyEdit(Model model, LiquorReplyEditDto dto) {
        LiquorDto liquorDto = liquorReplyService.edit(dto);
        model.addAttribute("liquorDto", liquorDto);

        return "/liquor/detail :: #reply";
    }




}
