package zemat.wetender.domain.suggestion;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zemat.wetender.domain.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Suggestion extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "suggestion_id")
    private Long id;

    private String suggestionTitle;

    private String suggestionContent;
}
