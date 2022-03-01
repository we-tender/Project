package zemat.wetender.dto.suggestionDto;


import lombok.Getter;
import lombok.Setter;
import zemat.wetender.domain.suggestion.Suggestion;

@Getter @Setter
public class SuggestionReplyInsertDto {

    private String suggestionReplyContent;
    private Long suggestionId;

    public SuggestionReplyInsertDto(String suggestionReplyContent, Long suggestionId) {
        this.suggestionReplyContent = suggestionReplyContent;
        this.suggestionId = suggestionId;
    }
}
