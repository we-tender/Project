package zemat.wetender.dto.supportersDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Data
public class TestVO {

    private Long seq_bno;
    private String title, content, writer;
    private Date regdate, updatedate;

    private int replyCnt;
    private List<TestFileVO> attachList;
}
