package zemat.wetender;

import lombok.RequiredArgsConstructor;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import zemat.wetender.domain.cocktail.*;
import zemat.wetender.domain.ingredient.Ingredient;
import zemat.wetender.domain.ingredient.IngredientFile;
import zemat.wetender.domain.ingredient.IngredientFileStore;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.liquor.LiquorFile;
import zemat.wetender.domain.liquor.LiquorFileStore;
import zemat.wetender.domain.member.Member;
import zemat.wetender.domain.suggestion.Suggestion;
import zemat.wetender.repository.IngredientRepository;
import zemat.wetender.repository.LiquorRepository;
import zemat.wetender.repository.MemberRepository;
import zemat.wetender.repository.SuggestionRepository;
import zemat.wetender.service.*;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static zemat.wetender.domain.member.Role.*;

@Component
@RequiredArgsConstructor
public class InitTestData {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final LiquorService liquorService;
    private final LiquorFileStore liquorFileStore;

    private final IngredientService ingredientService;
    private final IngredientFileStore ingredientFileStore;

    private final SupportersService supportersService;
    private final CocktailFileStore cocktailFileStore;

    private final SuggestionService suggestionService;

    private static final String basicPath = "src/main/resources/static/images/initData/";
    // "src/test/resources/static/images/"

    /**
     * 테스트 데이터 - 서버 실행 시 DB에 저장된다
     */

    @PostConstruct
    public void init() throws IOException {
        // 멤버 테스트 객체 삽입
        initMemberData();
        // 식재료 테스트 객체 삽입
        initIngredientData();
        // 주류 테스트 객체 삽입
        initLiquorData();
        // 칵테일 테스트 객체 삽입
        initCocktailData();
    }

    private void initMemberData() {
        Member member1 = new Member("id1", passwordEncoder.encode("pwd1"), "name1", "email1@email.com", "add1", "010-1234-1234");
        Member member2 = new Member("id2", passwordEncoder.encode("pwd2"), "name2", "email2@email.com", "add2", "010-1234-1234");
        Member member3 = new Member("id3", passwordEncoder.encode("pwd3"), "name3", "email3@email.com", "add3", "010-1234-1234");
        member1.setMemberRole(ROLE_USER);
        member2.setMemberRole(ROLE_SUPPORTER);
        member3.setMemberRole(ROLE_ADMIN);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
    }

