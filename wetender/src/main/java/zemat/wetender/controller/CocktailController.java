package zemat.wetender.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.dto.liquorDto.LiquorDto;
import zemat.wetender.service.CocktailService;

@Controller
@RequestMapping("/cocktail")
@RequiredArgsConstructor
@Slf4j
public class CocktailController {

    private final CocktailService cocktailService;


    @GetMapping("/{itemId}")
    public String cocktailDetail(@PathVariable("cocktailId") Long cocktailId,Model model){

        Cocktail cocktail = cocktailService.findById(cocktailId);
        model.addAttribute("cocktail",cocktail);

        return "cocktail/detail";
    }
}
