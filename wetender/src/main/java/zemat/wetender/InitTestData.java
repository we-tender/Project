package zemat.wetender;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import zemat.wetender.domain.member.Member;
import zemat.wetender.repository.MemberRepository;

import javax.annotation.PostConstruct;

import static zemat.wetender.domain.member.Role.*;

@Component
@RequiredArgsConstructor
public class InitTestData {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 테스트 데이터 - 서버 실행 시 DB에 저장된다
     */

    @PostConstruct
    public void init() {
        Member member1 = new Member("id1", passwordEncoder.encode("pwd1"), "name1", "email1@email.com", "add1", "010-1234-1234");
        Member member2 = new Member("id2", passwordEncoder.encode("pwd2"), "name2", "email2@email.com", "add2", "010-1234-1234");
        Member member3 = new Member("id3", passwordEncoder.encode("pwd3"), "name3", "email3@email.com", "add3", "010-1234-1234");
        member1.setMemberRole(ROLE_USER);
        member2.setMemberRole(ROLE_SUPPORTER);
        member3.setMemberRole(ROLE_ADMIN);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
    }
}
