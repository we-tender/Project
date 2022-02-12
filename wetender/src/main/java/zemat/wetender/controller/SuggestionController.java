package zemat.wetender.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zemat.wetender.domain.suggestion.Suggestion;
import zemat.wetender.service.SuggestionService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/suggestion")
@RequiredArgsConstructor
public class SuggestionController {

    private final SuggestionService suggestionService;

    @GetMapping("/main")
    public String main(Model model) {

        List<Suggestion> suggestionList = suggestionService.list();

        model.addAttribute("suggestionList", suggestionList);

        return "suggestion/main";
    }

    // 건의 사항 등록 시작
    @GetMapping("/insert")
    public String insert(Model model) {

        return "suggestion/insert";
    }

    @PostMapping("/insert")
    public String create(@ModelAttribute Suggestion suggestion) {

        suggestionService.insert(suggestion);

        return "redirect:/suggestion/main";
    }
    // 건의 사항 등록 끝

    // 상세 페이지 시작
    @GetMapping("/detail")
    public String detail(Model model, @RequestParam(required = true) Long suggestionId) {

        Optional<Suggestion> suggestionFind = suggestionService.find(suggestionId);
        Suggestion suggestion = suggestionFind.get();

        model.addAttribute("suggestion", suggestion);

        return "suggestion/detail";

    }
    // 상세 페이지 끝

}
