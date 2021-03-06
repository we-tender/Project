package zemat.wetender.dto.suggestionDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
public class SuggestionInsertDto {

    private Long id;

    @NotBlank
    private String suggestionTitle;

    @NotBlank
    private String suggestionContent;

    public SuggestionInsertDto() {
    }

    public SuggestionInsertDto(String suggestionTitle, String suggestionContent) {
        this.suggestionTitle = suggestionTitle;
        this.suggestionContent = suggestionContent;
    }

}
