package zemat.wetender.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import zemat.wetender.domain.member.Member;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인 진행
 * 로그인 진행 완료가 되면 시큐리티 session을 만들어줌 (Security ContextHolder)
 * 오브젝트 타입 => Authentication 타입 객체
 * Authentication 안에 User 정보가 있어야 됨
 * User 오브젝트 타입 => UserDetails 타입 객체
 *
 * Security Session => Authentication => UserDetails(PrincipalDetails)
 *
 */

@Getter @Setter
@EqualsAndHashCode
public class PrincipalDetails implements UserDetails {

    private Member member;

    public PrincipalDetails(Member member) {
        this.member = member;
    }

    // 해당 유저의 권한을 리턴
   @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       Collection<GrantedAuthority> collect = new ArrayList<>();
       collect.add(new GrantedAuthority() {
           @Override
           public String getAuthority() {
               return String.valueOf(member.getMemberRole());
           }
       });
        return collect;
    }

    @Override
    public String getPassword() {
        return member.getMemberPassword();
    }

    @Override
    public String getUsername() {
        return member.getMemberIdName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
