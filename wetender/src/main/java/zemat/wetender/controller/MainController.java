package zemat.wetender.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zemat.wetender.dto.cocktailDto.CocktailHomeDto;
import zemat.wetender.dto.liquorDto.LiquorHomeDto;
import zemat.wetender.service.CocktailService;
import zemat.wetender.service.LiquorService;
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
    public Resource cocktailDownloadImage(@PathVariable String year,
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


    public void getCocktailTop20(Model model) {
        List<CocktailHomeDto> cocktailTop20 = cocktailService.findTop20ByRecommendation();
        model.addAttribute("cocktailTop20", cocktailTop20);
    }

    public void getLiquorTop20(Model model) {
        List<LiquorHomeDto> liquorTop20 = liquorService.findTop20ByRecommendation();
        model.addAttribute("liquorTop20", liquorTop20);
    }
}