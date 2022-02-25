package zemat.wetender;

import lombok.RequiredArgsConstructor;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import zemat.wetender.domain.cocktail.Cocktail;
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
import zemat.wetender.service.SuggestionService;

import javax.annotation.PostConstruct;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static zemat.wetender.domain.member.Role.*;

@Component
@RequiredArgsConstructor
public class InitTestData {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final LiquorRepository liquorRepository;
    private final LiquorFileStore liquorFileStore;

    private final IngredientRepository ingredientRepository;
    private final IngredientFileStore ingredientFileStore;

    private final SuggestionService suggestionService;

    private static final String basicPath = "src/java/resources/static/images/initData/";

    /**
     * 테스트 데이터 - 서버 실행 시 DB에 저장된다
     */

    @PostConstruct
    public void init() throws IOException {
        Member member1 = new Member("id1", passwordEncoder.encode("pwd1"), "name1", "email1@email.com", "add1", "010-1234-1234");
        Member member2 = new Member("id2", passwordEncoder.encode("pwd2"), "name2", "email2@email.com", "add2", "010-1234-1234");
        Member member3 = new Member("id3", passwordEncoder.encode("pwd3"), "name3", "email3@email.com", "add3", "010-1234-1234");
        member1.setMemberRole(ROLE_USER);
        member2.setMemberRole(ROLE_SUPPORTER);
        member3.setMemberRole(ROLE_ADMIN);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        initIngredientData();
        initLiquorData();

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
    }

    public void initIngredientData() throws IOException {
        List<Ingredient> ingredients = new ArrayList<>();
        List<IngredientFile> ingredientFiles;

        ingredientFiles = ingredientFileStore.storeFiles(getMultipartFiles("src/test/resources/static/images/", "lemon", 3));
        ingredients.add(Ingredient.builder()
                .ingredientName("레몬")
                .ingredientEname("lemon")
                .ingredientFiles(ingredientFiles)
                .build());
        ingredientFiles = ingredientFileStore.storeFiles(getMultipartFiles("src/test/resources/static/images/", "lime", 3));
        ingredients.add(Ingredient.builder()
                .ingredientName("라임")
                .ingredientEname("lime")
                .ingredientFiles(ingredientFiles)
                .build());
        ingredientFiles = ingredientFileStore.storeFiles(getMultipartFiles("src/test/resources/static/images/", "sugar", 3));
        ingredients.add(Ingredient.builder()
                .ingredientName("설탕")
                .ingredientEname("sugar")
                .ingredientFiles(ingredientFiles)
                .build());

        for (Ingredient ingredient : ingredients) {
            ingredientRepository.save(ingredient);
        }
    }

    public void initLiquorData() throws IOException {
        List<Liquor> liquors = new ArrayList<>();
        List<LiquorFile> liquorFiles;

        liquorFiles = liquorFileStore.storeFiles(getMultipartFiles("src/test/resources/static/images/", "jackdaniel", 3));
        liquors.add(Liquor.builder()
                .liquorName("잭다니엘")
                .liquorEname("jackdaniel")
                .liquorAbv(40)
                .liquorOneLine("아메리칸 위스키")
                .liquorContent("잭 다니엘스 테네시 위스키는 세계에서 가장 많이 팔리는 위스키입니다. ")
                .liquorFiles(liquorFiles)
                .build());
        liquorFiles = liquorFileStore.storeFiles(getMultipartFiles("src/test/resources/static/images/", "gin", 3));
        liquors.add(Liquor.builder()
                .liquorName("진")
                .liquorEname("gin")
                .liquorAbv(60)
                .liquorOneLine("몰트향이 강하며 단맛이 나는 술")
                .liquorContent("노간주나무 열매를 알코올에 침전시켜 증류하는 과정을 통해 만든 약용주. 네덜란드에서 영국으로 넘어오면서 향이 약해지고 단맛이 없어지는 등 드라이하게 변함")
                .liquorFiles(liquorFiles)
                .build());
        liquorFiles = liquorFileStore.storeFiles(getMultipartFiles("src/test/resources/static/images/", "rum", 3));
        liquors.add(Liquor.builder()
                .liquorName("럼")
                .liquorEname("rum")
                .liquorAbv(45)
                .liquorOneLine("사탕수수술")
                .liquorContent("사탕수수를 발효, 증류하여 만든 증류주")
                .liquorFiles(liquorFiles)
                .build());
        liquorFiles = liquorFileStore.storeFiles(getMultipartFiles("src/test/resources/static/images/", "vodka", 3));
        liquors.add(Liquor.builder()
                .liquorName("보드카")
                .liquorEname("vodka")
                .liquorAbv(40)
                .liquorOneLine("무색 무취 무향을 특징으로 하는 증류주")
                .liquorContent("치열한 전투에서의 필수품 독하디 독한 술 엔진 연료로 사용 가능")
                .liquorFiles(liquorFiles)
                .build());
        liquorFiles = liquorFileStore.storeFiles(getMultipartFiles("src/test/resources/static/images/", "whisky", 3));
        liquors.add(Liquor.builder()
                .liquorName("위스키")
                .liquorEname("whisky")
                .liquorAbv(40)
                .liquorOneLine("스코틀랜드에서 유래한 증류주")
                .liquorContent("기원전 스코틀랜드 지방에서 전해 내려오는 보리를 발효시켜 알코올을 추출하여 만든 전통 증류주")
                .liquorFiles(liquorFiles)
                .build());
        liquorFiles = liquorFileStore.storeFiles(getMultipartFiles("src/test/resources/static/images/", "vermuth", 3));
        liquors.add(Liquor.builder()
                .liquorName("베르뭇")
                .liquorEname("vermuth")
                .liquorAbv(40)
                .liquorOneLine("주정강화 와인의 한 종류")
                .liquorContent("레드/화이트 와인에 쑥, 용담, 키니네, 창포 등의 향료나 약초를 넣어 향미를 낸 강화 와인")
                .liquorFiles(liquorFiles)
                .build());

        for (Liquor liquor : liquors) {
            liquorRepository.save(liquor);
        }
    }

    private List<MultipartFile> getMultipartFiles(String basicPath, String category, int N) throws IOException {
        List<MultipartFile> multipartFiles = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            String filename = basicPath + category + i + ".png";

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
