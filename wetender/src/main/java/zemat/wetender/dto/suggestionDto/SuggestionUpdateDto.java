package zemat.wetender.dto.suggestionDto;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SuggestionUpdateDto {

    @NotNull
    private Long id;

    @NotBlank
    private String suggestionTitle;

    @NotBlank
    private String suggestionContent;

    public SuggestionUpdateDto() {
    }

    public SuggestionUpdateDto(Long id, String suggestionTitle, String suggestionContent) {
        this.id = id;
        this.suggestionTitle = suggestionTitle;
        this.suggestionContent = suggestionContent;
    }
}
