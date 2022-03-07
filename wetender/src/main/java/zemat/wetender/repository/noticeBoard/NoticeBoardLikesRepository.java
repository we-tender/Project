package zemat.wetender.repository.noticeBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import zemat.wetender.domain.member.Member;
import zemat.wetender.domain.noticeBoard.NoticeBoard;
import zemat.wetender.domain.noticeBoard.NoticeBoardLikes;

import java.util.Optional;

public interface NoticeBoardLikesRepository extends JpaRepository<NoticeBoardLikes, Long> {

    Optional<NoticeBoardLikes> findByMemberAndNoticeBoard(Member member, NoticeBoard noticeBoard);

}
