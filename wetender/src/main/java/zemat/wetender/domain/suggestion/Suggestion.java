package zemat.wetender.domain.suggestion;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zemat.wetender.domain.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Suggestion extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "suggestion_id")
    private Long id;

    private String suggestionTitle;
    private String suggestionContent;


    public Suggestion(String suggestionTitle, String suggestionContent) {
        this.suggestionTitle = suggestionTitle;
        this.suggestionContent = suggestionContent;
    }


}
