package zemat.wetender.domain.suggestion;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.util.Lazy;
import zemat.wetender.domain.base.BaseEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class SuggestionReply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suggestion_reply_id")
    private Long id;

    private String suggestionReplyContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suggestion_id")
    private Suggestion suggestion;

    public SuggestionReply(String suggestionReplyContent, Suggestion suggestion) {
        this.suggestionReplyContent = suggestionReplyContent;
        this.suggestion = suggestion;
    }


}
