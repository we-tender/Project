package zemat.wetender.domain.noticeBoard;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zemat.wetender.domain.member.Member;
import zemat.wetender.domain.noticeBoard.NoticeBoard;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeBoardLikes {

    @Id @GeneratedValue
    @Column(name = "notice_board_likes_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "notice_board_id")
    private NoticeBoard noticeBoard;

    public NoticeBoardLikes(Member member, NoticeBoard noticeBoard) {
        this.member = member;
        this.noticeBoard = noticeBoard;
    }
}
