package zemat.wetender.controller.cocktail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.member.Member;
import zemat.wetender.domain.noticeBoard.NoticeStatus;
import zemat.wetender.dto.cocktailDto.CocktailDetailDto;
import zemat.wetender.dto.cocktailDto.CocktailMainDto;
import zemat.wetender.dto.cocktailDto.CocktailSearchSortByDto;
import zemat.wetender.dto.liquorDto.LiquorDto;
import zemat.wetender.dto.liquorDto.LiquorSortDto;
import zemat.wetender.dto.noticeBoardDto.NoticeBoardDto;
import zemat.wetender.service.NoticeBoardService;
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
    private final NoticeBoardService noticeBoardService;
    private final MemberService memberService;


    @Value("${cocktailFile.dir}")
    private String cocktailFileDir;

    @ModelAttribute("sessionMember")
    public UserDetails getSessionMember() {
        return memberService.getSessionMember();
    }

    // 주류 메인(게시판)
    @GetMapping("/main")
    public String main(Model model,
                       @PageableDefault(size = 5) Pageable pageable) {

        String keyword = "";

        // 검색기능
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), 5, Sort.by(Sort.Direction.DESC, "createdBy"));
        Page<Cocktail> cocktails = cocktailService.pageFindKeyword(pageRequest, keyword);
        Page<CocktailMainDto> cocktailDtos = cocktails.map(cocktail -> new CocktailMainDto(cocktail));

        // 페이지
        int startPage = Math.max(1, cocktailDtos.getPageable().getPageNumber() - 4);
        int endPage = Math.min(cocktailDtos.getTotalPages(), cocktailDtos.getPageable().getPageNumber() + 4);
        if(endPage == 0) startPage = 0;

        // 전체공지사항
        Page<NoticeBoardDto> noticeBoardAllDtos = noticeBoardService.statusFindPage(NoticeStatus.ALL, pageable);
        model.addAttribute("noticeBoardAllDtos", noticeBoardAllDtos);

        model.addAttribute("sortBy", "createdBy");
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("cocktailDtos", cocktailDtos);
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

        if (sessionMember != null) {
            Member member = memberService.findByMemberIdName(sessionMember.getUsername());
            boolean likesCheck = cocktailLikesService.likesCheck(member, cocktail);
            model.addAttribute("likesCheck", likesCheck);
        }

        cocktailService.viewsUp(cocktailId);


        return "cocktail/detail";
    }

    // 메인 화면 칵테일 검색 정렬 Ajax
    @RequestMapping(value = "/searchSortBy", method = RequestMethod.POST)
    public String searchSortBy(Model model, CocktailSearchSortByDto dto, Pageable pageable) {

        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        PageRequest pageRequest = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, dto.getSortBy()));

        Page<Cocktail> cocktails = cocktailService.pageFindKeyword(pageRequest, dto.getKeyword());
        Page<CocktailMainDto> cocktailDtos = cocktails.map(cocktail -> new CocktailMainDto(cocktail));

        int startPage = Math.max(1, cocktailDtos.getPageable().getPageNumber() - 4);
        int endPage = Math.min(cocktailDtos.getTotalPages(), cocktailDtos.getPageable().getPageNumber() + 4);
        if (endPage == 0) {
            startPage = 0;
        }

        model.addAttribute("sortBy", dto.getSortBy());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("cocktailDtos", cocktailDtos);
        model.addAttribute("sideMenu", "nav-side-menu-cocktail");

        return "/cocktail/main :: #main";
    }

    // 페이지 이동 Ajax
    @RequestMapping(value = "/movePage", method = RequestMethod.GET)
    public String movePage(Model model,
                           @PageableDefault(size = 5) Pageable pageable,
                           @RequestParam String keyword,
                           @RequestParam String sortBy) {


        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), 5, Sort.by(Sort.Direction.DESC, sortBy));

        Page<Cocktail> cocktails = cocktailService.pageFindKeyword(pageRequest, keyword);
        Page<CocktailMainDto> cocktailDtos = cocktails.map(cocktail -> new CocktailMainDto(cocktail));


        int startPage = Math.max(1, cocktailDtos.getPageable().getPageNumber() - 4);
        int endPage = Math.min(cocktailDtos.getTotalPages(), cocktailDtos.getPageable().getPageNumber() + 4);
        if (endPage == 0) {
            startPage = 0;
        }

        model.addAttribute("keyword", keyword);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("cocktailDtos", cocktailDtos);

        return "/cocktail/main :: #main";
    }


}