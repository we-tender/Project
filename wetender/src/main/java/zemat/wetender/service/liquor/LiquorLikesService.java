package zemat.wetender.service.liquor;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.domain.cocktail.CocktailLikes;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.liquor.LiquorLikes;
import zemat.wetender.domain.member.Member;
import zemat.wetender.dto.cocktailDto.CocktailMainDto;
import zemat.wetender.dto.liquorDto.LiquorDto;
import zemat.wetender.dto.liquorDto.likes.LiquorLikesInsertOrDeleteDto;
import zemat.wetender.repository.MemberRepository;
import zemat.wetender.repository.liquor.LiquorLikesRepository;
import zemat.wetender.repository.liquor.LiquorRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LiquorLikesService {

    private final LiquorLikesRepository liquorLikesRepository;
    private final LiquorRepository liquorRepository;
    private final MemberRepository memberRepository;

    // 주류 좋아요 저장하기 삭제하기
    public LiquorDto insertOrDelete(LiquorLikesInsertOrDeleteDto dto) {

        String memberName = dto.getMemberName();
        Member member = memberRepository.findByMemberIdName(memberName).get();

        Long liquorId = dto.getLiquorId();
        Liquor liquor = liquorRepository.getById(liquorId);



        LiquorLikes liquorLikes = new LiquorLikes(member, liquor);

        Optional<LiquorLikes> liquorLikesCheck = liquorLikesRepository.findByMemberAndLiquor(member, liquor);

        if ( liquorLikesCheck.isEmpty() ) {
            liquorLikesRepository.save(liquorLikes);
            liquor.likesAdd();
        }
        else {
            liquorLikesRepository.delete(liquorLikesCheck.get());
            liquor.likesRemove();
        }

        Liquor newLiquor = liquorRepository.getById(liquorId);

        return new LiquorDto(newLiquor);
    }


    // 주류 좋아요 확인
    public boolean likesCheck(Member member, Liquor liquor) {
        Optional<LiquorLikes> liquorLikesCheck = liquorLikesRepository.findByMemberAndLiquor(member, liquor);

        if ( liquorLikesCheck.isEmpty() ) {
            return false;
        }
        else {
            return true;
        }
    }

    // 회원이 좋아요 누른 주류 반환
    public Page<LiquorDto> findMemberId(Long memberId, Pageable pageable) {
        Member member = memberRepository.getById(memberId);
        Page<LiquorLikes> liquorLikes = liquorLikesRepository.findByMember(member, pageable);

        Page<Liquor> liquorlist = liquorLikes.map(LiquorLikes::getLiquor);

        return liquorlist.map(LiquorDto::new);
    }

}
