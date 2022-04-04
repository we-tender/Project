package zemat.wetender.service.cocktail;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.domain.cocktail.CocktailLikes;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.liquor.LiquorLikes;
import zemat.wetender.domain.member.Member;
import zemat.wetender.dto.cocktailDto.CocktailMainDto;
import zemat.wetender.dto.cocktailDto.likes.CocktailLikesInsertOrDeleteDto;
import zemat.wetender.repository.MemberRepository;
import zemat.wetender.repository.cocktail.CocktailLikesRepository;
import zemat.wetender.repository.cocktail.CocktailRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CocktailLikesService {

    private final CocktailRepository cocktailRepository;
    private final CocktailLikesRepository cocktailLikesRepository;
    private final MemberRepository memberRepository;


    // 칵테일 좋아요 저장하기 삭제하기
    public CocktailMainDto insertOrDelete(CocktailLikesInsertOrDeleteDto dto) {

        Member member = memberRepository.findByMemberIdName(dto.getMemberName()).get();
        Cocktail cocktail = cocktailRepository.getById(dto.getCocktailId());

        Optional<CocktailLikes> cocktailLikesCheck = cocktailLikesRepository.findByMemberAndCocktail(member, cocktail);

        if ( cocktailLikesCheck.isEmpty() ) {
            CocktailLikes cocktailLikes = new CocktailLikes(member, cocktail);
            cocktailLikesRepository.save(cocktailLikes);
            cocktail.likesAdd();
        }
        else {
            cocktailLikesRepository.delete(cocktailLikesCheck.get());
            cocktail.likesRemove();
        }

        return new CocktailMainDto(cocktail);

    }


    // 칵테일 좋아요 확인
    public boolean likesCheck(Member member, Cocktail cocktail) {
        Optional<CocktailLikes> cocktailLikesCheck = cocktailLikesRepository.findByMemberAndCocktail(member, cocktail);

        if ( cocktailLikesCheck.isEmpty() ) {
            return false;
        }
        else {
            return true;
        }
    }

}
