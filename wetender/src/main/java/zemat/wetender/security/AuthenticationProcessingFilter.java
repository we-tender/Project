package zemat.wetender.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    protected AuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        System.out.println("1.. AuthenticationProcessingFilter 스프링시큐리티 인증 필터 시작");

        String memberIdName = request.getParameter("idname");
        String memberPassword = request.getParameter("pwd");
        System.out.println("1.. 입력값 - ID:[" + memberIdName + "]PW:[" + memberPassword + "]");
        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(memberIdName, memberPassword));
        // 아이디, 패스워드가 담긴 토큰으로 authenticate 메소드를 실행하고 (토큰도 Authenticate의 구현체임)
        // authenticate 메소드에서 UserDetails를 가진 Authentication 객체를 반환하도록 한다.
        // 속성 2개짜리 생성자를 실행한 클래스를 인자로 받음 -> DB에 저장된 ID의 비밀번호와 비교 -> 일치하면(인증) 속성 3개짜리 생성자로 토큰을 재발행하여 (인증된 객체)리턴한다
    }
}
/**
 * Authentication 객체를 반환
 * Authentication 을 상속받는 UsernamePasswordAuthenticationToken 객체가 리턴됨
 * UsernamePasswordAuthenticationToken 의 주요 멤버변수 principal, credentials, authorities 가 있는데
 * principal, credentials 은 Object 형, authorities 는 Collection<GrantedAuthority> 형의 컬렉션 객체임
 * pricicpal 에는 Member 객체를 그대로, credentials 에는 password를 string으로 저장 하였으며 authorities 는 Role 을 갖고 있음
 */
