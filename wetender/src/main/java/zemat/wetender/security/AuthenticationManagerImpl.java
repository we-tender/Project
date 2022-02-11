package zemat.wetender.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthenticationManagerImpl implements AuthenticationManager {

    @Autowired PrincipalDetailsService principalDetailsService;
    @Autowired PasswordEncoder passwordEncoder;

    /**
     * 로그인 인증 관련 각종 처리를 구현
     * - 비밀번호 일치 여부
     * - 유효한 회원ID 인지?
     * - 예외처리
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("2.. AuthenticationManager#authenticate() 실행");
        String memberIdName = (String) authentication.getPrincipal();
        String memberPassword = (String) authentication.getCredentials();
        UserDetails memberDetails = principalDetailsService.loadUserByUsername(memberIdName);

        if (passwordEncoder.matches(memberPassword, memberDetails.getPassword())) {
            System.out.println("2.. 비밀번호 일치");
            System.out.println("2.. 권한");
            for (GrantedAuthority authority : memberDetails.getAuthorities()) {
                System.out.println(authority.getAuthority());
            }
        } else {
            System.out.println("2.. 비밀번호 불일치");
            throw new BadCredentialsException("비밀번호 불일치");
        }

        // principal, credentials, athorities
        return new UsernamePasswordAuthenticationToken(memberDetails, memberPassword, memberDetails.getAuthorities());
        // 첫 번째, 두 번째 파라미터는 Object형 이므로 정보를 추가한 객체를 만들어 사용할 수 있다
        // principal엔 user 객체를 그대로 담고, 비밀번호에는 만료 기간을 추가한 객체를 담을 수가 있겠다
    }
}
