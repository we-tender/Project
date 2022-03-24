package zemat.wetender.dto.noticeBoardDto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class NoticeBoardUpdateDto {

    @NotNull
    private Long id;

    @NotBlank
    private String noticeBoardTitle;

    @NotBlank
    private String noticeBoardContent;

    @NotBlank
    private String status;


    public NoticeBoardUpdateDto(String noticeBoardTitle, String noticeBoardContent, String status) {
        this.noticeBoardTitle = noticeBoardTitle;
        this.noticeBoardContent = noticeBoardContent;
        this.status = status;
    }


}
