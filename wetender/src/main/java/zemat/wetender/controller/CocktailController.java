package zemat.wetender.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.domain.cocktail.CocktailFileStore;
import zemat.wetender.dto.cocktailDto.CocktailDetailDto;
import zemat.wetender.dto.cocktailDto.CocktailMainDto;
import zemat.wetender.service.CocktailService;

import java.net.MalformedURLException;

@Controller
@RequestMapping("/cocktail")
@RequiredArgsConstructor
@Slf4j
public class CocktailController {

    private final CocktailService cocktailService;
    private final CocktailFileStore cocktailFileStore;

    // 주류 메인(게시판)
    @GetMapping("/main")
    public String main(Model model,
                       @PageableDefault(size = 24) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String keyword) {

        // 검색기능
        Page<Cocktail> cocktails = cocktailService.pageFindKeyword(pageable, keyword);
        Page<CocktailMainDto> cocktailDtos = cocktails.map(cocktail -> new CocktailMainDto(cocktail));
        // 페이지
        int startPage = Math.max(1, cocktailDtos.getPageable().getPageNumber() - 4);
        int endPage = Math.min(cocktailDtos.getTotalPages(), cocktailDtos.getPageable().getPageNumber() + 4);

        if(endPage == 0) startPage = 0;

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("cocktailDtos", cocktailDtos);
        return "cocktail/main";
    }

    // 칵테일 이미지 보이게 설정
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource cocktailDownloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + cocktailFileStore.getFullPath(filename));
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long cocktailId, Model model){
        Cocktail cocktail = cocktailService.findById(cocktailId);
        CocktailDetailDto cocktailDetailDto = new CocktailDetailDto(cocktail);
        model.addAttribute("cocktailDto",cocktailDetailDto);

        return "cocktail/detail";
    }
}
