//package zemat.wetender.service;
//
//
//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.disk.DiskFileItem;
//import org.apache.commons.io.IOUtils;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;
//import zemat.wetender.domain.ingredient.Ingredient;
//import zemat.wetender.domain.ingredient.IngredientFile;
//import zemat.wetender.domain.ingredient.IngredientFileStore;
//import zemat.wetender.domain.liquor.Liquor;
//import zemat.wetender.domain.liquor.LiquorFile;
//import zemat.wetender.domain.liquor.LiquorFileStore;
//import zemat.wetender.dto.cocktailDto.CocktailHomeDto;
//import zemat.wetender.repository.IngredientRepository;
//import zemat.wetender.repository.liquor.LiquorRepository;
//import zemat.wetender.service.cocktail.CocktailService;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import java.io.*;
//import java.nio.file.Files;
//import java.util.ArrayList;
//import java.util.List;
//
//@SpringBootTest
//@Transactional
//class CocktailServiceTest {
//
//    @PersistenceContext EntityManager em;
//    @Autowired
//    CocktailService cocktailService;
//    @Autowired CocktailFileStore cocktailFileStore;
//    @Autowired IngredientFileStore ingredientFileStore;
//    @Autowired IngredientRepository ingredientRepository;
//    @Autowired LiquorFileStore liquorFileStore;
//    @Autowired LiquorRepository liquorRepository;
//
//    @Test
//    public void 리소스이미지사용() throws IOException { // 리소스/스태틱/이미지 파일 사용하여 cocktailFile.dir에 이미지 저장 확인
//        for (int i = 0; i < 5; i++) {
//            String filename = "src/test/resources/static/images/cocktail" + i + ".png";
//
//            File file = new File(filename);
//            FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
//
//            try {
//                InputStream input = new FileInputStream(file);
//                OutputStream os = fileItem.getOutputStream();
//                IOUtils.copy(input, os);
//                // Or faster..
//                // IOUtils.copy(new FileInputStream(file), fileItem.getOutputStream());
//            } catch (IOException ex) {
//                // do something.
//            }
//
//            System.out.println("filename = " + filename);
//            MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
////            cocktailFileStore.storeFile(multipartFile);
//        }
//    }
//
//    @Test
//    public void 칵테일재료이니셜데이터확인() throws IOException {
//        initIngredientData();
//        initLiquorData();
//        em.flush();
//        em.clear();
//
//        List<Ingredient> ingredients = ingredientRepository.findAll();
//        for (Ingredient ingredient : ingredients) {
//            System.out.println("재료이름: " + ingredient.getIngredientName());
//            for (IngredientFile ingredientFile : ingredient.getIngredientFiles()) {
////                System.out.print("    [" + ingredientFile.getUploadIngredientFileName() + "]");
////                System.out.println("[" + ingredientFile.getStoreIngredientFileName() + "]");
//            }
//        }
//
//        List<Liquor> liquors = liquorRepository.findAll();
//        for (Liquor liquor : liquors) {
//            System.out.println("재료이름: " + liquor.getLiquorName() + " 도수: " + liquor.getLiquorAbv());
//            System.out.println("내용: " + liquor.getLiquorContent());
//            System.out.println("한줄평: " + liquor.getLiquorOneLine());
//            for (LiquorFile liquorFile : liquor.getLiquorFiles()) {
////                System.out.print("    [" + liquorFile.getUploadLiquorFileName() + "]");
////                System.out.println("[" + liquorFile.getStoreLiquorFileName() + "]");
//            }
//        }
//    }
//
//    public void initIngredientData() throws IOException {
//        List<Ingredient> ingredients = new ArrayList<>();
//        List<IngredientFile> ingredientFiles;
//
//        ingredientFiles = ingredientFileStore.storeFiles(getMultipartFiles("src/test/resources/static/images/", "lemon", 3));
//        ingredients.add(Ingredient.builder()
//                        .ingredientName("레몬")
//                        .ingredientEname("lemon")
//                        .ingredientFiles(ingredientFiles)
//                        .build());
//        ingredientFiles = ingredientFileStore.storeFiles(getMultipartFiles("src/test/resources/static/images/", "lime", 3));
//        ingredients.add(Ingredient.builder()
//                        .ingredientName("라임")
//                        .ingredientEname("lime")
//                        .ingredientFiles(ingredientFiles)
//                        .build());
//        ingredientFiles = ingredientFileStore.storeFiles(getMultipartFiles("src/test/resources/static/images/", "sugar", 3));
//        ingredients.add(Ingredient.builder()
//                        .ingredientName("설탕")
//                        .ingredientEname("sugar")
//                        .ingredientFiles(ingredientFiles)
//                        .build());
//
//        for (Ingredient ingredient : ingredients) {
//            ingredientRepository.save(ingredient);
//        }
//    }
//
//    public void initLiquorData() throws IOException {
//        List<Liquor> liquors = new ArrayList<>();
//        List<LiquorFile> liquorFiles;
//
//        liquorFiles = liquorFileStore.storeFiles(getMultipartFiles("src/test/resources/static/images/", "jackdaniel", 3));
//        liquors.add(Liquor.builder()
//                .liquorName("잭다니엘")
//                .liquorEname("jackdaniel")
//                .liquorAbv(40)
//                .liquorOneLine("아메리칸 위스키")
//                .liquorContent("잭 다니엘스 테네시 위스키는 세계에서 가장 많이 팔리는 위스키입니다. ")
//                .liquorFiles(liquorFiles)
//                .build());
//        liquorFiles = liquorFileStore.storeFiles(getMultipartFiles("src/test/resources/static/images/", "gin", 3));
//        liquors.add(Liquor.builder()
//                .liquorName("진")
//                .liquorEname("gin")
//                .liquorAbv(60)
//                .liquorOneLine("몰트향이 강하며 단맛이 나는 술")
//                .liquorContent("노간주나무 열매를 알코올에 침전시켜 증류하는 과정을 통해 만든 약용주. 네덜란드에서 영국으로 넘어오면서 향이 약해지고 단맛이 없어지는 등 드라이하게 변함")
//                .liquorFiles(liquorFiles)
//                .build());
//        liquorFiles = liquorFileStore.storeFiles(getMultipartFiles("src/test/resources/static/images/", "rum", 3));
//        liquors.add(Liquor.builder()
//                .liquorName("럼")
//                .liquorEname("rum")
//                .liquorAbv(45)
//                .liquorOneLine("사탕수수술")
//                .liquorContent("사탕수수를 발효, 증류하여 만든 증류주")
//                .liquorFiles(liquorFiles)
//                .build());
//        liquorFiles = liquorFileStore.storeFiles(getMultipartFiles("src/test/resources/static/images/", "vodka", 3));
//        liquors.add(Liquor.builder()
//                .liquorName("보드카")
//                .liquorEname("vodka")
//                .liquorAbv(40)
//                .liquorOneLine("무색 무취 무향을 특징으로 하는 증류주")
//                .liquorContent("치열한 전투에서의 필수품 독하디 독한 술 엔진 연료로 사용 가능")
//                .liquorFiles(liquorFiles)
//                .build());
//        liquorFiles = liquorFileStore.storeFiles(getMultipartFiles("src/test/resources/static/images/", "whisky", 3));
//        liquors.add(Liquor.builder()
//                .liquorName("위스키")
//                .liquorEname("whisky")
//                .liquorAbv(40)
//                .liquorOneLine("스코틀랜드에서 유래한 증류주")
//                .liquorContent("기원전 스코틀랜드 지방에서 전해 내려오는 보리를 발효시켜 알코올을 추출하여 만든 전통 증류주")
//                .liquorFiles(liquorFiles)
//                .build());
//        liquorFiles = liquorFileStore.storeFiles(getMultipartFiles("src/test/resources/static/images/", "vermuth", 3));
//        liquors.add(Liquor.builder()
//                .liquorName("베르뭇")
//                .liquorEname("vermuth")
//                .liquorAbv(40)
//                .liquorOneLine("주정강화 와인의 한 종류")
//                .liquorContent("레드/화이트 와인에 쑥, 용담, 키니네, 창포 등의 향료나 약초를 넣어 향미를 낸 강화 와인")
//                .liquorFiles(liquorFiles)
//                .build());
//
//        for (Liquor liquor : liquors) {
//            liquorRepository.save(liquor);
//        }
//    }
//
//    private List<MultipartFile> getMultipartFiles(String basicPath, String category, int N) throws IOException {
//        List<MultipartFile> multipartFiles = new ArrayList<>();
//        for (int i = 0; i < N; i++) {
//            String filename = basicPath + category + i + ".png";
//
//            File file = new File(filename);
//            FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
//
//            try {
//                InputStream input = new FileInputStream(file);
//                OutputStream os = fileItem.getOutputStream();
//                IOUtils.copy(input, os);
//                // Or faster..
//                // IOUtils.copy(new FileInputStream(file), fileItem.getOutputStream());
//            } catch (IOException ex) {
//                // do something.
//            }
//
//            MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
//            multipartFiles.add(multipartFile);
//        }
//        return multipartFiles;
//    }
//
//    @Test
//    public void 칵테일_상위20추천수_조회_with_Dto() throws IOException {
//        List<CocktailHomeDto> cocktailHomeDtos = cocktailService.findTop20ByRecommendation();
//        for (CocktailHomeDto cocktailHomeDto : cocktailHomeDtos) {
//            System.out.println("===========================================================");
//            System.out.println("칵테일이름 = " + cocktailHomeDto.getName());
//            System.out.println("추천수 = " + cocktailHomeDto.getRecommendation());
//            System.out.println("이미지파일 = " + cocktailHomeDto.getMainImage());
//            System.out.println("===========================================================");
//        }
//    }
//}