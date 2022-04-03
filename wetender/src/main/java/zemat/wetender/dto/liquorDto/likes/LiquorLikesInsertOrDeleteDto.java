package zemat.wetender.dto.liquorDto.likes;

import lombok.Data;

@Data
public class LiquorLikesInsertOrDeleteDto {

    private Long liquorId;
    private String memberName;

}
