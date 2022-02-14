package zemat.wetender.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import zemat.wetender.domain.member.Member;
import zemat.wetender.service.MemberService;

/**
 *
 * 시큐리티 설정에서 loginProcessingUrl("/User/login")
 * /login 요청이 오면 자동으로 USerDetailsService 타입으로 IoC 되어 있는 loadUserByUsername 함수가 실행
 *
 */

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberService memberService;

    // 시큐리티 Session(내부 Authentication(내부 UserDetails))
    @Override
    public UserDetails loadUserByUsername(String memberIdName) throws UsernameNotFoundException {
        System.out.println("3.. UserDetailService#loadByUsername() 실행");
        Member findMember = null;
        try {
            findMember = memberService.findByMemberIdName(memberIdName);
        } catch (IllegalStateException e) {
            throw new UsernameNotFoundException("존재하지 않는 아이디입니다!");
        }
        return new PrincipalDetails(findMember);
    }
}
