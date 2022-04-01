package zemat.wetender.dto.liquorDto.reply;

import lombok.Data;

@Data
public class LiquorReplyEditDto {
    private Long liquorId;
    private Long liquorReplyId;
    private String liquorReplyContent;
}
