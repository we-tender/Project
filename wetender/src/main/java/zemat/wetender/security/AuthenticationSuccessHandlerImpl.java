package zemat.wetender.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        HttpSession session = request.getSession();
        if (session != null) {
            String prevUrl = (String) session.getAttribute("prevUrl");
            System.out.println("4.. prevUrl = " + prevUrl);
            if (prevUrl != null) {
                session.removeAttribute("prevUrl");
                response.sendRedirect(prevUrl);
            } else {
                response.sendRedirect(defaultSuccessUrl);
            }
        } else {
            response.sendRedirect(defaultSuccessUrl);
        }
    }
}
