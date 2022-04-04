package zemat.wetender.domain.cocktail;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.member.Member;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CocktailLikes {

    @Id
    @GeneratedValue
    @Column(name = "cocktail_likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cocktail_id")
    private Cocktail cocktail;

    public CocktailLikes(Member member, Cocktail cocktail) {
        this.member = member;
        this.cocktail = cocktail;
    }

}
