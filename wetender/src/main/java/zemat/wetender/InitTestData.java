package zemat.wetender;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.domain.ingredient.Ingredient;
import zemat.wetender.domain.ingredient.IngredientFile;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.liquor.LiquorFile;
import zemat.wetender.domain.member.Member;
import zemat.wetender.domain.suggestion.Suggestion;
import zemat.wetender.repository.IngredientRepository;
import zemat.wetender.repository.LiquorRepository;
import zemat.wetender.repository.MemberRepository;
import zemat.wetender.repository.SuggestionRepository;
import zemat.wetender.service.SuggestionService;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;

import static zemat.wetender.domain.member.Role.*;

@Component
@RequiredArgsConstructor
public class InitTestData {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final LiquorRepository liquorRepository;
    private final IngredientRepository ingredientRepository;

    private final SuggestionService suggestionService;

    /**
     * 테스트 데이터 - 서버 실행 시 DB에 저장된다
     */

    @PostConstruct
    public void init() {
        Member member1 = new Member("id1", passwordEncoder.encode("pwd1"), "name1", "email1@email.com", "add1", "010-1234-1234");
        Member member2 = new Member("id2", passwordEncoder.encode("pwd2"), "name2", "email2@email.com", "add2", "010-1234-1234");
        Member member3 = new Member("id3", passwordEncoder.encode("pwd3"), "name3", "email3@email.com", "add3", "010-1234-1234");
        member1.setMemberRole(ROLE_USER);
        member2.setMemberRole(ROLE_SUPPORTER);
        member3.setMemberRole(ROLE_ADMIN);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);







        //주류
        List<LiquorFile> storeFileResult = new ArrayList<>();
        Liquor liquor1 = Liquor.builder()
                .liquorName("잭다니엘")
                .liquorEname("Jack Daniel's")
                .liquorAbv(40)
                .liquorOneLine("아메리칸 위스키")
                .liquorContent("잭 다니엘스 테네시 위스키는 세계에서 가장 많이 팔리는 위스키입니다. ")
                .liquorFiles(storeFileResult)
                .build();

        Liquor liquor2 = Liquor.builder()
                .liquorName("위스키")
                .liquorEname("whisky")
                .liquorAbv(40)
                .liquorOneLine("증류주")
                .liquorContent("위스키")
                .liquorFiles(storeFileResult)
                .build();

        Liquor liquor3 = Liquor.builder()
                .liquorName("럼")
                .liquorEname("rum")
                .liquorFiles(storeFileResult)
                .build();

        Liquor liquor4 = Liquor.builder()
                .liquorName("진")
                .liquorEname("Jin")
                .liquorFiles(storeFileResult)
                .build();

        Liquor liquor5 = Liquor.builder()
                .liquorName("보드카")
                .liquorEname("Vodka")
                .liquorFiles(storeFileResult)
                .build();
        Liquor liquor6 = Liquor.builder()
                .liquorName("스윗 베르뭇")
                .liquorEname("sweet vermuth")
                .liquorFiles(storeFileResult)
                .build();

        liquorRepository.save(liquor1);
        liquorRepository.save(liquor2);
        liquorRepository.save(liquor3);
        liquorRepository.save(liquor4);
        liquorRepository.save(liquor5);
        liquorRepository.save(liquor6);

        for(int i = 0; i <= 30; i++) {
            Liquor liquor7 = Liquor.builder()
                    .liquorName("더미 데이터" +i)
                    .liquorEname("dummy" + i)
                    .liquorFiles(storeFileResult)
                    .build();
            liquorRepository.save(liquor7);
        }

        // 식재료
        List<IngredientFile> storeFileResult2 = new ArrayList<>();

        Ingredient ingredient = Ingredient.builder()
                .ingredientName("레몬")
                .ingredientEname("lemon")
                .ingredientFiles(storeFileResult2)
                .build();

        Ingredient ingredient1 = Ingredient.builder()
                .ingredientName("라임")
                .ingredientEname("lime")
                .ingredientFiles(storeFileResult2)
                .build();

        Ingredient ingredient2 = Ingredient.builder()
                .ingredientName("설탕")
                .ingredientEname("sugar")
                .ingredientFiles(storeFileResult2)
                .build();

        ingredientRepository.save(ingredient);
        ingredientRepository.save(ingredient1);
        ingredientRepository.save(ingredient2);

        for(int i = 0; i <= 30; i++) {
            Ingredient ingredient3 = Ingredient.builder()
                    .ingredientName("더미 데이터" + i)
                    .ingredientEname("dummy" + i)
                    .ingredientFiles(storeFileResult2)
                    .build();
            ingredientRepository.save(ingredient3);
        }


        // Suggestion init Data
//        Suggestion suggestion1 = new Suggestion("1", "1");
//        Suggestion suggestion2 = new Suggestion("2", "2");
//        Suggestion suggestion3 = new Suggestion("3", "3");
//        Suggestion suggestion4 = new Suggestion("4", "4");
//        Suggestion suggestion5 = new Suggestion("5", "5");
//        Suggestion suggestion6 = new Suggestion("6", "6");
//        Suggestion suggestion7 = new Suggestion("7", "7");
//        Suggestion suggestion8 = new Suggestion("8", "8");
//        suggestionService.insert(suggestion1);
//        suggestionService.insert(suggestion2);
//        suggestionService.insert(suggestion3);
//        suggestionService.insert(suggestion4);
//        suggestionService.insert(suggestion5);
//        suggestionService.insert(suggestion6);
//        suggestionService.insert(suggestion7);
//        suggestionService.insert(suggestion8);
        // Suggestion init Data

    }
}
