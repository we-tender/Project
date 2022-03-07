package zemat.wetender.domain.noticeBoard;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zemat.wetender.domain.base.BasePostEntity;
import zemat.wetender.dto.noticeBoardDto.NoticeBoardReplyInsertDto;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class NoticeBoardReply extends BasePostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_board_reply_id")
    private Long id;

    private String noticeBoardReplyContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_board_id")
    private NoticeBoard noticeBoard;

    public NoticeBoardReply(NoticeBoard noticeBoard, NoticeBoardReplyInsertDto noticeBoardReplyInsertDto) {
        this.noticeBoardReplyContent = noticeBoardReplyInsertDto.getNoticeBoardReplyContent();
        this.noticeBoard = noticeBoard;
    }
}
