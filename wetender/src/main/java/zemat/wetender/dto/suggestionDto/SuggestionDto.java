package zemat.wetender.dto.suggestionDto;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;
import zemat.wetender.domain.base.BaseEntity;
import zemat.wetender.domain.suggestion.Suggestion;
import zemat.wetender.domain.suggestion.SuggestionReply;

import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.persistence.GeneratedValue;
import java.time.LocalDateTime;

@Getter @Setter
public class SuggestionDto {


    private Long id;
    private String suggestionTitle;
    private String suggestionContent;
    private List<SuggestionReply> suggestionReplyList = new ArrayList<>();

    private String createdDate;
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

        String cur = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-d"));
        LocalDateTime createdDate = suggestion.getCreatedDate();
        if (cur.equals(createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-d")))) {
            this.createdDate = createdDate.format(DateTimeFormatter.ofPattern("H:mm"));
        } else {
            this.createdDate = createdDate.format(DateTimeFormatter.ofPattern("yy-MM-d"));
        }

        this.lastModifiedDate = suggestion.getLastModifiedDate();
        this.createdBy = suggestion.getCreatedBy();
        this.lastModifiedBy = suggestion.getLastModifiedBy();

        this.views = suggestion.getViews();
        this.likes = suggestion.getLikes();
        this.replies = suggestion.getReplies();
    }

}
