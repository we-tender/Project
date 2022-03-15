package zemat.wetender.dto.noticeBoardDto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NoticeBoardReplyInsertDto {

    private Long noticeBoardReplyId;
    private Long noticeBoardId;
    private String noticeBoardReplyContent;

}
