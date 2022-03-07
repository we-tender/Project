package zemat.wetender.repository.noticeBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import zemat.wetender.domain.noticeBoard.NoticeBoardReply;

public interface NoticeBoardReplyRepository extends JpaRepository<NoticeBoardReply, Long> {
}
