package zemat.wetender.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import zemat.wetender.domain.cocktail.*;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.liquor.LiquorFile;
import zemat.wetender.domain.liquor.LiquorFileStore;
import zemat.wetender.dto.supportersDto.CocktailInsertForm;
import zemat.wetender.dto.supportersDto.LiquorInsertForm;
import zemat.wetender.service.SupportersService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/supporters")
@RequiredArgsConstructor
public class SupportersController {

    private final SupportersService supportersService;
    private final CocktailFileStore cocktailFileStore;
    private final LiquorFileStore liquorFileStore;

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
    public String cocktailInsert(CocktailInsertForm form,
                                 HttpServletRequest request) throws IOException {

        // 칵테일 맛
        List<String> tastes = form.getTastes();
        List<CocktailTaste> cocktailTastes = new ArrayList<>();

        for (String taste : tastes) {
            CocktailTaste cocktailTaste = new CocktailTaste(taste);
            cocktailTastes.add(cocktailTaste);
        }


        // 칵테일 순서
        List<CocktailSequence> cocktailSequences = new ArrayList<>();

        String[] values = request.getParameterValues("cnt");
        int n = Integer.parseInt(values[0]);
        for(int i = 1; i <= n; i++){
            String[] values1 = request.getParameterValues("iname" + i);
            String sequence = values1[0];

            CocktailSequence cocktailSequence = new CocktailSequence(sequence);
            cocktailSequences.add(cocktailSequence);
        }

        //칵테일 파일
        List<CocktailFile> cocktailFiles = cocktailFileStore.storeFiles(form.getImages());

        Cocktail cocktail = Cocktail.builder()
                .cocktailName(form.getName())
                .cocktailEName(form.getEName())
                .cocktailAbv(form.getAbv())
                .cocktailBase(form.getBase())
                .cocktailContent(form.getContent())
                .cocktailOneLine(form.getOneLine())
                .cocktailTastes(cocktailTastes)
                .cocktailFiles(cocktailFiles)
                .cocktailSequences(cocktailSequences)
                .build();

        System.out.println(cocktail.getCocktailName());

        supportersService.cocktailSave(cocktail);

        return "redirect:/supporters/main";

    }

    @GetMapping("/liquorInsert")
    public String liquorInsertForm(Model model){

        model.addAttribute("form", new LiquorInsertForm());

        return "supporters/liquorInsert";
    }

    @PostMapping("/liquorInsert")
    public String liquorInsert(LiquorInsertForm form) throws IOException {

        List<LiquorFile> liquorFiles = liquorFileStore.storeFiles(form.getImages());

        Liquor liquor = Liquor.builder()
                .liquorName(form.getName())
                .liquorEname(form.getEName())
                .liquorAbv(form.getAbv())
                .liquorContent(form.getContent())
                .liquorOneLine(form.getOneLine())
                .liquorFiles(liquorFiles)
                .build();

        supportersService.liquorSave(liquor);

        return "redirect:/supporters/main";
    }
}