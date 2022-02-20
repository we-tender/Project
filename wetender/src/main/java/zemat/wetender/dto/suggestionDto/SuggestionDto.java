package zemat.wetender.dto.suggestionDto;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;
import zemat.wetender.domain.base.BaseEntity;
import zemat.wetender.domain.suggestion.Suggestion;

import javax.persistence.GeneratedValue;
import java.time.LocalDateTime;

@Getter @Setter
public class SuggestionDto {


    private Long id;
    private String suggestionTitle;
    private String suggestionContent;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String createdBy;
    private String lastModifiedBy;



    public SuggestionDto(Suggestion suggestion) {
        this.id = suggestion.getId();
        this.suggestionTitle = suggestion.getSuggestionTitle();
        this.suggestionContent = suggestion.getSuggestionContent();

        this.createdDate = suggestion.getCreatedDate();
        this.lastModifiedDate = suggestion.getLastModifiedDate();
        this.createdBy = suggestion.getCreatedBy();
        this.lastModifiedBy = suggestion.getLastModifiedBy();
    }

}
