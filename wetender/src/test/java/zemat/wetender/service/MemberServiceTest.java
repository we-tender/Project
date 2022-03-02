package zemat.wetender.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.member.Member;
import zemat.wetender.dto.memberDto.MemberJoinForm;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @PersistenceContext EntityManager em;

    @Test
    public void 회원가입() {
        MemberJoinForm form = new MemberJoinForm("id1", "pwd1", "pwd1", "name1", "email1", "add1", "010-1234-1234");
        Long joinMemberId = memberService.join(form);

        em.flush();
        em.clear();

        Member findMember = em.find(Member.class, joinMemberId);
        assertThat(joinMemberId).isEqualTo(findMember.getId());
    }

    @Test
    public void 중복회원가입() {
        memberService.join(new MemberJoinForm("id1", "pwd1", "pwd1", "name1", "email1", "add1", "010-1234-1234"));

        em.flush();
        em.clear();

//        memberService.join(new MemberJoinForm("id1", "pwd1234", "pwd1234", "name2", "email2", "add2", "010-1234-1234"));
        assertThrows(IllegalStateException.class, () -> memberService.join(new MemberJoinForm("id1", "pwd1234", "pwd1234", "name2", "email2", "add2", "010-1234-1234")));
    }

    @Test void 비밀번호불일치() {
        assertThrows(IllegalStateException.class, () -> memberService.join(new MemberJoinForm("id1", "pwd1", "pwd2", "name1", "email1", "add1", "010-1234-1234")));
    }
}