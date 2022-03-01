package zemat.wetender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.member.Member;
import zemat.wetender.dto.memberDto.MemberJoinForm;
import zemat.wetender.repository.MemberRepository;

import static zemat.wetender.domain.member.Role.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder pwdEncoder;

    @Transactional
    public Long join(MemberJoinForm form) {
        duplicateMember(form.getIdname());
        confirmPassword(form.getPwd1(), form.getPwd2());
        /**
         * 비밀번호 확인 체크 로직 -> javascript
         * */

        String encodedPwd = pwdEncoder.encode(form.getPwd1());
        Member member = new Member(form.getIdname(), encodedPwd, form.getName(), form.getEmail(), form.getAddress(), form.getPhone());
        member.setMemberRole(ROLE_USER);
        Member savedMember = memberRepository.save(member);

        return savedMember.getId();
    }

    public void duplicateMember(String memberIdName) {
        memberRepository.findByMemberIdName(memberIdName).ifPresent(m -> {
            throw new IllegalStateException(m.getMemberIdName() + " - 이미 존재하는 아이디입니다!");
        });
    }

    public void confirmPassword(String password1, String password2) {
        if (!password1.equals(password2)) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다!");
        }
    }

    public Member findByMemberIdName(String memberIdName) {
        return memberRepository.findByMemberIdName(memberIdName).orElseThrow(() -> new IllegalStateException("존재하지 않는 아이디입니다!"));
    }
}
