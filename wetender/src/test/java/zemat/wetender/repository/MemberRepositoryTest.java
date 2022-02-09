package zemat.wetender.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.member.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @PersistenceContext EntityManager em;
    @Autowired MemberRepository memberRepository;

    @Test
    public void basicCRUD() {
        Member member = new Member("id1", "pwd1", "name1", "email1", "add1", "010-1234-1234");

        // create
        Member saveMember = memberRepository.save(member);
        assertThat(saveMember.getId()).isSameAs(member.getId());

        // update
        member.setMemberNick("changedNick");

        em.flush();
        em.clear();

        // read
        Member findMember = memberRepository.findById(member.getId()).get();
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getMemberNick()).isEqualTo("changedNick");

        System.out.println("member.getCreatedBy() = " + member.getCreatedBy());
        System.out.println("member.getLastModifiedBy() = " + member.getLastModifiedBy());
        System.out.println("member.getCreatedDate() = " + member.getCreatedDate());
        System.out.println("member.getLastModifiedDate() = " + member.getLastModifiedDate());
    }

    @Test
    public void findByMemberIdName() {
        Member member = new Member("id1", "pwd1", "name1", "email1", "add1", "010-1234-1234");
        memberRepository.save(member);

        em.flush();
        em.clear();

        Member findMember = memberRepository.findByMemberIdName(member.getMemberIdName()).orElseThrow();
        assertThat(findMember.getMemberIdName()).isEqualTo(member.getMemberIdName());
    }
}