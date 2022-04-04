package zemat.wetender.domain.cocktail;


import lombok.Data;
import lombok.NoArgsConstructor;
import zemat.wetender.domain.base.BasePostEntity;
import zemat.wetender.dto.cocktailDto.reply.CocktailReplyInsertDto;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class CocktailReply extends BasePostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cocktail_reply_id")
    private Long id;

    private String cocktailReplyContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cocktail_id")
    private Cocktail cocktail;

    public CocktailReply(Cocktail cocktail, CocktailReplyInsertDto dto){
        this.cocktailReplyContent = dto.getCocktailReplyContent();
        this.cocktail = cocktail;
    }






}
