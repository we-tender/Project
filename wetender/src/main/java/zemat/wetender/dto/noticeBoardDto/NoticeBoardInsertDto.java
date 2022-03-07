package zemat.wetender.dto.noticeBoardDto;


import lombok.Getter;
import lombok.Setter;
import zemat.wetender.domain.noticeBoard.NoticeStatus;

@Getter @Setter
public class NoticeBoardInsertDto {

    private Long id;
    private String noticeBoardTitle;
    private String noticeBoardContent;
    private String status;


    public NoticeBoardInsertDto(String noticeBoardTitle, String noticeBoardContent, String status) {
        this.noticeBoardTitle = noticeBoardTitle;
        this.noticeBoardContent = noticeBoardContent;
        this.status = status;
    }
}
