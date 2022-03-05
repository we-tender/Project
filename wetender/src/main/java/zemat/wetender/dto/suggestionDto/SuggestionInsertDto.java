package zemat.wetender.dto.suggestionDto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SuggestionInsertDto {

    private Long id;
    private String suggestionTitle;
    private String suggestionContent;

    public SuggestionInsertDto(String suggestionTitle, String suggestionContent) {
        this.suggestionTitle = suggestionTitle;
        this.suggestionContent = suggestionContent;
    }

}
