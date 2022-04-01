package zemat.wetender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zemat.wetender.domain.liquor.LiquorReply;

public interface LiquorReplyRepository extends JpaRepository<LiquorReply, Long> {
}
