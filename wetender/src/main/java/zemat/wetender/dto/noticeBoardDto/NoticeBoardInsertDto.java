package zemat.wetender.dto.noticeBoardDto;


import lombok.Getter;
import lombok.Setter;
import zemat.wetender.domain.noticeBoard.NoticeStatus;

@Getter @Setter
public class NoticeBoardInsertDto {

    private Long id;
    private String noticeBoardTitle;
    private String noticeBoardContent;
    private NoticeStatus status;


    public NoticeBoardInsertDto(String noticeBoardTitle, String noticeBoardContent, NoticeStatus status) {
        this.noticeBoardTitle = noticeBoardTitle;
        this.noticeBoardContent = noticeBoardContent;
        this.status = status;
    }
}
