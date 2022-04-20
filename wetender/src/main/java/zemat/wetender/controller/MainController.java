package zemat.wetender.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.dto.cocktailDto.CocktailHomeDto;
import zemat.wetender.dto.cocktailDto.CocktailMainDto;
import zemat.wetender.dto.liquorDto.LiquorDto;
import zemat.wetender.dto.liquorDto.LiquorHomeDto;
import zemat.wetender.service.cocktail.CocktailService;
import zemat.wetender.service.liquor.LiquorService;
import zemat.wetender.service.MemberService;

import java.net.MalformedURLException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final CocktailService cocktailService;
    private final LiquorService liquorService;
    private final MemberService memberService;

    @Value("${cocktailFile.dir}") private String cocktailFileDir;
    @Value("${liquorFile.dir}") private String liquorFileDir;

    @ModelAttribute("sessionMember")
    public UserDetails getSessionMember() {
        return memberService.getSessionMember();
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("sessionMember", memberService.getSessionMember());
        getCocktailTop20(model);
        getLiquorTop20(model);
        return "fragment/main";
    }

    // 칵테일 이미지 보이게 설정
    @ResponseBody
    @GetMapping("/cocktailTop20Images/{year}/{month}/{day}/{filename}")
    public Resource cocktailTop20Image(@PathVariable String year,
                                          @PathVariable String month,
                                          @PathVariable String day,
                                          @PathVariable String filename) throws MalformedURLException {
        log.info("파일확인 file:" + cocktailFileDir + year + "/"+ month + "/" + day + "/" + filename);
        return new UrlResource("file:" + cocktailFileDir + year + "/"+ month + "/" + day + "/" + filename);
    }

    // 주류 이미지 보이게 설정
    @ResponseBody
    @GetMapping("/liquorTop20Images/{year}/{month}/{day}/{filename}")
    public Resource liquorTop20Images(@PathVariable String year,
                                          @PathVariable String month,
                                          @PathVariable String day,
                                          @PathVariable String filename) throws MalformedURLException {
        log.info("파일확인 file:" + liquorFileDir + year + "/"+ month + "/" + day + "/" + filename);
        return new UrlResource("file:" + liquorFileDir + year + "/"+ month + "/" + day + "/" + filename);
    }

    // 칵테일 이미지 보이게 설정
    @ResponseBody
    @GetMapping("/cocktailImages/{year}/{month}/{day}/{filename}")
    public Resource cocktailDownloadImage(@PathVariable String year,
                                          @PathVariable String month,
                                          @PathVariable String day,
                                          @PathVariable String filename) throws MalformedURLException {
        log.info("file:" + cocktailFileDir + filename);
        return new UrlResource("file:" + cocktailFileDir + year + "/"+ month + "/" + day + "/" + filename);
    }


    // 주류 이미지 보이게 설정
    @ResponseBody
    @GetMapping("/liquorImages/{year}/{month}/{day}/{filename}")
    public Resource liquorDownloadImage(@PathVariable String year,
                                        @PathVariable String month,
                                        @PathVariable String day,
                                        @PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + liquorFileDir + year + "/"+ month + "/" + day + "/" + filename);
    }


    public void getCocktailTop20(Model model) {
        List<CocktailHomeDto> cocktailTop20 = cocktailService.findTop20ByRecommendation();
        model.addAttribute("cocktailTop20", cocktailTop20);
    }

    public void getLiquorTop20(Model model) {
        List<LiquorHomeDto> liquorTop20 = liquorService.findTop20ByRecommendation();
        model.addAttribute("liquorTop20", liquorTop20);
    }

    // 전체 검색
    @GetMapping("mainSearch")
    public String mainSearch( @PageableDefault(size = 5) Pageable pageable,
                            @RequestParam(required = false, defaultValue = "") String keyword,
                            Model model) {

        Page<Cocktail> cocktails = cocktailService.pageFindKeyword(pageable, keyword);
        Page<CocktailMainDto> cocktailDtos = cocktails.map(cocktail -> new CocktailMainDto(cocktail));

        Page<Liquor> liquors = liquorService.pageFindKeyword(pageable, keyword);
        Page<LiquorDto> liquorDtos = liquors.map(liquor -> new LiquorDto(liquor));

        int startPage = Math.max(1, cocktailDtos.getPageable().getPageNumber() - 4);
        int endPage = Math.min(cocktailDtos.getTotalPages(), cocktailDtos.getPageable().getPageNumber() + 4);
        if(endPage == 0) startPage = 0;

        model.addAttribute("cocktailDtos", cocktailDtos);
        model.addAttribute("liquorDtos", liquorDtos);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("sideMenu", "nav-side-menu-liquor");

        return "fragment/mainSearch";

    }





}