package zemat.wetender.controller.liquor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zemat.wetender.domain.cocktail.CocktailFile;
import zemat.wetender.domain.cocktail.CocktailLiquor;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.member.Member;
import zemat.wetender.dto.liquorDto.LiquorDto;
import zemat.wetender.dto.liquorDto.LiquorSortDto;
import zemat.wetender.service.liquor.LiquorLikesService;
import zemat.wetender.service.liquor.LiquorService;
import zemat.wetender.service.MemberService;

import java.net.MalformedURLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Controller
@RequestMapping("/liquor")
@RequiredArgsConstructor
public class LiquorController {

    private final LiquorService liquorService;
    private final LiquorLikesService liquorLikesService;
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

        model.addAttribute("sortBy", "createdBy");
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

        Map<Long, Map<String,String>> cocktails = new ConcurrentHashMap<>();
        for (CocktailLiquor cocktail : liquorDto.getCocktails()) {
            Long cocktailId = cocktail.getCocktail().getId();
            cocktails.put(cocktailId, new ConcurrentHashMap<>());

            String cocktailName = cocktail.getCocktail().getCocktailName();
            CocktailFile cocktailFile = cocktail.getCocktail().getCocktailFiles().get(0);
            String storedCocktailFileName = cocktailFile.getUploadPath() +"/" + cocktailFile.getUuid() + "_" + cocktailFile.getFileName();

            cocktails.get(cocktailId).put("cocktailName", cocktailName);
            cocktails.get(cocktailId).put("storedCocktailFileName", storedCocktailFileName);
        }

        UserDetails sessionMember = memberService.getSessionMember();


        model.addAttribute("liquorDto", liquorDto);
        model.addAttribute("cocktails",cocktails);
        model.addAttribute("sessionMember", sessionMember);

        if (sessionMember != null) {
            Member member = memberService.findByMemberIdName(sessionMember.getUsername());
            boolean likesCheck = liquorLikesService.likesCheck(member, liquor);
            model.addAttribute("likesCheck", likesCheck);
        }

        liquorService.viewsUp(liquorId);

        return "liquor/detail";
    }


    // 메인 화면 주류 정렬 Ajax
    @RequestMapping(value = "/sortBy", method = RequestMethod.POST)
    public String sortBy(Model model, LiquorSortDto dto, Pageable pageable) {


        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        PageRequest pageRequest = PageRequest.of(page, 24, Sort.by(Sort.Direction.DESC, dto.getSortBy()));

        Page<Liquor> liquors = liquorService.pageFindKeyword(pageRequest, dto.getKeyword());
        Page<LiquorDto> liquorDtos = liquors.map(liquor -> new LiquorDto(liquor));

        int startPage = Math.max(1, liquorDtos.getPageable().getPageNumber() - 4);
        int endPage = Math.min(liquorDtos.getTotalPages(), liquorDtos.getPageable().getPageNumber() + 4);
        if (endPage == 0) {
            startPage = 0;
        }

        model.addAttribute("sortBy", dto.getSortBy());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("liquorDtos", liquorDtos);
        model.addAttribute("sideMenu", "nav-side-menu-liquor");


        return "/liquor/main :: #main";
    }


}
