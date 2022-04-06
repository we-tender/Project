package zemat.wetender.dto.suggestionDto;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;
import zemat.wetender.domain.base.BaseEntity;
import zemat.wetender.domain.suggestion.Suggestion;
import zemat.wetender.domain.suggestion.SuggestionReply;

import java.util.*;

import javax.persistence.GeneratedValue;
import java.time.LocalDateTime;

@Getter @Setter
public class SuggestionDto {


    private Long id;
    private String suggestionTitle;
    private String suggestionContent;
    private List<SuggestionReply> suggestionReplyList = new ArrayList<>();

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String createdBy;
    private String lastModifiedBy;

    private long views;
    private long likes;
    private long replies;

    public SuggestionDto() {
    }

    public SuggestionDto(Suggestion suggestion) {
        this.id = suggestion.getId();
        this.suggestionTitle = suggestion.getSuggestionTitle();
        this.suggestionContent = suggestion.getSuggestionContent();
        this.suggestionReplyList = suggestion.getSuggestionReplyList();

        this.createdDate = suggestion.getCreatedDate();
        this.lastModifiedDate = suggestion.getLastModifiedDate();
        this.createdBy = suggestion.getCreatedBy();
        this.lastModifiedBy = suggestion.getLastModifiedBy();

        this.views = suggestion.getViews();
        this.likes = suggestion.getLikes();
        this.replies = suggestion.getReplies();
    }

}
