package zemat.wetender.dto.cocktailDto.reply;

import lombok.Data;

@Data
public class CocktailReplyEditDto {
    private Long cocktailId;
    private Long cocktailReplyId;
    private String cocktailReplyContent;
}
