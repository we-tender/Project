package zemat.wetender.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.dto.liquorDto.LiquorDto;
import zemat.wetender.dto.supportersDto.LiquorInsertForm;
import zemat.wetender.service.LiquorService;


@Controller
@RequestMapping("/liquor")
@RequiredArgsConstructor
public class LiquorController {

    private final LiquorService liquorService;


    // 주류 메인(게시판)
    @GetMapping("/main")
    public String main(Model model,
                       @PageableDefault(size = 10)Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText
                       ) {

        // 검색기능
        Page<Liquor> liquors = liquorService.pageFindKeyword(pageable, searchText);
        Page<LiquorDto> liquorDtos = liquors.map(liquor -> new LiquorDto(liquor));

        // 페이지
        int startPage = Math.max(1, liquorDtos.getPageable().getPageNumber() - 4);
        int endPage = Math.min(liquorDtos.getTotalPages(), liquorDtos.getPageable().getPageNumber() + 4);

        if(endPage == 0) startPage = 0;

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("liquorDtos", liquorDtos);
        return "liquor/main";
    }

    // 주류 등록


}
