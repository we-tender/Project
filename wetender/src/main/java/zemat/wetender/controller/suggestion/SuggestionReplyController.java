package zemat.wetender.controller.suggestion;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zemat.wetender.dto.cocktailDto.CocktailMainDto;
import zemat.wetender.dto.cocktailDto.reply.CocktailReplyDeleteDto;
import zemat.wetender.dto.cocktailDto.reply.CocktailReplyEditDto;
import zemat.wetender.dto.cocktailDto.reply.CocktailReplyInsertDto;
import zemat.wetender.dto.suggestionDto.SuggestionDto;
import zemat.wetender.dto.suggestionDto.reply.SuggestionReplyDeleteDto;
import zemat.wetender.dto.suggestionDto.reply.SuggestionReplyEditDto;
import zemat.wetender.dto.suggestionDto.reply.SuggestionReplyInsertDto;
import zemat.wetender.service.MemberService;
import zemat.wetender.service.cocktail.CocktailReplyService;
import zemat.wetender.service.cocktail.CocktailService;
import zemat.wetender.service.suggestion.SuggestionReplyService;
import zemat.wetender.service.suggestion.SuggestionService;

@Controller
@RequestMapping("/suggestionReply")
@RequiredArgsConstructor
public class SuggestionReplyController {

    private final SuggestionService suggestionService;
    private final SuggestionReplyService suggestionReplyService;
    private final MemberService memberService;

    // 댓글 등록 Ajax
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String replyInsert(Model model, SuggestionReplyInsertDto dto) {
        SuggestionDto suggestionDto = suggestionReplyService.insert(dto);
        model.addAttribute("suggestionDto", suggestionDto);
        model.addAttribute("sessionMember", memberService.getSessionMember());

        return "/suggestion/detail :: #reply";
    }

    // 댓글 삭제 Ajax
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String replyDelete(Model model, SuggestionReplyDeleteDto dto) {
        SuggestionDto suggestionDto = suggestionReplyService.delete(dto);
        model.addAttribute("suggestionDto", suggestionDto);
        model.addAttribute("sessionMember", memberService.getSessionMember());

        return "/suggestion/detail :: #reply";
    }

    // 댓글 수정 Ajax
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String replyEdit(Model model, SuggestionReplyEditDto dto) {
        SuggestionDto suggestionDto = suggestionReplyService.edit(dto);
        model.addAttribute("suggestionDto", suggestionDto);
        model.addAttribute("sessionMember", memberService.getSessionMember());
        return "/suggestion/detail :: #reply";
    }
}
