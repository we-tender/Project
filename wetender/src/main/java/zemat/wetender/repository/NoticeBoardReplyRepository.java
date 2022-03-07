package zemat.wetender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zemat.wetender.domain.noticeBoard.NoticeBoardReply;

public interface NoticeBoardReplyRepository extends JpaRepository<NoticeBoardReply, Long> {
}
