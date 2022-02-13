package zemat.wetender.domain.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zemat.wetender.domain.base.BaseEntity;
import zemat.wetender.domain.base.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String memberIdName;

    private String memberPassword;

    private String memberName;

    private String memberNick;

    private String memberEmail;

    private String memberAddress;

    private String memberPhone;

    private Boolean memberExpiredFlag;

    private LocalDateTime memberExpiredDate;

    @Enumerated(value = EnumType.STRING)
    private Role memberRole;

    public Member(String idName, String password, String name, String email, String address, String phone) {
        this(idName, password, name, null, email, address, phone, false, null);
    }

    public Member(String idName, String password, String name, String nick, String email, String address, String phone, Boolean expiredFlag, LocalDateTime expiredDate) {
        this.memberIdName = idName;
        this.memberPassword = password;
        this.memberName = name;
        this.memberNick = nick;
        this.memberEmail = email;
        this.memberAddress = address;
        this.memberPhone = phone;
        this.memberExpiredFlag = expiredFlag;
        this.memberExpiredDate = expiredDate;
    }
}
