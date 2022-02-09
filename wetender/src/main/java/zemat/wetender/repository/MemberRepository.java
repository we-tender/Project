package zemat.wetender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zemat.wetender.domain.member.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberIdName(String memberIdName);
}
