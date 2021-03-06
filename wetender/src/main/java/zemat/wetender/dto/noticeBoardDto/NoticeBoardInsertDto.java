package zemat.wetender.dto.noticeBoardDto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import zemat.wetender.domain.noticeBoard.NoticeStatus;

import javax.validation.constraints.NotBlank;

@Data
public class NoticeBoardInsertDto {

    private Long id;

    @NotBlank
    private String noticeBoardTitle;

    @NotBlank
    private String noticeBoardContent;

    @NotBlank
    private String status;


    public NoticeBoardInsertDto() {
    }

    public NoticeBoardInsertDto(String noticeBoardTitle, String noticeBoardContent, String status) {
        this.noticeBoardTitle = noticeBoardTitle;
        this.noticeBoardContent = noticeBoardContent;
        this.status = status;
    }
}
