package zemat.wetender.dto.suggestionDto.reply;


import lombok.Data;

@Data
public class SuggestionReplyEditDto {

    private Long suggestionId;
    private Long suggestionReplyId;
    private String suggestionReplyContent;

}
