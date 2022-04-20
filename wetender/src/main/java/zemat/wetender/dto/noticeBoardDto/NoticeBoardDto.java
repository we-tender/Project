package zemat.wetender.dto.noticeBoardDto;


import lombok.Getter;
import lombok.Setter;
import zemat.wetender.domain.noticeBoard.NoticeBoardLikes;
import zemat.wetender.domain.noticeBoard.NoticeBoard;
import zemat.wetender.domain.noticeBoard.NoticeBoardReply;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class NoticeBoardDto {

    private Long id;
    private String noticeBoardTitle;
    private String noticeBoardContent;
    private String status;

    private List<NoticeBoardReply> noticeBoardReplyList = new ArrayList<>();
    private List<NoticeBoardLikes> noticeBoardLikesList = new ArrayList<>();

    private String createdDate;
    private LocalDateTime lastModifiedDate;
    private String createdBy;
    private String lastModifiedBy;

    private long views;
    private long likes;
    private long replies;

    public NoticeBoardDto() {
    }

    public NoticeBoardDto(NoticeBoard noticeBoard) {
        this.id = noticeBoard.getId();
        this.noticeBoardTitle = noticeBoard.getNoticeBoardTitle();
        this.noticeBoardContent = noticeBoard.getNoticeBoardContent();
        this.status = noticeBoard.getStatus();
        this.noticeBoardReplyList = noticeBoard.getNoticeBoardReplyList();
        this.noticeBoardLikesList = noticeBoard.getNoticeBoardLikesList();

        String cur = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-d"));
        LocalDateTime createdDate = noticeBoard.getCreatedDate();
        if (cur.equals(createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-d")))) {
            this.createdDate = createdDate.format(DateTimeFormatter.ofPattern("H:mm"));
        } else {
            this.createdDate = createdDate.format(DateTimeFormatter.ofPattern("yy-MM-d"));
        }

        this.lastModifiedDate = noticeBoard.getLastModifiedDate();
        this.createdBy = noticeBoard.getCreatedBy();
        this.lastModifiedBy = noticeBoard.getLastModifiedBy();

        this.views = noticeBoard.getViews();
        this.likes = noticeBoard.getLikes();
        this.replies = noticeBoard.getReplies();

    }
}
