package zemat.wetender.controller.cocktail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zemat.wetender.dto.cocktailDto.CocktailMainDto;
import zemat.wetender.dto.cocktailDto.reply.CocktailReplyDeleteDto;
import zemat.wetender.dto.cocktailDto.reply.CocktailReplyEditDto;
import zemat.wetender.dto.cocktailDto.reply.CocktailReplyInsertDto;
import zemat.wetender.dto.liquorDto.LiquorDto;
import zemat.wetender.dto.liquorDto.reply.LiquorReplyDeleteDto;
import zemat.wetender.dto.liquorDto.reply.LiquorReplyEditDto;
import zemat.wetender.dto.liquorDto.reply.LiquorReplyInsertDto;
import zemat.wetender.service.MemberService;
import zemat.wetender.service.cocktail.CocktailReplyService;
import zemat.wetender.service.cocktail.CocktailService;

@Controller
@RequestMapping("/cocktailReply")
@RequiredArgsConstructor
public class CocktailReplyController {

    private final CocktailService cocktailService;
    private final CocktailReplyService cocktailReplyService;
    private final MemberService memberService;

    // 댓글 등록 Ajax
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String replyInsert(Model model, CocktailReplyInsertDto dto) {

        CocktailMainDto cocktailMainDto = cocktailReplyService.insert(dto);

        model.addAttribute("cocktailDto", cocktailMainDto);
        model.addAttribute("sessionMember", memberService.getSessionMember());

        return "/cocktail/detail :: #reply";
    }

    // 댓글 삭제 Ajax
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String replyDelete(Model model, CocktailReplyDeleteDto dto) {
        CocktailMainDto cocktailMainDto = cocktailReplyService.delete(dto);
        model.addAttribute("cocktailDto", cocktailMainDto);
        model.addAttribute("sessionMember", memberService.getSessionMember());

        return "/cocktail/detail :: #reply";
    }

    // 댓글 수정 Ajax
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String replyEdit(Model model, CocktailReplyEditDto dto) {
        CocktailMainDto cocktailMainDto = cocktailReplyService.edit(dto);
        model.addAttribute("cocktailDto", cocktailMainDto);
        model.addAttribute("sessionMember", memberService.getSessionMember());

        return "/cocktail/detail :: #reply";
    }


}
