package zemat.wetender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.dto.cocktailDto.CocktailHomeDto;
import zemat.wetender.dto.liquorDto.LiquorHomeDto;
import zemat.wetender.repository.LiquorRepository;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LiquorService {

    private final LiquorRepository liquorRepository;

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
}
