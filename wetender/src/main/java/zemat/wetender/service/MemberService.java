package zemat.wetender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.member.Member;
import zemat.wetender.domain.member.Role;
import zemat.wetender.dto.memberDto.MemberJoinForm;
import zemat.wetender.repository.MemberRepository;

import static zemat.wetender.domain.member.Role.ROLE_USER;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public Long join(MemberJoinForm form) {
        /**
         * 비밀번호 확인 체크 로직 추가
         * */
        Member member = new Member(form.getId(), form.getPwd1(), form.getName(), form.getEmail(), form.getAddress(), form.getPhone());
        member.setMemberRole(ROLE_USER);
        Member savedMember = memberRepository.save(member);

        return savedMember.getId();
    }
}
