package zemat.wetender.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.liquor.LiquorReply;
import zemat.wetender.dto.liquorDto.LiquorDto;
import zemat.wetender.dto.liquorDto.reply.LiquorReplyDeleteDto;
import zemat.wetender.dto.liquorDto.reply.LiquorReplyEditDto;
import zemat.wetender.dto.liquorDto.reply.LiquorReplyInsertDto;
import zemat.wetender.service.LiquorReplyService;
import zemat.wetender.service.MemberService;

@Slf4j
@Controller
@RequestMapping("/liquorReply")
@RequiredArgsConstructor
public class LiquorReplyController {

    private final LiquorReplyService liquorReplyService;
    private final MemberService memberService;

    // 댓글 등록 Ajax
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String replyInsert(Model model, LiquorReplyInsertDto Dto) {

        log.info("Dto.getLiquorId() = {}", Dto.getLiquorId());

        LiquorDto liquorDto = liquorReplyService.insert(Dto);

        model.addAttribute("liquorDto", liquorDto);
        model.addAttribute("sessionMember", memberService.getSessionMember());


        return "/liquor/detail :: #reply";
    }

    // 댓글 삭제 Ajax
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String replyDelete(Model model, LiquorReplyDeleteDto Dto) {
        LiquorDto liquorDto = liquorReplyService.delete(Dto);
        model.addAttribute("liquorDto", liquorDto);
        model.addAttribute("sessionMember", memberService.getSessionMember());

        return "/liquor/detail :: #reply";
    }

    // 댓글 수정 Ajax
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String replyEdit(Model model, LiquorReplyEditDto Dto) {
        LiquorDto liquorDto = liquorReplyService.edit(Dto);
        model.addAttribute("liquorDto", liquorDto);
        model.addAttribute("sessionMember", memberService.getSessionMember());

        return "/liquor/detail :: #reply";
    }




}
