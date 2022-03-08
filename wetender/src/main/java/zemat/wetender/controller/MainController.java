package zemat.wetender.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.domain.cocktail.CocktailFileStore;
import zemat.wetender.domain.liquor.LiquorFileStore;
import zemat.wetender.dto.cocktailDto.CocktailHomeDto;
import zemat.wetender.dto.liquorDto.LiquorHomeDto;
import zemat.wetender.service.CocktailService;
import zemat.wetender.service.LiquorService;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final CocktailService cocktailService;
    private final CocktailFileStore cocktailFileStore;
    private final LiquorService liquorService;
    private final LiquorFileStore liquorFileStore;

    @GetMapping("/")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("sessionMember", null);
        } else {
            UserDetails principalDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("sessionMember", principalDetails);
        }
        getCocktailTop20(model);
        getLiquorTop20(model);
        return "fragment/main";
    }

    @ResponseBody
    @GetMapping("/cocktailTop20Images/{filename}")
    public Resource cocktailTop20Images(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + cocktailFileStore.getFullPath(filename));
    }

    @ResponseBody
    @GetMapping("/liquorTop20Images/{filename}")
    public Resource liquorTop20Images(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + liquorFileStore.getFullPath(filename));
    }

    public void getCocktailTop20(Model model) {
        List<CocktailHomeDto> cocktailTop20 = cocktailService.findTop20ByRecommendation();
        model.addAttribute("cocktailTop20", cocktailTop20);
    }

    public void getLiquorTop20(Model model) {
        List<LiquorHomeDto> liquorTop20 = liquorService.findTop20ByRecommendation();
        model.addAttribute("liquorTop20", liquorTop20);
    }
}