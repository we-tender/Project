package zemat.wetender.domain.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zemat.wetender.domain.base.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter

public class member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String memberIdName;

    private String memberName;

    private String memberPassword;

    private String memberNick;

    private String memberAdress;

    private String memberEmail;

    private int memberFlag;

    private LocalDateTime memberFlagDate;

    @Enumerated(value = EnumType.STRING)
    private Role role;
}
