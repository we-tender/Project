package zemat.wetender.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zemat.wetender.domain.cocktail.*;
import zemat.wetender.domain.ingredient.Ingredient;
import zemat.wetender.domain.ingredient.IngredientFile;
import zemat.wetender.domain.ingredient.IngredientFileStore;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.liquor.LiquorFile;
import zemat.wetender.domain.liquor.LiquorFileStore;
import zemat.wetender.dto.ingredientDto.IngredientDto;
import zemat.wetender.dto.liquorDto.LiquorDto;
import zemat.wetender.dto.supportersDto.CocktailInsertForm;
import zemat.wetender.dto.supportersDto.IngredientInsertForm;
import zemat.wetender.dto.supportersDto.LiquorInsertForm;
import zemat.wetender.service.IngredientService;
import zemat.wetender.service.LiquorService;
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
@Slf4j
public class SupportersController {

    private final SupportersService supportersService;
    private final CocktailFileStore cocktailFileStore;
    private final LiquorService liquorService;
    private final IngredientService ingredientService;

    private final LiquorFileStore liquorFileStore;
    private final IngredientFileStore ingredientFileStore;

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
    public String cocktailInsert(@Validated @ModelAttribute("form") CocktailInsertForm form, BindingResult bindingResult,
                                 HttpServletRequest request) throws IOException {

        // 검증 실패 로직
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "/supporters/cocktailInsert";
        }

        // 칵테일 맛
        List<String> tastes = form.getTastes();
        List<CocktailTaste> cocktailTastes = new ArrayList<>();

        for (String taste : tastes) {
            CocktailTaste cocktailTaste = new CocktailTaste(taste);
            cocktailTastes.add(cocktailTaste);
        }

        // 칵테일 순서
        List<CocktailSequence> cocktailSequences = new ArrayList<>();

        String[] sArr = request.getParameterValues("sequenceCnt");
        int n = Integer.parseInt(sArr[0]);
        for(int i = 1; i <= n; i++){
            String[] values1 = request.getParameterValues("sequenceContent" + i);
            String sequence = values1[0];
            CocktailSequence cocktailSequence = new CocktailSequence(sequence);
            cocktailSequences.add(cocktailSequence);
        }

        // 칵테일 주류 재료
        List<CocktailIngredient> cocktailIngredients = new ArrayList<>();

        sArr = request.getParameterValues("liquorIngredientCnt");
        n = Integer.parseInt(sArr[0]);

        for(int i = 1; i <= n; i++){
            String[] values1 = request.getParameterValues("liquorIngredientId" + i);
            String[] values2 = request.getParameterValues("liquorIngredientQty" + i);
            if(values1[0].equals("")) continue;
            Long liquorId = Long.parseLong(values1[0]);
            String liquorIngredientQty = values2[0];
            Liquor liquor = liquorService.findById(liquorId);
            CocktailIngredient liquorIngredient = CocktailIngredient.builder()
                    .liquor(liquor)
                    .cocktailIngredientQty(liquorIngredientQty)
                    .build();

            cocktailIngredients.add(liquorIngredient);
        }

        // 칵테일 재료

        sArr = request.getParameterValues("cocktailIngredientCnt");
        n = Integer.parseInt(sArr[0]);

        for(int i = 1; i <= n; i++){
            String[] values1 = request.getParameterValues("cocktailIngredientId" + i);
            String[] values2 = request.getParameterValues("cocktailIngredientQty" + i);
            if(values1[0].equals("")) continue;
            Long ingredientId = Long.parseLong(values1[0]);
            String cocktailIngredientQty = values2[0];
            Ingredient ingredient = ingredientService.findById(ingredientId);

            CocktailIngredient cocktailIngredient = CocktailIngredient.builder()
                    .ingredient(ingredient)
                    .cocktailIngredientQty(cocktailIngredientQty)
                    .build();

            cocktailIngredients.add(cocktailIngredient);
        }

        //칵테일 파일
        List<CocktailFile> cocktailFiles = cocktailFileStore.storeFiles(form.getImages());


        // 칵테일 엔티티 생성
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
                .cocktailIngredients(cocktailIngredients)
                .cocktailRecommendation(0)
                .build();

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

    @GetMapping("/ingredientInsert")
    public String ingredientInsertForm(Model model){

        model.addAttribute("form", new IngredientInsertForm());

        return "supporters/ingredientInsert";
    }

    @PostMapping("/ingredientInsert")
    public String ingredientInsert(IngredientInsertForm form) throws IOException {

        List<IngredientFile> ingredientFiles = ingredientFileStore.storeFiles(form.getImages());

        Ingredient ingredient =
                Ingredient.builder()
                .ingredientName(form.getName())
                .ingredientEname(form.getEName())
                .ingredientContent(form.getContent())
                .ingredientFiles(ingredientFiles)
                .build();

        supportersService.ingredientSave(ingredient);

        return "redirect:/supporters/main";
    }

    @GetMapping("/pop/liquorPop")
    public String liquorPopForm(Model model,
                                @RequestParam(value = "id",required = false) String id,
                                @RequestParam(value = "name",required = false) String name,
                                @RequestParam(value = "keyword",required = false, defaultValue = "") String keyword,
                                Pageable pageable){

        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        PageRequest pageRequest = PageRequest.of(page, 5, Sort.by(Sort.Direction.ASC, "liquorName"));
        Page<Liquor> liquors = liquorService.pageFindKeyword(pageRequest,keyword);

        Page<LiquorDto> liquorDtos = liquors.map(liquor -> new LiquorDto(liquor));

        model.addAttribute("id",id);
        model.addAttribute("name",name);
        model.addAttribute("liquors",liquorDtos);

        return "supporters/pop/liquorPop";
    }

    @GetMapping("/pop/ingredientPop")
    public String ingredientPopForm(Model model,
                                @RequestParam(value = "id",required = false) String id,
                                @RequestParam(value = "name",required = false) String name,
                                @RequestParam(value = "keyword",required = false, defaultValue = "") String keyword,
                                Pageable pageable){

        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        PageRequest pageRequest = PageRequest.of(page, 5, Sort.by(Sort.Direction.ASC, "ingredientName"));
        Page<Ingredient> ingredients = ingredientService.pageFindKeyword(pageRequest,keyword);

        Page<IngredientDto> ingredientDtos = ingredients.map(ingredient -> new IngredientDto(ingredient));

        System.out.printf("id는 잘 받음%s\n",id);
        System.out.printf("name는 잘 받음%s\n",name);
        model.addAttribute("id",id);
        model.addAttribute("name",name);
        model.addAttribute("ingredients",ingredientDtos);

        return "supporters/pop/ingredientPop";
    }
}