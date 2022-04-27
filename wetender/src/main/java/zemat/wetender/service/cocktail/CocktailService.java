package zemat.wetender.service.cocktail;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.cocktail.*;
import zemat.wetender.domain.ingredient.Ingredient;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.dto.cocktailDto.CocktailHomeDto;
import zemat.wetender.dto.supportersDto.CocktailUpdateForm;
import zemat.wetender.repository.cocktail.CocktailRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CocktailService {

    private final CocktailRepository cocktailRepository;

    public List<CocktailHomeDto> findTop20ByRecommendation() {
        PageRequest pageRequest = PageRequest.of(0, 4);
        Page<Cocktail> page = cocktailRepository.findByCocktailRecommendation(pageRequest);
        List<CocktailHomeDto> cocktailHomeDtos = page.map(CocktailHomeDto::new).getContent();
        return cocktailHomeDtos;
    }

    // 메인 화면에 좋아요가 많은 순으로 칵테일을 보여주는 함수
    public List<CocktailHomeDto> findTop20ByLikes() {
        PageRequest pageRequest = PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "likes"));
        Page<Cocktail> page = cocktailRepository.findAll(pageRequest);
        List<CocktailHomeDto> cocktailHomeDtos = page.map(CocktailHomeDto::new).getContent();
        return cocktailHomeDtos;
    }




    public Cocktail findById(Long cocktailId){
        return cocktailRepository.findById(cocktailId).get();
    }


    public Page<Cocktail> pageFindKeyword(Pageable pageable, String keyword){
        return cocktailRepository.findByCocktailNameContainingIgnoreCaseOrCocktailEnameContainingIgnoreCase(pageable,keyword,keyword);
    }

    @Transactional
    public void deleteById(Long cocktailId){
        cocktailRepository.deleteById(cocktailId);
    }

    // 조회수 +1
    @Transactional
    public void viewsUp(Long cocktailId) {
        cocktailRepository.getById(cocktailId).viewsAdd();
    }

}
