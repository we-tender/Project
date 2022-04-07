package zemat.wetender.service.liquor;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.dto.liquorDto.LiquorHomeDto;
import zemat.wetender.repository.liquor.LiquorRepository;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LiquorService {

    private final LiquorRepository liquorRepository;


    // 조회수 +1
    @Transactional
    public void viewsUp(Long liquorId) {
        liquorRepository.getById(liquorId).viewsAdd();
    }

    @Transactional
    public Long insertLiquor(Liquor liquor) {
        Liquor savedLiquor = liquorRepository.save(liquor);
        return savedLiquor.getId();
    }

    public Liquor findById(Long liquorId){
        return liquorRepository.findById(liquorId).get();
    }

    public Page<Liquor> pageFindAll(Pageable pageable){
        return liquorRepository.findAll(pageable);
    }

    public Page<Liquor> pageFindKeyword(Pageable pageable,String keyword){
        return liquorRepository.findByLiquorNameContainingIgnoreCaseOrLiquorEnameContainingIgnoreCase(pageable,keyword,keyword);
    }

    @Transactional
    public Liquor findByLiquorName(String liquorName) {
        Liquor liquor = liquorRepository.findByLiquorName(liquorName).orElseThrow(() -> new IllegalStateException("존재하지 않는 술입니다!"));
        liquor.getCocktailLiquors().size();
        return liquor;
    }

    public List<LiquorHomeDto> findTop20ByRecommendation() {
        PageRequest pageRequest = PageRequest.of(0, 4);
        Page<Liquor> page = liquorRepository.findByLiquorRecommendation(pageRequest);
        List<LiquorHomeDto> liquorHomeDtos = page.map(LiquorHomeDto::new).getContent();
        return liquorHomeDtos;
    }

    @Transactional
    public void deleteById(Long liquorId){
        liquorRepository.deleteById(liquorId);
    }
}
