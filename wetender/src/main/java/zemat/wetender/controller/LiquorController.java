package zemat.wetender.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zemat.wetender.domain.cocktail.CocktailFile;
import zemat.wetender.domain.cocktail.CocktailLiquor;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.dto.liquorDto.LiquorDto;
import zemat.wetender.service.LiquorService;
import zemat.wetender.service.MemberService;

import java.net.MalformedURLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Controller
@RequestMapping("/liquor")
@RequiredArgsConstructor
public class LiquorController {

    private final LiquorService liquorService;
    private final MemberService memberService;


    @Value("${cocktailFile.dir}")
    private String cocktailFileDir;

    @Value("${liquorFile.dir}")
    private String liquorFileDir;

    // 주류 메인(게시판)
    @GetMapping("/main")
    public String main(Model model,
                       @PageableDefault(size = 24)Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String keyword
                       ) {

        // 검색기능
        Page<Liquor> liquors = liquorService.pageFindKeyword(pageable, keyword);
        Page<LiquorDto> liquorDtos = liquors.map(liquor -> new LiquorDto(liquor));
        // 페이지
        int startPage = Math.max(1, liquorDtos.getPageable().getPageNumber() - 4);
        int endPage = Math.min(liquorDtos.getTotalPages(), liquorDtos.getPageable().getPageNumber() + 4);

        if(endPage == 0) startPage = 0;

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("liquorDtos", liquorDtos);
        model.addAttribute("sessionMember", memberService.getSessionMember());
        model.addAttribute("sideMenu", "nav-side-menu-liquor");
        return "liquor/main";
    }

//    // 주류 이미지 보이게 설정
//    @ResponseBody
//    @GetMapping("/images/{filename}")
//    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
//        return new UrlResource("file:" + liquorFileStore.getFullPath(filename));
//    }

    // 주류 이미지 보이게 설정
    @ResponseBody
    @GetMapping("/images/{year}/{month}/{day}/{filename}")
    public Resource liquorDownloadImage(@PathVariable String year,
                                          @PathVariable String month,
                                          @PathVariable String day,
                                          @PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + liquorFileDir + year + "/"+ month + "/" + day + "/" + filename);
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
    public String detail(@PathVariable("id") Long liquorId, Model model){
        Liquor liquor = liquorService.findById(liquorId);
        LiquorDto liquorDto = new LiquorDto(liquor);

        Map<String,String> cocktails = new ConcurrentHashMap<>();
        for (CocktailLiquor cocktail : liquorDto.getCocktails()) {
            String cocktailName = cocktail.getCocktail().getCocktailName();
            CocktailFile cocktailFile = cocktail.getCocktail().getCocktailFiles().get(0);
            String storeCocktailFileName = cocktailFile.getUploadPath() +"/" + cocktailFile.getUuid() + "_" + cocktailFile.getFileName();
            cocktails.put(cocktailName,storeCocktailFileName);
        }

        model.addAttribute("liquorDto", liquorDto);
        model.addAttribute("cocktails",cocktails);
        model.addAttribute("sessionMember", memberService.getSessionMember());

        return "liquor/detail";
    }


}
