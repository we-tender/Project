package zemat.wetender.domain.noticeBoard;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zemat.wetender.domain.base.BaseEntity;
import zemat.wetender.domain.base.BasePostEntity;
import zemat.wetender.dto.noticeBoardDto.NoticeBoardDto;
import zemat.wetender.dto.noticeBoardDto.NoticeBoardInsertDto;

import javax.persistence.*;

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

    // 전체공개, 일부분 공개 여부 설정
    // ALL. Part
    @Enumerated(EnumType.STRING)
    private NoticeStatus status;


    public NoticeBoard(NoticeBoardInsertDto noticeBoardInsertDto) {
        this.id = noticeBoardInsertDto.getId();
        this.noticeBoardTitle = noticeBoardInsertDto.getNoticeBoardTitle();
        this.noticeBoardContent = noticeBoardInsertDto.getNoticeBoardContent();
        this.status = noticeBoardInsertDto.getStatus();
    }


}
