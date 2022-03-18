package zemat.wetender.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zemat.wetender.domain.cocktail.CocktailFile;
import zemat.wetender.domain.cocktail.CocktailIngredient;
import zemat.wetender.domain.ingredient.Ingredient;
import zemat.wetender.dto.ingredientDto.IngredientDto;
import zemat.wetender.service.IngredientService;

import java.net.MalformedURLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/ingredient")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @Value("${cocktailFile.dir}")
    private String cocktailFileDir;

    @Value("${ingredientFile.dir}")
    private String ingredientFileDir;

    // 재료 이미지 보이게 설정
    @ResponseBody
    @GetMapping("/images/{year}/{month}/{day}/{filename}")
    public Resource ingredientDownloadImage(@PathVariable String year,
                                        @PathVariable String month,
                                        @PathVariable String day,
                                        @PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + ingredientFileDir + year + "/"+ month + "/" + day + "/" + filename);
    }

    // 칵테일 이미지 보이게 설정
    @ResponseBody
    @GetMapping("/images/cocktail/{year}/{month}/{day}/{filename}")
    public Resource cocktailDownloadImage(@PathVariable String year,
                                          @PathVariable String month,
                                          @PathVariable String day,
                                          @PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + cocktailFileDir + year + "/"+ month + "/" + day + "/" + filename);
    }


    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long ingredientId, Model model){
        Ingredient ingredient = ingredientService.findById(ingredientId);
        IngredientDto ingredientDto = new IngredientDto(ingredient);
        Map<String,String> cocktails = new ConcurrentHashMap<>();
        for (CocktailIngredient cocktail : ingredientDto.getCocktails()) {
            String cocktailName = cocktail.getCocktail().getCocktailName();
            CocktailFile cocktailFile = cocktail.getCocktail().getCocktailFiles().get(0);
            String storeCocktailFileName = cocktailFile.getUploadPath() +"/" + cocktailFile.getUuid() + "_" + cocktailFile.getFileName();
            cocktails.put(cocktailName,storeCocktailFileName);
        }

        model.addAttribute("ingredientDto", ingredientDto);
        model.addAttribute("cocktails",cocktails);

        return "ingredient/detail";
    }
}
