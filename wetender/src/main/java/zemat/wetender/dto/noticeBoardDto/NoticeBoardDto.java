package zemat.wetender.dto.noticeBoardDto;


import lombok.Getter;
import lombok.Setter;
import zemat.wetender.domain.noticeBoard.NoticeBoard;
import zemat.wetender.domain.noticeBoard.NoticeStatus;

import java.time.LocalDateTime;

@Getter @Setter
public class NoticeBoardDto {

    private Long id;
    private String noticeBoardTitle;
    private String noticeBoardContent;
    private NoticeStatus status;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String createdBy;
    private String lastModifiedBy;

    private long views;
    private long heart;

    public NoticeBoardDto() {
    }

    public NoticeBoardDto(NoticeBoard noticeBoard) {
        this.id = noticeBoard.getId();
        this.noticeBoardTitle = noticeBoard.getNoticeBoardTitle();
        this.noticeBoardContent = noticeBoard.getNoticeBoardContent();
        this.status = noticeBoard.getStatus();
        this.createdDate = noticeBoard.getCreatedDate();
        this.lastModifiedDate = noticeBoard.getLastModifiedDate();
        this.createdBy = noticeBoard.getCreatedBy();
        this.lastModifiedBy = noticeBoard.getLastModifiedBy();
        this.views = noticeBoard.getViews();
        this.heart = noticeBoard.getHeart();
    }
}
