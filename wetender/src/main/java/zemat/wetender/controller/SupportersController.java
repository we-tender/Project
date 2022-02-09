package zemat.wetender.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.domain.cocktail.CocktailTaste;
import zemat.wetender.dto.supportersDto.CocktailInsertForm;
import zemat.wetender.service.SupportersService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/supporters")
@RequiredArgsConstructor
public class SupportersController {

    private final SupportersService supportersService;

    @ModelAttribute("tastes")
    public Map<String, String> tastes(){
        Map<String, String> tastes = new LinkedHashMap<>();
        tastes.put("단맛","단맛");
        tastes.put("쓴맛","쓴맛");
        tastes.put("신맛","신맛");
        tastes.put("매운맛","매운맛");
        tastes.put("짠맛","짠맛");
        tastes.put("고소한맛","고소한맛");
        tastes.put("떫은맛","떫은맛");

        return tastes;
    }

    @GetMapping("/main")
    public String main(Model model) {

        return "supporters/main";
    }

    @GetMapping("/cocktailInsert")
    public String cocktailInsertForm(Model model){

        model.addAttribute("form", new CocktailInsertForm());

        return "supporters/cocktailInsert";
    }

    @PostMapping("/cocktailInsert")
    public String cocktailInsert(CocktailInsertForm form){
        List<String> tastes = form.getTastes();
        List<CocktailTaste> cocktailTastes = new ArrayList<>();

        for (String taste : tastes) {
            CocktailTaste cocktailTaste = new CocktailTaste(taste);
            cocktailTastes.add(cocktailTaste);
        }

        Cocktail cocktail = Cocktail.builder()
                .cocktailName(form.getName())
                .cocktailEName(form.getEName())
                .cocktailAbv(form.getAbv())
                .cocktailBase(form.getBase())
                .cocktailContent(form.getContent())
                .cocktailOneLine(form.getOneLine())
                .cocktailTastes(cocktailTastes)
                .build();


        supportersService.cocktailSave(cocktail);

        return "redirect:/supporters/main";

    }

}