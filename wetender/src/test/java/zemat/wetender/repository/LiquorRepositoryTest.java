package zemat.wetender.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.repository.liquor.LiquorRepository;

import java.util.List;

@Transactional
@SpringBootTest
class LiquorRepositoryTest {

    @Autowired
    LiquorRepository liquorRepository;
    
    @Test
    void 주류_상위_추천수20_조회() {
        PageRequest pageRequest = PageRequest.of(0, 20);
        Page<Liquor> page = liquorRepository.findByLiquorRecommendation(pageRequest);
        List<Liquor> content = page.getContent();
        for (Liquor liquor : content) {
            System.out.println("liquor : " + liquor.getLiquorName() + " 추천수 : " + liquor.getLiquorRecommendation());
        }

    }
}