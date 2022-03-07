package zemat.wetender.domain.noticeBoard;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zemat.wetender.domain.base.BasePostEntity;
import zemat.wetender.dto.noticeBoardDto.NoticeBoardInsertDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class NoticeBoard extends BasePostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noticeBoard_id")
    private Long id;

    private String noticeBoardTitle;
    private String noticeBoardContent;

    // ALL. Part
    private String status;

    @OneToMany(mappedBy = "noticeBoard")
    private List<NoticeBoardReply> noticeBoardReplyList = new ArrayList<>();


    @OneToMany(mappedBy = "noticeBoard", cascade = CascadeType.ALL)
    private List<NoticeBoardLikes> noticeBoardLikesList = new ArrayList<>();


    public NoticeBoard(NoticeBoardInsertDto noticeBoardInsertDto) {
        this.id = noticeBoardInsertDto.getId();
        this.noticeBoardTitle = noticeBoardInsertDto.getNoticeBoardTitle();
        this.noticeBoardContent = noticeBoardInsertDto.getNoticeBoardContent();
        this.status = noticeBoardInsertDto.getStatus();
    }


}
