package zemat.wetender.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {
    private String defaultFailureUrl;

    public AuthenticationFailureHandlerImpl(String defualtFailureUrl) {
        this.defaultFailureUrl = defualtFailureUrl;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 실패 로그를 남긴다
        // 실패 이벤트를 발송한다
        System.out.println("로그인 실패!!");
//        response.sendRedirect(defaultFailureUrl);
        request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
    }
}
