package zemat.wetender.service.cocktail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.domain.cocktail.CocktailReply;
import zemat.wetender.dto.cocktailDto.CocktailMainDto;
import zemat.wetender.dto.cocktailDto.reply.CocktailReplyDeleteDto;
import zemat.wetender.dto.cocktailDto.reply.CocktailReplyEditDto;
import zemat.wetender.dto.cocktailDto.reply.CocktailReplyInsertDto;
import zemat.wetender.repository.MemberRepository;
import zemat.wetender.repository.cocktail.CocktailReplyRepository;
import zemat.wetender.repository.cocktail.CocktailRepository;


@Service
@Transactional
@RequiredArgsConstructor
public class CocktailReplyService {

    private final CocktailReplyRepository cocktailReplyRepository;
    private final CocktailRepository cocktailRepository;
    private final MemberRepository memberRepository;

    // 댓글 등록
    public CocktailMainDto insert(CocktailReplyInsertDto dto) {
        Cocktail cocktail = cocktailRepository.getById(dto.getCocktailId());
        CocktailReply cocktailReply = new CocktailReply(cocktail, dto);
        cocktailReplyRepository.save(cocktailReply);
        cocktail.repliesAdd();

        return new CocktailMainDto(cocktail);
    }

    // 댓글 삭제
    public CocktailMainDto delete(CocktailReplyDeleteDto dto) {
        CocktailReply cocktailReply = cocktailReplyRepository.getById(dto.getCocktailReplyId());
        cocktailReplyRepository.delete(cocktailReply);
        Cocktail cocktail = cocktailRepository.getById(dto.getCocktailId());
        cocktail.repliesRemove();

        return new CocktailMainDto(cocktail);
    }

    // 댓글 수정
    public CocktailMainDto edit(CocktailReplyEditDto dto) {
        CocktailReply cocktailReply = cocktailReplyRepository.getById(dto.getCocktailReplyId());
        cocktailReply.setCocktailReplyContent(dto.getCocktailReplyContent());
        Cocktail cocktail = cocktailRepository.getById(dto.getCocktailId());

        return new CocktailMainDto(cocktail);
    }

}
