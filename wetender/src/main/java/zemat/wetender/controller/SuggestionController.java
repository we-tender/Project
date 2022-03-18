package zemat.wetender.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zemat.wetender.domain.suggestion.Suggestion;
import zemat.wetender.domain.suggestion.SuggestionReply;
import zemat.wetender.dto.suggestionDto.SuggestionDto;
import zemat.wetender.dto.suggestionDto.SuggestionInsertDto;
import zemat.wetender.dto.suggestionDto.SuggestionReplyInsertDto;
import zemat.wetender.service.MemberService;
import zemat.wetender.service.SuggestionService;

import java.util.*;

@Controller
@RequestMapping("/suggestion")
@RequiredArgsConstructor
public class SuggestionController {

    private final SuggestionService suggestionService;
    private final MemberService memberService;

    @ModelAttribute("sideMenuItems")
    public Map<String, Boolean> sideMenu() {
        Map<String, Boolean> sideMenuItems = new LinkedHashMap<>();
        sideMenuItems.put("noticeBoard", false);
        sideMenuItems.put("cocktail", false);
        sideMenuItems.put("liquor", false);
        sideMenuItems.put("community", false);
        sideMenuItems.put("suggestion", true);
        return sideMenuItems;
    }

    // 건의사항 메인페이지 시작
    @GetMapping("/main")
    public String main(Model model,
                       @PageableDefault(size = 10) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText) {

        // 검색 기능
        Page<Suggestion> suggestions = suggestionService.searchPage(searchText, pageable);
        Page<SuggestionDto> suggestionDtos = suggestions.map(suggestion -> new SuggestionDto(suggestion));

        // 페이지
        int maxpage = 4;
        int startPage = Math.max(1, suggestions.getPageable().getPageNumber() - maxpage);
        int endPage = Math.min(suggestions.getTotalPages(), suggestions.getPageable().getPageNumber() + maxpage);

        if ( endPage == 0 ) {
            startPage = 0;
        }

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("suggestionDtos", suggestionDtos);
        model.addAttribute("sessionMember", memberService.getSessionMember());
        return "suggestion/main";

    }
    // 건의사항 메인페이지 끝


    // 건의사항 등록, 수정
    @GetMapping("/insert")
    public String insertForm(Model model, @RequestParam(required = false) Long id) {

        if (id == null) {
            Suggestion suggestion = new Suggestion("", "");
            model.addAttribute("suggestionDto", new SuggestionDto(suggestion));
        } else {
            Suggestion suggestion = suggestionService.findById(id).orElse(null);
            SuggestionDto suggestionDto = new SuggestionDto(suggestion);
            model.addAttribute("suggestionDto", suggestionDto);
        }

        model.addAttribute("sessionMember", memberService.getSessionMember());
        return "suggestion/insert";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute SuggestionInsertDto suggestionInsertDto) {
        suggestionService.insert(suggestionInsertDto);
        return "redirect:/suggestion/main";
    }
    // 건의사항 등록, 수정 끝


    // 건의사항 삭제
    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) Long id) {
        suggestionService.delete(id);
        return "redirect:/suggestion/main";
    }
    // 건의사항 삭제


    // 상세 페이지 시작
    @GetMapping("/detail")
    public String detail(Model model,
                         @RequestParam(required = true) Long suggestionId
    ) {
        // 게시글 기능
        Optional<Suggestion> suggestionFind = suggestionService.findById(suggestionId);
        Suggestion suggestion = suggestionFind.get();
        SuggestionDto suggestionDto = new SuggestionDto(suggestion);

        model.addAttribute("suggestionDto", suggestionDto);
        // 게시글 기능

        // 게시판 기능
        List<Suggestion> suggestions = suggestionService.detail_list(suggestionId);
        List<SuggestionDto> suggestionDtos = new ArrayList<>();
        for (Suggestion suggestion1 : suggestions) {
            suggestionDtos.add(new SuggestionDto(suggestion1));
        }
        model.addAttribute("suggestionDtos", suggestionDtos);
        // 게시판 기능

        model.addAttribute("sessionMember", memberService.getSessionMember());
        return "suggestion/detail";
    }

    // 건의사항 댓글 등록
    @PostMapping("/replyInsert")
    public String replyInsert(@ModelAttribute SuggestionReplyInsertDto suggestionReplyInsertDto) {
        Long id = suggestionService.replyInsert(suggestionReplyInsertDto);
        return "redirect:/suggestion/detail?suggestionId=" + id;
    }



}