package zemat.wetender.security;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements AuditorAware<String> {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManagerImpl authenticationManager() {
        return new AuthenticationManagerImpl();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }

    @Bean
    public AuthenticationProcessingFilter authenticationProcessingFilter() {
        AuthenticationProcessingFilter filter = new AuthenticationProcessingFilter("/member/login");
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandlerImpl("/"));
        filter.setAuthenticationFailureHandler(new AuthenticationFailureHandlerImpl("/loginError"));
        return filter;
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            // ??? ????????? ??? anonymousUser ??? ?????????
            return Optional.empty();
        }
        return Optional.ofNullable(authentication.getName());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
                .and()
            .authorizeRequests()
                .antMatchers("/supporters", "/supporters/**").access("hasRole('SUPPORTER') or hasRole('ADMIN')")
                .antMatchers("/member/mypage").authenticated()
                .anyRequest().permitAll()
                .and()
            .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/member/login")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/member/logout")
                .logoutSuccessUrl("/")
                .addLogoutHandler(new LogoutHandler() {
                    @Override
                    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                        HttpSession session = request.getSession();
                        session.invalidate();
                    }
                })
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        response.sendRedirect("/");
                    }
                })
                .permitAll()
                .and()
            .addFilterBefore(authenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
            .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredUrl("/loginForm")
                .sessionRegistry(sessionRegistry());
    }
}
