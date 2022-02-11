package zemat.wetender.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    private String defaultSuccessUrl;

    public AuthenticationSuccessHandlerImpl(String defaultSuccessUrl) {
        this.defaultSuccessUrl = defaultSuccessUrl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("4.. 로그인성공핸들러 실행");
        System.out.println("4.. null or not : " + SecurityContextHolder.getContext().getAuthentication());
        response.sendRedirect(defaultSuccessUrl);
    }
}
