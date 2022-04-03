package zemat.wetender.repository.liquor;

import org.springframework.data.jpa.repository.JpaRepository;
import zemat.wetender.domain.liquor.LiquorReply;

public interface LiquorReplyRepository extends JpaRepository<LiquorReply, Long> {
}
