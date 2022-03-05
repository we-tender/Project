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
import zemat.wetender.dto.cocktailDto.CocktailHomeDto;
import zemat.wetender.service.CocktailService;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final CocktailService cocktailService;
    private final CocktailFileStore cocktailFileStore;

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
        return "fragment/main";
    }

    @ResponseBody
    @GetMapping("/cocktailTop20Images/{filename}")
    public Resource cocktailTop20Images(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + cocktailFileStore.getFullPath(filename));
    }

    public void getCocktailTop20(Model model) {
        List<CocktailHomeDto> cocktailTop20 = cocktailService.findTop20ByRecommendation();
        model.addAttribute("cocktailTop20", cocktailTop20);
    }
}