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

        List<Suggestion> suggestionList = suggestionService.findAll();

        model.addAttribute("suggestionList", suggestionList);

        return "suggestion/main";
    }


    // 건의사항 등록, 수정
    @GetMapping("/insert")
    public String insertForm(Model model, @RequestParam(required = false) Long id) {

        if(id == null)
        {
            model.addAttribute("suggestion", new Suggestion("", ""));
        }
        else
        {
            Suggestion suggestion = suggestionService.findById(id).orElse(null);
            model.addAttribute("suggestion", suggestion);
        }


        return "suggestion/insert";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Suggestion suggestion) {

        suggestionService.insert(suggestion);

        return "redirect:/suggestion/main";
    }
    // 건의사항 등록, 수정 끝


    // 건의사항 삭제
    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) Long id)
    {
        suggestionService.delete(id);
        return "redirect:/suggestion/main";
    }




    // 상세 페이지 시작
    @GetMapping("/detail")
    public String detail(Model model, @RequestParam(required = true) Long suggestionId) {

        Optional<Suggestion> suggestionFind = suggestionService.findById(suggestionId);
        Suggestion suggestion = suggestionFind.get();

        model.addAttribute("suggestion", suggestion);

        return "suggestion/detail";
    }
    // 상세 페이지 끝

}
