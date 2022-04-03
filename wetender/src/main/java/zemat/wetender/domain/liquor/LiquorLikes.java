package zemat.wetender.domain.liquor;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import zemat.wetender.domain.member.Member;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LiquorLikes {

    @Id
    @GeneratedValue
    @Column(name = "liquor_likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liquor_id")
    private Liquor liquor;

    public LiquorLikes(Member member, Liquor liquor) {
        this.member = member;
        this.liquor = liquor;
    }

}
