package zemat.wetender.domain.suggestion;

import java.util.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zemat.wetender.domain.base.BaseEntity;
import zemat.wetender.dto.suggestionDto.SuggestionDto;
import zemat.wetender.dto.suggestionDto.SuggestionInsertDto;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Suggestion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suggestion_id")
    private Long id;

    private String suggestionTitle;
    private String suggestionContent;


    @OneToMany(mappedBy = "suggestion")
    private List<SuggestionReply> suggestionReplyList = new ArrayList<>();

    public Suggestion(String suggestionTitle, String suggestionContent) {
        this.suggestionTitle = suggestionTitle;
        this.suggestionContent = suggestionContent;
    }

    public Suggestion(SuggestionInsertDto suggestionInsertDto) {
        this.id = suggestionInsertDto.getId();
        this.suggestionTitle = suggestionInsertDto.getSuggestionTitle();
        this.suggestionContent = suggestionInsertDto.getSuggestionContent();
    }

    public Suggestion(SuggestionDto suggestionDto) {
        this.id = suggestionDto.getId();
        this.suggestionTitle = suggestionDto.getSuggestionTitle();
        this.suggestionContent = suggestionDto.getSuggestionContent();
    }



}