    private void initCocktailData() throws IOException {
        ////////////////////////////////////////////////상그리아////////////////////////////////////////////////
        // Taste
        List<CocktailTaste> cocktailTastes = new ArrayList<>();
        cocktailTastes.add(new CocktailTaste("맵다"));
        cocktailTastes.add(new CocktailTaste("시다"));
        cocktailTastes.add(new CocktailTaste("상큼하다"));

        // File
        List<CocktailFile> cocktailFiles = cocktailFileStore.storeFiles(getMultipartFiles(basicPath, "sangria", 2));

        // Sequnece
        List<CocktailSequence> cocktailSequences = new ArrayList<>();
        cocktailSequences.add(new CocktailSequence("오렌지와 레몬은 베이킹 소다로 닦아낸 후 물기를 완전히 제거한 상태에서 껍질채로 얇게 슬라이스 한 다음 다시 반으로 잘라서 준비한다. 자를 때 과즙이 너무 빠지지 않도록 조심!"));
        cocktailSequences.add(new CocktailSequence("입구가 넓은 병이나 큰 볼에 와인, 오렌지즙, 트리플 섹을 먼저 넣고 시나몬 가루와 설탕을 더한 후 잘 저어준다."));
        cocktailSequences.add(new CocktailSequence("오렌지와 레몬 조각을 더한 후 국자로 조심스럽게 저어준 후 뚜껑을 닫거나 랩을 씌워서 냉장고에서 24시간 숙성시킨다."));
        cocktailSequences.add(new CocktailSequence("다음날, 서빙 직전에 차가운 레몬에이드를 넣고 저어 내면 완성!"));

        // Ingredient
        List<CocktailIngredient> cocktailIngredients = new ArrayList<>();
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("3개")
                .ingredient(ingredientService.findByIngredientName("오렌지"))
                .build());
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("1개")
                .ingredient(ingredientService.findByIngredientName("레몬"))
                .build());
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("티스푼 1/2")
                .ingredient(ingredientService.findByIngredientName("시나몬가루"))
                .build());
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("50g")
                .ingredient(ingredientService.findByIngredientName("설탕"))
                .build());
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("1병")
                .liquor(liquorService.findByLiquorName("산그레 데 토로"))
                .build());
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("10cl")
                .liquor(liquorService.findByLiquorName("트리플 섹"))
                .build());

        // builder
        Cocktail cocktail = Cocktail.builder()
                .cocktailName("샹그리아")
                .cocktailEName("sangria")
                .cocktailAbv(20)
                .cocktailBase("산그레데토로")
                .cocktailContent("와인 베이스 칵테일로 스페인과 포루투갈의 전통음료")
                .cocktailOneLine("레드와인에 다양한 과일들과 탄산수, 설탕 드을 넣어서 하루 숙성시킨 후 얼음과 같이 먹는다.")
                .cocktailTastes(cocktailTastes)
                .cocktailFiles(cocktailFiles)
                .cocktailSequences(cocktailSequences)
                .cocktailIngredients(cocktailIngredients)
                .cocktailRecommendation(100)
                .build();
        supportersService.cocktailSave(cocktail);

        ////////////////////////////////////////////////모히또////////////////////////////////////////////////
        cocktailTastes.clear();
        cocktailFiles.clear();
        cocktailSequences.clear();
        cocktailIngredients.clear();

        // Taste
        cocktailTastes.add(new CocktailTaste("새콤하다"));
        cocktailTastes.add(new CocktailTaste("달다"));
        cocktailTastes.add(new CocktailTaste("민트맛"));
        cocktailTastes.add(new CocktailTaste("상큼하다"));

        // File
        cocktailFiles = cocktailFileStore.storeFiles(getMultipartFiles(basicPath, "mojito", 2));

        // Sequnece
        cocktailSequences.add(new CocktailSequence("민트잎은 찬물에 담가두었다가 건져 줄기에서 잎을 한장씩 뜯어 준비한다"));
        cocktailSequences.add(new CocktailSequence("라임은 껍질째 사용할 것이므로 베이킹소다를 이용해 깨끗하게 닦고 물기를 제거한다"));
        cocktailSequences.add(new CocktailSequence("데코용 라임 몇 조각은 모양 그대로 썰고 나머지는 웨지 모양으로 썬다"));
        cocktailSequences.add(new CocktailSequence("컵에 라임과 민트를 담는다"));
        cocktailSequences.add(new CocktailSequence("컵에 설탕을 2스푼 넣는다"));
        cocktailSequences.add(new CocktailSequence("민트와 설탕, 라임 즙이 잘 어우러지도록 눌러가며 머들링한다"));
        cocktailSequences.add(new CocktailSequence("얼음은 잘게 부수어 컵에 담는다"));
        cocktailSequences.add(new CocktailSequence("화이트럼을 잔의 1/3가량 채운다"));
        cocktailSequences.add(new CocktailSequence("탄산수로 남은 잔을 채운다"));
        cocktailSequences.add(new CocktailSequence("남은 얼음과 민트잎, 라임 조각을 올려 데코한다"));

        // Ingredient
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("20장")
                .ingredient(ingredientService.findByIngredientName("민트잎"))
                .build());
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("2스푼")
                .ingredient(ingredientService.findByIngredientName("설탕"))
                .build());
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("2개")
                .ingredient(ingredientService.findByIngredientName("라임"))
                .build());
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("적당히")
                .ingredient(ingredientService.findByIngredientName("탄산수"))
                .build());
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("2컵")
                .ingredient(ingredientService.findByIngredientName("얼음"))
                .build());
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("500ml")
                .liquor(liquorService.findByLiquorName("바카디 카르타 블랑카"))
                .build());

        // builder
        cocktail = Cocktail.builder()
                .cocktailName("모히또")
                .cocktailEName("mojito")
                .cocktailAbv(15)
                .cocktailBase("바카디화이트")
                .cocktailContent("새콤하고 시원한 맛과 입안에 가득 퍼지는 민트향! 입 안이 시원해지는 마법을 경험할 수 있다!")
                .cocktailOneLine("모히또 가서 몰디브 한잔~ 논알콜 칵테일로도 만들 수 있어서 카페에서도 쉽게 만날 수 있다. " +
                        "잔 가득 든 얼음 사이로 살짝 짓이겨진 민트 향과 라임쥬스의 새콤달콤함을 느껴보자.")
                .cocktailTastes(cocktailTastes)
                .cocktailFiles(cocktailFiles)
                .cocktailSequences(cocktailSequences)
                .cocktailIngredients(cocktailIngredients)
                .cocktailRecommendation(88)
                .build();
        supportersService.cocktailSave(cocktail);

        ////////////////////////////////////////////////마가리타////////////////////////////////////////////////
        cocktailTastes.clear();
        cocktailFiles.clear();
        cocktailSequences.clear();
        cocktailIngredients.clear();

        // Taste
        cocktailTastes.add(new CocktailTaste("독특하다"));
        cocktailTastes.add(new CocktailTaste("달다"));
        cocktailTastes.add(new CocktailTaste("시다"));
        cocktailTastes.add(new CocktailTaste("쓰다"));
        cocktailTastes.add(new CocktailTaste("짜다"));

        // File
        cocktailFiles = cocktailFileStore.storeFiles(getMultipartFiles(basicPath, "margarita", 2));

        // Sequnece
        cocktailSequences.add(new CocktailSequence("레몬 조각으로 칵테일 글라스 가장자리에 레몬즙을 발라준다"));
        cocktailSequences.add(new CocktailSequence("레몬즙이 묻어있는 가장자리에 소금을 묻힌다"));
        cocktailSequences.add(new CocktailSequence("셰이커에 얼음을 6~70% 넣고 데낄라 1 + 1/2oz 를 넣는다"));
        cocktailSequences.add(new CocktailSequence("트리플 섹 1/2oz 를 넣는다"));
        cocktailSequences.add(new CocktailSequence("데코용 라임을 준비하고 나머지는 짜서 즙을 넣는다"));
        cocktailSequences.add(new CocktailSequence("셰이커를 닫고 힘차게 흔든다"));
        cocktailSequences.add(new CocktailSequence("왼손으로 칵테일 글라스 밑부분을 잡고 음료를 따른다"));

        // Ingredient
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("1스푼")
                .ingredient(ingredientService.findByIngredientName("소금"))
                .build());
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("1스푼")
                .ingredient(ingredientService.findByIngredientName("메이플시럽"))
                .build());
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("4개")
                .ingredient(ingredientService.findByIngredientName("라임"))
                .build());
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("반개")
                .ingredient(ingredientService.findByIngredientName("레몬"))
                .build());
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("2컵")
                .ingredient(ingredientService.findByIngredientName("얼음"))
                .build());
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("1/2oz")
                .liquor(liquorService.findByLiquorName("트리플 섹"))
                .build());
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("1+1/2oz")
                .liquor(liquorService.findByLiquorName("호세 쿠엘보"))
                .build());

        // builder
        cocktail = Cocktail.builder()
                .cocktailName("마가리타")
                .cocktailEName("margarita")
                .cocktailAbv(33)
                .cocktailBase("데킬라")
                .cocktailContent("정열적인 데킬라와 오렌지 큐라소, 라임주스의 단순 조합으로 만들 수 있는 칵테일 마가리타.")
                .cocktailOneLine("라임을 씹고 소금을 햝은 후 데킬라를 한 모금 마시는 음용법인 멕시칸 스타일을 좀 더 세련되게 변화시켜 보자는 취지에서 개발된 칵테일. " +
                        "멕시코 토속주인 데킬라를 세계적인 스피리츠로 끌어올렸다는 의의가 있다.")
                .cocktailTastes(cocktailTastes)
                .cocktailFiles(cocktailFiles)
                .cocktailSequences(cocktailSequences)
                .cocktailIngredients(cocktailIngredients)
                .cocktailRecommendation(110)
                .build();
        supportersService.cocktailSave(cocktail);

        ////////////////////////////////////////////////올드패션드////////////////////////////////////////////////
        cocktailTastes.clear();
        cocktailFiles.clear();
        cocktailSequences.clear();
        cocktailIngredients.clear();

        // Taste
        cocktailTastes.add(new CocktailTaste("달다"));
        cocktailTastes.add(new CocktailTaste("쓰다"));
        cocktailTastes.add(new CocktailTaste("텁텁하다"));

        // File
        cocktailFiles = cocktailFileStore.storeFiles(getMultipartFiles(basicPath, "oldFashioned", 2));

        // Sequnece
        cocktailSequences.add(new CocktailSequence("글라스에 각설탕 1개를 넣는다"));
        cocktailSequences.add(new CocktailSequence("앙고스투라 비터스를 1dash 넣는다"));
        cocktailSequences.add(new CocktailSequence("탄산수를 1/2oz 넣는다"));
        cocktailSequences.add(new CocktailSequence("스푼으로 각설탕을 으깨고 휘저으면서 녹인다"));
        cocktailSequences.add(new CocktailSequence("각얼음을 글라스의 90%까지 채운다"));
        cocktailSequences.add(new CocktailSequence("버번위스키를 넣고 바 스푼ㄹ으로 저어준다"));
        cocktailSequences.add(new CocktailSequence("오렌지 슬라이스와 체리로 장식을 한다"));

        // Ingredient
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("1조각")
                .ingredient(ingredientService.findByIngredientName("설탕"))
                .build());
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("1/2oz")
                .ingredient(ingredientService.findByIngredientName("탄산수"))
                .build());
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("반개")
                .ingredient(ingredientService.findByIngredientName("오렌지"))
                .build());
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("3개")
                .ingredient(ingredientService.findByIngredientName("체리"))
                .build());
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("2컵")
                .ingredient(ingredientService.findByIngredientName("얼음"))
                .build());
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("1/2oz")
                .liquor(liquorService.findByLiquorName("앙고스트라비터스"))
                .build());
        cocktailIngredients.add(CocktailIngredient.builder()
                .cocktailIngredientQty("1+1/2oz")
                .liquor(liquorService.findByLiquorName("짐빔"))
                .build());

        // builder
        cocktail = Cocktail.builder()
                .cocktailName("올드패션드")
                .cocktailEName("old fashioned")
                .cocktailAbv(33)
                .cocktailBase("데킬라")
                .cocktailContent("가장 클래식한 칵테일 중 하나. 향이 좋다고 한 모금씩 마시다보면 금방 취하기 쉽다.")
                .cocktailOneLine("버번 위스키를 베이스로 하며 들어가는 위스키의 종류에 따라 향과 맛이 조금씩 달라진다. " +
                        "위스키의 향과 세월의 맛이 가득한 맛을 음미하다보면 칵테일의 맛을 알게 되는 경험을 할 수 있다.")
                .cocktailTastes(cocktailTastes)
                .cocktailFiles(cocktailFiles)
                .cocktailSequences(cocktailSequences)
                .cocktailIngredients(cocktailIngredients)
                .cocktailRecommendation(157)
                .build();
        supportersService.cocktailSave(cocktail);
    }



    public void initIngredientData() throws IOException {
        List<Ingredient> ingredients = new ArrayList<>();
        List<IngredientFile> ingredientFiles;

        ingredientFiles = ingredientFileStore.storeFiles(getMultipartFiles(basicPath, "lemon", 3));
        ingredients.add(Ingredient.builder()
                .ingredientName("레몬")
                .ingredientEname("lemon")
                .ingredientFiles(ingredientFiles)
                .build());
        ingredientFiles = ingredientFileStore.storeFiles(getMultipartFiles(basicPath, "lime", 3));
        ingredients.add(Ingredient.builder()
                .ingredientName("라임")
                .ingredientEname("lime")
                .ingredientFiles(ingredientFiles)
                .build());
        ingredientFiles = ingredientFileStore.storeFiles(getMultipartFiles(basicPath, "sugar", 3));
        ingredients.add(Ingredient.builder()
                .ingredientName("설탕")
                .ingredientEname("sugar")
                .ingredientFiles(ingredientFiles)
                .build());
        ingredientFiles = ingredientFileStore.storeFiles(getMultipartFiles(basicPath, "carbonatedWater", 4));
        ingredients.add(Ingredient.builder()
                .ingredientName("탄산수")
                .ingredientEname("carbonated water")
                .ingredientFiles(ingredientFiles)
                .build());
        ingredientFiles = ingredientFileStore.storeFiles(getMultipartFiles(basicPath, "orange", 2));
        ingredients.add(Ingredient.builder()
                .ingredientName("오렌지")
                .ingredientEname("orange")
                .ingredientFiles(ingredientFiles)
                .build());
        ingredientFiles = ingredientFileStore.storeFiles(getMultipartFiles(basicPath, "cinnamonPowder", 2));
        ingredients.add(Ingredient.builder()
                .ingredientName("시나몬가루")
                .ingredientEname("cinnamon powder")
                .ingredientFiles(ingredientFiles)
                .build());
        ingredientFiles = ingredientFileStore.storeFiles(getMultipartFiles(basicPath, "ice", 2));
        ingredients.add(Ingredient.builder()
                .ingredientName("얼음")
                .ingredientEname("ice")
                .ingredientFiles(ingredientFiles)
                .build());
        ingredientFiles = ingredientFileStore.storeFiles(getMultipartFiles(basicPath, "mintleaf", 1));
        ingredients.add(Ingredient.builder()
                .ingredientName("민트잎")
                .ingredientEname("mint leaf")
                .ingredientFiles(ingredientFiles)
                .build());
        ingredientFiles = ingredientFileStore.storeFiles(getMultipartFiles(basicPath, "salt", 2));
        ingredients.add(Ingredient.builder()
                .ingredientName("소금")
                .ingredientEname("salt")
                .ingredientFiles(ingredientFiles)
                .build());
        ingredientFiles = ingredientFileStore.storeFiles(getMultipartFiles(basicPath, "mapleSyrup", 2));
        ingredients.add(Ingredient.builder()
                .ingredientName("메이플시럽")
                .ingredientEname("maplesyrup")
                .ingredientFiles(ingredientFiles)
                .build());
        ingredientFiles = ingredientFileStore.storeFiles(getMultipartFiles(basicPath, "cherry", 3));
        ingredients.add(Ingredient.builder()
                .ingredientName("체리")
                .ingredientEname("cherry")
                .ingredientFiles(ingredientFiles)
                .build());

        for (Ingredient ingredient : ingredients) {
            ingredientService.insertIngredient(ingredient);
        }
    }

    public void initLiquorData() throws IOException {
        List<Liquor> liquors = new ArrayList<>();
        List<LiquorFile> liquorFiles;

        liquorFiles = liquorFileStore.storeFiles(getMultipartFiles(basicPath, "jackdaniel", 3));
        liquors.add(Liquor.builder()
                .liquorName("잭다니엘")
                .liquorEname("jackdaniel")
                .liquorAbv(40)
                .liquorOneLine("아메리칸 위스키")
                .liquorContent("잭 다니엘스 테네시 위스키는 세계에서 가장 많이 팔리는 위스키입니다. ")
                .liquorFiles(liquorFiles)
                .build());
        liquorFiles = liquorFileStore.storeFiles(getMultipartFiles(basicPath, "gin", 3));
        liquors.add(Liquor.builder()
                .liquorName("진")
                .liquorEname("gin")
                .liquorAbv(60)
                .liquorOneLine("몰트향이 강하며 단맛이 나는 술")
                .liquorContent("노간주나무 열매를 알코올에 침전시켜 증류하는 과정을 통해 만든 약용주. 네덜란드에서 영국으로 넘어오면서 향이 약해지고 단맛이 없어지는 등 드라이하게 변함")
                .liquorFiles(liquorFiles)
                .build());
        liquorFiles = liquorFileStore.storeFiles(getMultipartFiles(basicPath, "rum", 3));
        liquors.add(Liquor.builder()
                .liquorName("럼")
                .liquorEname("rum")
                .liquorAbv(45)
                .liquorOneLine("사탕수수술")
                .liquorContent("사탕수수를 발효, 증류하여 만든 증류주")
                .liquorFiles(liquorFiles)
                .build());
        liquorFiles = liquorFileStore.storeFiles(getMultipartFiles(basicPath, "vodka", 3));
        liquors.add(Liquor.builder()
                .liquorName("보드카")
                .liquorEname("vodka")
                .liquorAbv(40)
                .liquorOneLine("무색 무취 무향을 특징으로 하는 증류주")
                .liquorContent("치열한 전투에서의 필수품 독하디 독한 술 엔진 연료로 사용 가능")
                .liquorFiles(liquorFiles)
                .build());
        liquorFiles = liquorFileStore.storeFiles(getMultipartFiles(basicPath, "whisky", 3));
        liquors.add(Liquor.builder()
                .liquorName("위스키")
                .liquorEname("whisky")
                .liquorAbv(40)
                .liquorOneLine("스코틀랜드에서 유래한 증류주")
                .liquorContent("기원전 스코틀랜드 지방에서 전해 내려오는 보리를 발효시켜 알코올을 추출하여 만든 전통 증류주")
                .liquorFiles(liquorFiles)
                .build());
        liquorFiles = liquorFileStore.storeFiles(getMultipartFiles(basicPath, "vermuth", 3));
        liquors.add(Liquor.builder()
                .liquorName("베르뭇")
                .liquorEname("vermuth")
                .liquorAbv(40)
                .liquorOneLine("주정강화 와인의 한 종류")
                .liquorContent("레드/화이트 와인에 쑥, 용담, 키니네, 창포 등의 향료나 약초를 넣어 향미를 낸 강화 와인")
                .liquorFiles(liquorFiles)
                .build());
        liquorFiles = liquorFileStore.storeFiles(getMultipartFiles(basicPath, "sangreDeToro", 3));
        liquors.add(Liquor.builder()
                .liquorName("산그레 데 토로")
                .liquorEname("sangre de toro")
                .liquorAbv(13)
                .liquorOneLine("스페인 정통 레드와인의 위상")
                .liquorContent("상그레 데 토로는 '황소의 혈통' 이라는 의미." +
                        "정통 스페인 레드 와인답게 위풍당당하고 강렬한 이미지를 보여주는 와인이다." +
                        "스피이시한 향이 느껴지고 입안에서는 부드러운 질감을 즐길 수 있다." +
                        "스튜와 볶음밥, 소고기 스테이크, 바비큐 등 다양한 음식과 매칭하기 좋다.")
                .liquorFiles(liquorFiles)
                .build());
        liquorFiles = liquorFileStore.storeFiles(getMultipartFiles(basicPath, "tripleSec", 3));
        liquors.add(Liquor.builder()
                .liquorName("트리플 섹")
                .liquorEname("triple sec")
                .liquorAbv(30)
                .liquorOneLine("오렌지 향이 나는 리큐어")
                .liquorContent("트리플 섹은 칵테일에 들어가며, 요리에도 들어가는데, 주로 단 맛을 내고 향을 내는 재료로 쓰인다." +
                        "섹 이란 단어는 프랑스어로 무미건조(dry) 하다는 의미이다. 이름 유래로 세배 더 드라이한 것이라는 설이 있다.")
                .liquorFiles(liquorFiles)
                .build());
        liquorFiles = liquorFileStore.storeFiles(getMultipartFiles(basicPath, "tripleSec", 3));
        liquors.add(Liquor.builder()
                .liquorName("바카디 카르타 블랑카")
                .liquorEname("bacardi carta blanca")
                .liquorAbv(30)
                .liquorOneLine("칵테일에 주로 사용되는 드라이한 맛의 화이트럼")
                .liquorContent("실버, 라이트 럼이라고도 부른다. 사탕수수를 짠 즙을 몇 차례 증류한 럼. 상대적으로 향이 적기에 칵테일 베이스로 가장 많이 쓰임")
                .liquorFiles(liquorFiles)
                .build());
        liquorFiles = liquorFileStore.storeFiles(getMultipartFiles(basicPath, "josecuervo", 2));
        liquors.add(Liquor.builder()
                .liquorName("호세 쿠엘보")
                .liquorEname("jose cuervo especial reposado")
                .liquorAbv(38)
                .liquorOneLine("쿠엘보 골드 라고도 불리는 데킬라 하면 가장 먼저 떠오르는 술!")
                .liquorContent("전 세계 판매 1위를 차지하는 제품으로 전형적인 레포사도(3개월 이상 숙성)한 데킬라. " +
                        "에스페샬 레포사도는 색처럼 달콤하고 향긋한 아가베향과 오크향의 풍미가 가득 느껴진다.")
                .liquorFiles(liquorFiles)
                .build());
        liquorFiles = liquorFileStore.storeFiles(getMultipartFiles(basicPath, "jimbeam", 3));
        liquors.add(Liquor.builder()
                .liquorName("짐빔")
                .liquorEname("jim beam")
                .liquorAbv(40)
                .liquorOneLine("바닐라 카라멜향맛의 부드럽고 달콤한 여운을 주는 짐빔!")
                .liquorContent("순수하고 전통적인 버번의 풍미를 느끼게 해주는 짐빔화이트는 1795년 이래로 변함없는 품질로 사랑받고 있다. " +
                        "최소 4년 이상을 오크통에서 숙성하였기에 오크통의 풍부한 카라멜향을 느낄 수 있다.")
                .liquorFiles(liquorFiles)
                .build());
        liquorFiles = liquorFileStore.storeFiles(getMultipartFiles(basicPath, "angosturaBitters", 2));
        liquors.add(Liquor.builder()
                .liquorName("앙고스트라비터스")
                .liquorEname("angostura bitters")
                .liquorAbv(45)
                .liquorOneLine("앙고스트라가 없는 칵테일 캐비닛은 소금과 후추가 없는 주방과 같다!")
                .liquorContent("44.7도의 높은 도수를 가지고 있음에도 음식이나 음료의 첨가제로 다른 재료와 혼합되어 non-alcoholic이 되는 신기한 제품! " +
                        "허브, 과일, 스피이스의 향기로운 부케가 느껴지는 풍미로 음료와 음식 맛을 한층 향상시킨다!")
                .liquorFiles(liquorFiles)
                .build());

        for (Liquor liquor : liquors) {
            liquorService.insertLiquor(liquor);
        }
    }

    // 스태틱 이미지를 멀티파트파일로 바꿔주는 함수
    private List<MultipartFile> getMultipartFiles(String basicPath, String fileName, int N) throws IOException {
        List<MultipartFile> multipartFiles = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            String filename = basicPath + fileName + i + ".png";

            File file = new File(filename);
            FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());

            try {
                InputStream input = new FileInputStream(file);
                OutputStream os = fileItem.getOutputStream();
                IOUtils.copy(input, os);
                // Or faster..
                // IOUtils.copy(new FileInputStream(file), fileItem.getOutputStream());
            } catch (IOException ex) {
                // do something.
            }

            MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
            multipartFiles.add(multipartFile);
        }
        return multipartFiles;
    }
}
