package zemat.wetender.controller.suggestion;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zemat.wetender.domain.noticeBoard.NoticeStatus;
import zemat.wetender.domain.suggestion.Suggestion;
import zemat.wetender.dto.noticeBoardDto.NoticeBoardDto;
import zemat.wetender.dto.suggestionDto.SuggestionDto;
import zemat.wetender.dto.suggestionDto.SuggestionInsertDto;
import zemat.wetender.dto.suggestionDto.reply.SuggestionReplyInsertDto;
import zemat.wetender.dto.suggestionDto.SuggestionUpdateDto;
import zemat.wetender.service.MemberService;
import zemat.wetender.service.NoticeBoardService;
import zemat.wetender.service.suggestion.SuggestionService;

@Controller
@RequestMapping("/suggestion")
@RequiredArgsConstructor
public class SuggestionController {

    private final SuggestionService suggestionService;
    private final NoticeBoardService noticeBoardService;
    private final MemberService memberService;

    /*@ModelAttribute("sideMenuItems")
    public Map<String, Boolean> sideMenu() {
        Map<String, Boolean> sideMenuItems = new LinkedHashMap<>();
        sideMenuItems.put("noticeBoard", false);
        sideMenuItems.put("cocktail", false);
        sideMenuItems.put("liquor", false);
        sideMenuItems.put("community", false);
        sideMenuItems.put("suggestion", true);
        return sideMenuItems;
    }*/

    @ModelAttribute("sessionMember")
    public UserDetails getSessionMember() {
        return memberService.getSessionMember();
    }

    // ???????????? ??????????????? ??????
    @GetMapping("/main")
    public String main(Model model,
                       @PageableDefault(size = 5) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText) {

        // ?????? ??????
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), 5, Sort.by(Sort.Direction.DESC, "createdBy"));
        Page<Suggestion> suggestions = suggestionService.searchPage(searchText, pageRequest);
        Page<SuggestionDto> suggestionDtoList = suggestions.map(suggestion -> new SuggestionDto(suggestion));

        // ?????????
        int maxpage = 4;
        int startPage = Math.max(1, suggestions.getPageable().getPageNumber() - maxpage);
        int endPage = Math.min(suggestions.getTotalPages(), suggestions.getPageable().getPageNumber() + maxpage);

        if ( endPage == 0 ) {
            startPage = 0;
        }

        // ?????? ?????? ??????
        Page<NoticeBoardDto> noticeBoardAllDtos = noticeBoardService.statusFindPage(NoticeStatus.ALL, pageable);
        model.addAttribute("noticeBoardAllDtos", noticeBoardAllDtos);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("suggestionDtoList", suggestionDtoList);
        model.addAttribute("sideMenu", "nav-side-menu-suggestion");
        return "suggestion/main";

    }
    // ???????????? ??????????????? ???


    // ???????????? ??????
    @GetMapping("/insert")
    public String insertForm(Model model) {
        model.addAttribute("suggestionInsertDto", new SuggestionInsertDto());
        return "suggestion/insert";
    }

    @PostMapping("/insert")
    public String insert(@Validated  @ModelAttribute SuggestionInsertDto suggestionInsertDto,
                         BindingResult bindingResult,
                         Model model) {

        if(bindingResult.hasErrors()) {
            return "suggestion/insert";
        }

        suggestionService.insert(suggestionInsertDto);

        return "redirect:/suggestion/main";
    }
    // ???????????? ??????


    // ???????????? ??????
    @GetMapping("/update")
    public String updateForm(Model model,  @RequestParam(required = false) Long suggestionId) {


        SuggestionDto suggestionDto = suggestionService.findById(suggestionId);

        model.addAttribute("suggestionDto", suggestionDto);

        return "suggestion/update";
    }

    // ???????????? ??????
    @PostMapping("/update")
    public String update(@Validated @ModelAttribute("suggestionDto") SuggestionUpdateDto suggestionUpdateDto,
                         BindingResult bindingResult,
                         Model model) {

        if(bindingResult.hasErrors()) {
            return "suggestion/update";
        }

        suggestionService.update(suggestionUpdateDto);

        return "redirect:/suggestion/main";
    }


    // ???????????? ??????
    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) Long suggestionId) {
        suggestionService.delete(suggestionId);
        return "redirect:/suggestion/main";
    }
    // ???????????? ??????


    // ?????? ????????? ??????
    @GetMapping("/detail")
    public String detail(Model model,
                         @RequestParam(required = true) Long suggestionId,
                         @PageableDefault(size=2) Pageable pageable
    ) {
        // ????????? ??????
        SuggestionDto suggestionDto = suggestionService.findById(suggestionId);
        model.addAttribute("suggestionDto", suggestionDto);
        // ????????? ??????

        suggestionService.viewUp(suggestionId);

        Page<SuggestionDto> suggestionDtoList = suggestionService.detail_list(suggestionId, pageable);

        int startPage = Math.max(1, suggestionDtoList.getPageable().getPageNumber() - 3);
        int endPage = Math.min(suggestionDtoList.getTotalPages(), suggestionDtoList.getPageable().getPageNumber() + 3);
        if (endPage == 0) {
            startPage = 0;
        }


        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("suggestionDtoList", suggestionDtoList);

        return "suggestion/detail";
    }

}