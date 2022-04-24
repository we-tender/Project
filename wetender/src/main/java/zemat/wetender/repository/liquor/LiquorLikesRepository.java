package zemat.wetender.repository.liquor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.liquor.LiquorLikes;
import zemat.wetender.domain.member.Member;

import java.util.Optional;

public interface LiquorLikesRepository extends JpaRepository<LiquorLikes, Long> {

    Optional<LiquorLikes> findByMemberAndLiquor(Member member, Liquor liquor);

    // 멤버 아이디로 검색
    Page<LiquorLikes> findByMember(Member member, Pageable pageable);

}
