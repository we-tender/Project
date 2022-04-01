package zemat.wetender.dto.noticeBoardDto.reply;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class NoticeBoardReplyInsertDto {

    private Long noticeBoardId;
    private Long noticeBoardReplyId;
    private String noticeBoardReplyContent;

}
