package zemat.wetender.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zemat.wetender.dto.liquorDto.LiquorHomeDto;
import zemat.wetender.service.liquor.LiquorService;

import java.util.List;

@SpringBootTest
class LiquorServiceTest {

    @Autowired
    LiquorService liquorService;

    @Test
    public void 주류_상위_추천수20_조회() {
        List<LiquorHomeDto> liquorHomeDtos = liquorService.findTop20ByRecommendation();
        for (LiquorHomeDto liquorHomeDto : liquorHomeDtos) {
            System.out.println("===========================================================");
            System.out.println("이름 = " + liquorHomeDto.getName() + " 추천수 = " + liquorHomeDto.getRecommendation());
            System.out.println("이미지파일 = " + liquorHomeDto.getMainImage());
            System.out.println("===========================================================");
        }
    }
}