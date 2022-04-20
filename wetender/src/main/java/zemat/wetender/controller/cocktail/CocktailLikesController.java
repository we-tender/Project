package zemat.wetender.controller.cocktail;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.member.Member;
import zemat.wetender.dto.cocktailDto.CocktailMainDto;
import zemat.wetender.dto.cocktailDto.likes.CocktailLikesInsertOrDeleteDto;
import zemat.wetender.dto.liquorDto.LiquorDto;
import zemat.wetender.dto.liquorDto.likes.LiquorLikesInsertOrDeleteDto;
import zemat.wetender.service.MemberService;
import zemat.wetender.service.cocktail.CocktailLikesService;
import zemat.wetender.service.cocktail.CocktailService;

@Controller
@RequestMapping("/cocktailLikes")
@RequiredArgsConstructor
public class CocktailLikesController {

    private final CocktailService cocktailService;
    private final CocktailLikesService cocktailLikesService;
    private final MemberService memberService;

    @ModelAttribute("sessionMember")
    public UserDetails getSessionMember() {
        return memberService.getSessionMember();
    }

    // 좋아요 삽입, 삭제
    @RequestMapping(value = "/InsertOrDelete", method = RequestMethod.POST)
    public String insertOrDeleteAjax(Model model, CocktailLikesInsertOrDeleteDto dto) {

        Member member = memberService.findByMemberIdName(dto.getMemberName());
        Cocktail cocktail = cocktailService.findById(dto.getCocktailId());

        CocktailMainDto cocktailDto = cocktailLikesService.insertOrDelete(dto);


        boolean likesCheck = cocktailLikesService.likesCheck(member, cocktail);

        model.addAttribute("likesCheck", likesCheck);
        model.addAttribute("cocktailDto", cocktailDto);

        return "/cocktail/detail :: #likes";
    }



}
