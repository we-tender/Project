package zemat.wetender.domain.liquor;


import lombok.*;
import zemat.wetender.domain.base.BasePostEntity;
import zemat.wetender.dto.liquorDto.reply.LiquorReplyInsertDto;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class LiquorReply extends BasePostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "liquor_reply_id")
    private Long id;

    private String liquorReplyContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liquor_id")
    private Liquor liquor;


    public LiquorReply(Liquor liquor, LiquorReplyInsertDto Dto) {
        this.liquorReplyContent = Dto.getLiquorReplyContent();
        this.liquor = liquor;
    }


}
