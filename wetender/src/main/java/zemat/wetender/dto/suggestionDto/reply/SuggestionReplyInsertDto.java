package zemat.wetender.dto.suggestionDto.reply;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import zemat.wetender.domain.suggestion.Suggestion;

@Data
public class SuggestionReplyInsertDto {

    private Long suggestionId;
    private String suggestionReplyContent;


    public SuggestionReplyInsertDto(String suggestionReplyContent, Long suggestionId) {
        this.suggestionId = suggestionId;
        this.suggestionReplyContent = suggestionReplyContent;
    }
}
