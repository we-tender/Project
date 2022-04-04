package zemat.wetender.controller.cocktail;

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
import zemat.wetender.domain.member.Member;
import zemat.wetender.dto.cocktailDto.CocktailDetailDto;
import zemat.wetender.dto.cocktailDto.CocktailMainDto;
import zemat.wetender.service.cocktail.CocktailLikesService;
import zemat.wetender.service.cocktail.CocktailService;
import zemat.wetender.service.MemberService;

import java.net.MalformedURLException;

@Controller
@RequestMapping("/cocktail")
@RequiredArgsConstructor
@Slf4j
public class CocktailController {

    private final CocktailService cocktailService;
    private final CocktailLikesService cocktailLikesService;
    private final MemberService memberService;


    @Value("${cocktailFile.dir}")
    private String cocktailFileDir;

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
        model.addAttribute("sessionMember", memberService.getSessionMember());
        model.addAttribute("sideMenu", "nav-side-menu-cocktail");


        return "cocktail/main";
    }

    // 칵테일 이미지 보이게 설정
    @ResponseBody
    @GetMapping("/images/{year}/{month}/{day}/{filename}")
    public Resource cocktailDownloadImage(@PathVariable String year,
                                          @PathVariable String month,
                                          @PathVariable String day,
                                          @PathVariable String filename) throws MalformedURLException {
            log.info("file:" + cocktailFileDir + filename);
            return new UrlResource("file:" + cocktailFileDir + year + "/"+ month + "/" + day + "/" + filename);
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long cocktailId, Model model){
        Cocktail cocktail = cocktailService.findById(cocktailId);
        String fileName = cocktail.getCocktailFiles().get(0).getFileName();
        log.info("fileName확인" + fileName);
        CocktailDetailDto cocktailDetailDto = new CocktailDetailDto(cocktail);

        UserDetails sessionMember = memberService.getSessionMember();

        model.addAttribute("cocktailDto",cocktailDetailDto);
        model.addAttribute("sessionMember", sessionMember);

        if (sessionMember != null) {
            Member member = memberService.findByMemberIdName(sessionMember.getUsername());
            boolean likesCheck = cocktailLikesService.likesCheck(member, cocktail);
            model.addAttribute("likesCheck", likesCheck);
        }

        cocktailService.viewsUp(cocktailId);


        return "cocktail/detail";
    }
}