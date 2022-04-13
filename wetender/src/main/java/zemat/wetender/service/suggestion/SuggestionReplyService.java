package zemat.wetender.service.suggestion;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.domain.cocktail.CocktailReply;
import zemat.wetender.domain.suggestion.Suggestion;
import zemat.wetender.domain.suggestion.SuggestionReply;
import zemat.wetender.dto.cocktailDto.CocktailMainDto;
import zemat.wetender.dto.cocktailDto.reply.CocktailReplyDeleteDto;
import zemat.wetender.dto.cocktailDto.reply.CocktailReplyEditDto;
import zemat.wetender.dto.cocktailDto.reply.CocktailReplyInsertDto;
import zemat.wetender.dto.suggestionDto.SuggestionDto;
import zemat.wetender.dto.suggestionDto.reply.SuggestionReplyDeleteDto;
import zemat.wetender.dto.suggestionDto.reply.SuggestionReplyEditDto;
import zemat.wetender.dto.suggestionDto.reply.SuggestionReplyInsertDto;
import zemat.wetender.repository.suggestion.SuggestionReplyRepository;
import zemat.wetender.repository.suggestion.SuggestionRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class SuggestionReplyService {

    private final SuggestionReplyRepository suggestionReplyRepository;
    private final SuggestionRepository suggestionRepository;

    // 댓글 등록
    public SuggestionDto insert(SuggestionReplyInsertDto dto) {
        Suggestion suggestion = suggestionRepository.getById(dto.getSuggestionId());
        SuggestionReply suggestionReply = new SuggestionReply(suggestion, dto);
        suggestionReplyRepository.save(suggestionReply);
        suggestion.repliesAdd();
        return new SuggestionDto(suggestion);
    }

    // 댓글 삭제
    public SuggestionDto delete(SuggestionReplyDeleteDto dto) {
        SuggestionReply suggestionReply = suggestionReplyRepository.getById(dto.getSuggestionId());
        suggestionReplyRepository.delete(suggestionReply);
        Suggestion suggestion = suggestionRepository.getById(dto.getSuggestionId());
        suggestion.repliesRemove();
        return new SuggestionDto(suggestion);
    }

    // 댓글 수정
    public SuggestionDto edit(SuggestionReplyEditDto dto) {
        SuggestionReply suggestionReply = suggestionReplyRepository.getById(dto.getSuggestionReplyId());
        suggestionReply.setSuggestionReplyContent(dto.getSuggestionReplyContent());
        Suggestion suggestion = suggestionRepository.getById(dto.getSuggestionId());
        return new SuggestionDto(suggestion);
    }

}
