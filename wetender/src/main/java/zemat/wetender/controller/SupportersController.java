package zemat.wetender.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zemat.wetender.domain.cocktail.*;
import zemat.wetender.domain.ingredient.Ingredient;
import zemat.wetender.domain.ingredient.IngredientFile;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.liquor.LiquorFile;
import zemat.wetender.dto.AttachFileDto;
import zemat.wetender.dto.cocktailDto.CocktailIngredientDto;
import zemat.wetender.dto.cocktailDto.CocktailLiquorDto;
import zemat.wetender.dto.cocktailDto.CocktailSequenceDto;
import zemat.wetender.dto.ingredientDto.IngredientDto;
import zemat.wetender.dto.liquorDto.LiquorPopDto;
import zemat.wetender.service.*;
import zemat.wetender.dto.supportersDto.*;
import zemat.wetender.service.cocktail.CocktailService;
import zemat.wetender.service.liquor.LiquorService;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Controller
@RequestMapping("/supporters")
@RequiredArgsConstructor
@Slf4j
public class SupportersController {

    private final SupportersService supportersService;
    private final CocktailService cocktailService;
    private final LiquorService liquorService;
    private final IngredientService ingredientService;
    private final MemberService memberService;

    @Value("${cocktailFile.dir}")
    private String cocktailFileDir;

    @Value("${liquorFile.dir}")
    private String liquorFileDir;

    @Value("${ingredientFile.dir}")
    private String ingredientFileDir;

    @ModelAttribute("tastes")
    public Map<String, String> tastes(){
        Map<String, String> tastes = new LinkedHashMap<>();
        tastes.put("단맛","단맛");
        tastes.put("쓴맛","쓴맛");
        tastes.put("신맛","신맛");
        tastes.put("매운맛","매운맛");
        tastes.put("짠맛","짠맛");
        tastes.put("고소한맛","고소한맛");
        tastes.put("떫은맛","떫은맛");
        tastes.put("새콤한맛","새콤한맛");
        tastes.put("상큼한맛","상큼한맛");
        tastes.put("시원한맛","시원한맛");

        return tastes;
    }

    @ModelAttribute("sessionMember")
    public UserDetails getSessionMember() {
        return memberService.getSessionMember();
    }

    @GetMapping("/main")
    public String main(Model model) {
        return "supporters/main";
    }

    //******************************************************************************************************************
    // insert 등록 관련

    @GetMapping("/insert/cocktail")
    public String cocktailInsertForm(Model model){

        int ingredientsCnt = 1;
        int liquorsCnt = 1;
        int sequencesCnt = 1;

        model.addAttribute("ingredientsCnt",ingredientsCnt);
        model.addAttribute("liquorsCnt",liquorsCnt);
        model.addAttribute("sequencesCnt",sequencesCnt);
        model.addAttribute("form", new CocktailInsertForm());

        return  "supporters/insert/cocktail";
    }

    @PostMapping("/insert/cocktail")
    public String testPost(@Validated @ModelAttribute("form") CocktailInsertForm form, BindingResult bindingResult, Model model){

        int ingredientsCnt = -1;
        int liquorsCnt = -1;
        int sequencesCnt = -1;

        List<CocktailSequenceDto> cocktailSequenceDtos = new ArrayList<>();

        if(form.getSequences() != null){
            for (CocktailSequenceDto sequence : form.getSequences()) {
                if(sequence.getContent() != null && !sequence.getContent().equals("")){
                    cocktailSequenceDtos.add(sequence);
                    sequencesCnt++;
                }
            }
        }

        List<CocktailLiquorDto> cocktailLiquorDtos = new ArrayList<>();

        if(form.getLiquors() != null){
            for (CocktailLiquorDto liquor : form.getLiquors()) {
                if(liquor.getId() != null && !liquor.getId().equals("")){
                    cocktailLiquorDtos.add(liquor);
                    liquorsCnt++;
                }
            }
        }

        List<CocktailIngredientDto> cocktailIngredientDtos = new ArrayList<>();

        if(form.getIngredients() != null){
            for (CocktailIngredientDto ingredient : form.getIngredients()) {
                if(ingredient.getId() != null && !ingredient.getId().equals("")){
                    cocktailIngredientDtos.add(ingredient);
                    ingredientsCnt++;
                }
            }
        }

        form.setSequences(cocktailSequenceDtos);
        form.setLiquors(cocktailLiquorDtos);
        form.setIngredients(cocktailIngredientDtos);
        model.addAttribute("ingredientsCnt",ingredientsCnt);
        model.addAttribute("liquorsCnt",liquorsCnt);
        model.addAttribute("sequencesCnt",sequencesCnt);

        // 검증 실패 로직
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "/supporters/insert/cocktail";
        }

        supportersService.cocktailSave(form);

        return "redirect:/supporters/main";
    }

    @GetMapping("/insert/liquor")
    public String liquorInsertForm(Model model){

        model.addAttribute("form", new LiquorInsertForm());

        return "supporters/insert/liquor";
    }

    @PostMapping("/insert/liquor")
    public String liquorInsert(@Validated @ModelAttribute("form") LiquorInsertForm form, BindingResult bindingResult) throws IOException {

        // 검증 실패 로직
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "/supporters/insert/liquor";
        }

        List<AttachFileDto> attachList = form.getAttachList();

        List<LiquorFile> liquorFiles = new ArrayList<>();

        for (AttachFileDto liquorFileDto : attachList) {
            LiquorFile liquorFile = liquorFileDto.toLiquorFileEntity();
            liquorFiles.add(liquorFile);
        }

        Liquor liquor = Liquor.builder()
                .liquorName(form.getName())
                .liquorEname(form.getEName())
                .liquorAbv(form.getAbv())
                .liquorContent(form.getContent())
                .liquorOneLine(form.getOneLine())
                .liquorFiles(liquorFiles)
                .build();

        supportersService.liquorSave(liquor);

        return "redirect:/supporters/main";
    }

    @GetMapping("/insert/ingredient")
    public String ingredientInsertForm(Model model){

        model.addAttribute("form", new IngredientInsertForm());

        return "supporters/insert/ingredient";
    }

    @PostMapping("/insert/ingredient")
    public String ingredientInsert(@Validated @ModelAttribute("form") IngredientInsertForm form, BindingResult bindingResult) throws IOException {

        System.out.println(form.getAttachList());
        // 검증 실패 로직
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "/supporters/insert/ingredient";
        }

        List<AttachFileDto> attachList = form.getAttachList();

        List<IngredientFile> ingredientFiles = new ArrayList<>();

        for (AttachFileDto ingredientFileDto : attachList) {
            ingredientFiles.add(ingredientFileDto.toIntgredientFileEntity());
        }

        Ingredient ingredient =
                Ingredient.builder()
                        .ingredientName(form.getName())
                        .ingredientEname(form.getEName())
                        .ingredientContent(form.getContent())
                        .ingredientFiles(ingredientFiles)
                        .build();

        supportersService.ingredientSave(ingredient);

        return "redirect:/supporters/main";
    }


    //******************************************************************************************************************


    //******************************************************************************************************************
    // 이미지 업로드 관련

    @PostMapping(value = "/upload/image/cocktail", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<AttachFileDto>> uploadCocktailImage(MultipartFile[] uploadFile) throws IOException {

        String uploadFolderPath = getFolder();
        File uploadPath = new File(cocktailFileDir, uploadFolderPath);
        List<AttachFileDto> list = supportersService.uploadImage(uploadFolderPath, uploadPath, uploadFile);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/upload/image/liquor", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<AttachFileDto>> uploadLiquorImage(MultipartFile[] uploadFile) throws IOException {

        String uploadFolderPath = getFolder();
        File uploadPath = new File(liquorFileDir, uploadFolderPath);

        List<AttachFileDto> list = supportersService.uploadImage(uploadFolderPath, uploadPath, uploadFile);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/upload/image/ingredient", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<AttachFileDto>> uploadIngredientImage(MultipartFile[] uploadFile) throws IOException {

        String uploadFolderPath = getFolder();
        File uploadPath = new File(ingredientFileDir, uploadFolderPath);

        List<AttachFileDto> list = supportersService.uploadImage(uploadFolderPath, uploadPath, uploadFile);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    private String getFolder(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sdf.format(date);
        return str.replace("-",File.separator);
    }

    //******************************************************************************************************************


    //******************************************************************************************************************
    // 이미지 파일 삭제

    @PostMapping("/deleteCocktailFile")
    @ResponseBody
    public ResponseEntity<String> deleteCocktailFile(String fileName, String type){
        File file;
        try{
            file = new File(cocktailFileDir + URLDecoder.decode(fileName,"UTF-8"));
            file.delete();
            if(type.equals("image")){
                String largeFileName = file.getAbsolutePath().replace("s_","");
                file = new File(largeFileName);
                boolean exists = file.exists();
                log.info("파일존재여부: " + exists);
                boolean delete = file.delete();
                log.info("삭제여부: "+ delete);
            }
            log.info("원본도 삭제완료");
        } catch(UnsupportedEncodingException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }

    @PostMapping("/deleteLiquorFile")
    @ResponseBody
    public ResponseEntity<String> deleteLiquorFile(String fileName, String type){
        File file;
        try{
            file = new File(liquorFileDir + URLDecoder.decode(fileName,"UTF-8"));
            file.delete();
            if(type.equals("image")){
                String largeFileName = file.getAbsolutePath().replace("s_","");
                file = new File(largeFileName);
                boolean exists = file.exists();
                log.info("파일존재여부: " + exists);
                boolean delete = file.delete();
                log.info("삭제여부: "+ delete);
            }
            log.info("원본도 삭제완료");
        } catch(UnsupportedEncodingException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }

    @PostMapping("/deleteIngredientFile")
    @ResponseBody
    public ResponseEntity<String> deleteIngredientFile(String fileName, String type){
        File file;
        try{
            file = new File(ingredientFileDir + URLDecoder.decode(fileName,"UTF-8"));
            file.delete();
            if(type.equals("image")){
                String largeFileName = file.getAbsolutePath().replace("s_","");
                file = new File(largeFileName);
                boolean exists = file.exists();
                log.info("파일존재여부: " + exists);
                boolean delete = file.delete();
                log.info("삭제여부: "+ delete);
            }
            log.info("원본도 삭제완료");
        } catch(UnsupportedEncodingException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }

    //******************************************************************************************************************


    //******************************************************************************************************************
    // 칵테일 이미지 보이게 설정
    @ResponseBody
    @GetMapping("/display/cocktail/{year}/{month}/{day}/{filename}")
    public Resource cocktailDownloadImage(@PathVariable String year,
                                          @PathVariable String month,
                                          @PathVariable String day,
                                          @PathVariable String filename) throws MalformedURLException {
        log.info("file:" + cocktailFileDir + filename);
        return new UrlResource("file:" + cocktailFileDir + year + "/"+ month + "/" + day + "/" + filename);
    }

    @ResponseBody
    @GetMapping("/display/liquor/{year}/{month}/{day}/{filename}")
    public Resource liquorDownloadImage(@PathVariable String year,
                                          @PathVariable String month,
                                          @PathVariable String day,
                                          @PathVariable String filename) throws MalformedURLException {
        log.info("file:" + liquorFileDir + filename);
        return new UrlResource("file:" + liquorFileDir + year + "/"+ month + "/" + day + "/" + filename);
    }

    @ResponseBody
    @GetMapping("/display/ingredient/{year}/{month}/{day}/{filename}")
    public Resource ingredientDownloadImage(@PathVariable String year,
                                        @PathVariable String month,
                                        @PathVariable String day,
                                        @PathVariable String filename) throws MalformedURLException {
        log.info("file:" + ingredientFileDir + filename);
        return new UrlResource("file:" + ingredientFileDir + year + "/"+ month + "/" + day + "/" + filename);
    }

    //******************************************************************************************************************

    //******************************************************************************************************************
    //주류검색,재료검색 pop 화면
    @GetMapping("/pop/liquorPop")
    public String liquorPopForm(Model model,
                                @RequestParam(value = "id",required = false) String id,
                                @RequestParam(value = "keyword",required = false, defaultValue = "") String keyword,
                                Pageable pageable){

        log.info("pop 들어옴");
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        PageRequest pageRequest = PageRequest.of(page, 5, Sort.by(Sort.Direction.ASC, "liquorName"));
        Page<Liquor> liquors = liquorService.pageFindKeyword(pageRequest,keyword);

        Page<LiquorPopDto> liquorPopDtos = liquors.map(liquor -> new LiquorPopDto(liquor.getId(), liquor.getLiquorName(), liquor.getLiquorEname()));


        int number = liquorPopDtos.getNumber();

        model.addAttribute("id",id);
        model.addAttribute("liquors",liquorPopDtos);

        return "supporters/pop/liquorPop";
    }

    @GetMapping("/pop/ingredientPop")
    public String ingredientPopForm(Model model,
                                @RequestParam(value = "id",required = false) String id,
                                @RequestParam(value = "keyword",required = false, defaultValue = "") String keyword,
                                Pageable pageable){

        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        PageRequest pageRequest = PageRequest.of(page, 5, Sort.by(Sort.Direction.ASC, "ingredientName"));
        Page<Ingredient> ingredients = ingredientService.pageFindKeyword(pageRequest,keyword);

        Page<IngredientDto> ingredientDtos = ingredients.map(ingredient -> new IngredientDto(ingredient));

        model.addAttribute("id",id);
        model.addAttribute("ingredients",ingredientDtos);

        return "supporters/pop/ingredientPop";
    }
    //******************************************************************************************************************

    //******************************************************************************************************************
    //update 관련
    @GetMapping("cocktail/update/{cocktailId}")
    public String cocktailUpdateForm(@PathVariable("cocktailId") Long cocktailId, Model model){
        Cocktail cocktail = cocktailService.findById(cocktailId);
        CocktailUpdateForm cocktailUpdateForm = new CocktailUpdateForm(cocktail);

        int sequencesCnt = cocktail.getCocktailSequences().size();
        int ingredientsCnt = cocktail.getCocktailIngredients().size();
        int liquorsCnt = cocktail.getCocktailLiquors().size();

        model.addAttribute("ingredientsCnt",ingredientsCnt);
        model.addAttribute("liquorsCnt",liquorsCnt);
        model.addAttribute("sequencesCnt",sequencesCnt);
        model.addAttribute( "form",cocktailUpdateForm);
        return "supporters/update/cocktail";
    }

    @GetMapping("liquor/update/{liquorId}")
    public String liquorUpdateForm(@PathVariable("liquorId") Long liquorId, Model model){
        Liquor liquor = liquorService.findById(liquorId);
        LiquorUpdateForm liquorUpdateForm = new LiquorUpdateForm(liquor);

        model.addAttribute("form",liquorUpdateForm);
        return "supporters/update/liquor";
    }

    @GetMapping("ingredient/update/{ingredientId}")
    public String ingredientUpdateForm(@PathVariable("ingredientId") Long ingredientId, Model model){
        Ingredient ingredient = ingredientService.findById(ingredientId);
        IngredientUpdateForm ingredientUpdateForm = new IngredientUpdateForm(ingredient);

        model.addAttribute("form",ingredientUpdateForm);
        return "supporters/update/ingredient";
    }

    @PostMapping("cocktail/update/{cocktailId}")
    public String cocktailUpdate(@PathVariable("cocktailId") Long cocktailId,
                                 @Validated @ModelAttribute("form") CocktailUpdateForm form,
                                 BindingResult bindingResult,
                                 Model model){

        log.info("update하러 들어옴");

        int ingredientsCnt = -1;
        int liquorsCnt = -1;
        int sequencesCnt = -1;

        List<CocktailSequenceDto> cocktailSequenceDtos = new ArrayList<>();

        if(form.getSequences() != null){
            for (CocktailSequenceDto sequence : form.getSequences()) {
                if(sequence.getContent() != null && !sequence.getContent().equals("")){
                    cocktailSequenceDtos.add(sequence);
                    sequencesCnt++;
                }
            }
        }

        List<CocktailLiquorDto> cocktailLiquorDtos = new ArrayList<>();

        if(form.getLiquors() != null){
            for (CocktailLiquorDto liquor : form.getLiquors()) {
                if(liquor.getId() != null && !liquor.getId().equals("")){
                    cocktailLiquorDtos.add(liquor);
                    liquorsCnt++;
                }
            }
        }

        List<CocktailIngredientDto> cocktailIngredientDtos = new ArrayList<>();

        if(form.getIngredients() != null){
            for (CocktailIngredientDto ingredient : form.getIngredients()) {
                if(ingredient.getId() != null && !ingredient.getId().equals("")){
                    cocktailIngredientDtos.add(ingredient);
                    ingredientsCnt++;
                }
            }
        }

        form.setSequences(cocktailSequenceDtos);
        form.setLiquors(cocktailLiquorDtos);
        form.setIngredients(cocktailIngredientDtos);
        model.addAttribute("ingredientsCnt",ingredientsCnt);
        model.addAttribute("liquorsCnt",liquorsCnt);
        model.addAttribute("sequencesCnt",sequencesCnt);

        // 검증 실패 로직
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "/supporters/update/cocktail";
        }

        supportersService.cocktailUpdate(cocktailId,form);

        return "redirect:/cocktail/{cocktailId}";
    }

    @PostMapping("liquor/update/{liquorId}")
    public String liquorUpdate(@PathVariable("liquorId") Long liquorId,
                                 @Validated @ModelAttribute("form") LiquorUpdateForm form,
                                 BindingResult bindingResult){

        // 검증 실패 로직
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "/supporters/update/liquor";
        }

        supportersService.liquorUpdate(liquorId, form);

        return "redirect:/liquor/{liquorId}";
    }

    @PostMapping("ingredient/update/{ingredientId}")
    public String ingredientUpdate(@PathVariable("ingredientId") Long ingredientId,
                                 @Validated @ModelAttribute("form") IngredientUpdateForm form,
                                 BindingResult bindingResult){

        // 검증 실패 로직
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "/supporters/update/ingredient";
        }

        supportersService.ingredientUpdate(ingredientId, form);

        return "redirect:/ingredient/{ingredientId}";
    }

    //******************************************************************************************************************

    //******************************************************************************************************************
    //delete 관련
    @GetMapping("cocktail/delete/{cocktailId}")
    public String cocktailDelete(@PathVariable("cocktailId") Long cocktailId){
        Cocktail cocktail = cocktailService.findById(cocktailId);
        deleteCocktailFiles(cocktail.getCocktailFiles());
        cocktailService.deleteById(cocktailId);
        return "redirect:/cocktail/main";
    }

    @GetMapping("liquor/delete/{liquorId}")
    public String liquorDelete(@PathVariable("liquorId") Long liquorId){
        Liquor liquor = liquorService.findById(liquorId);
        deleteLiquorFiles(liquor.getLiquorFiles());
        liquorService.deleteById(liquorId);
        return "redirect:/liquor/main";
    }

    @GetMapping("ingredient/delete/{ingredientId}")
    public String ingredientDelete(@PathVariable("ingredientId") Long ingredientId){
        Ingredient ingredient = ingredientService.findById(ingredientId);
        deleteIngredientFiles(ingredient.getIngredientFiles());
        ingredientService.deleteById(ingredientId);
        return "redirect:/cocktail/main";
    }

    private void deleteCocktailFiles(List<CocktailFile> cocktailFiles){
        if(cocktailFiles == null || cocktailFiles.size() == 0) return;

        cocktailFiles.forEach(cocktailFile -> {
            try{
                Path file = Paths.get(cocktailFileDir + cocktailFile.getUploadPath() +
                        "\\" + cocktailFile.getUuid() + "_" + cocktailFile.getFileName());
                Files.deleteIfExists(file);
                //썸네일 관련 삭제
//                if(Files.probeContentType(file).startsWith("image")){
//                    Path thumbNail = Paths.get(cocktailFileDir + cocktailFile.getUploadPath() +
//                            "\\s_" + cocktailFile.getUuid() + "_" + cocktailFile.getFileName());
//                    Files.delete(thumbNail);
//                }
            } catch (Exception e){
                log.error("delete file error : " + e.getMessage());
            }
        });
    }

    private void deleteLiquorFiles(List<LiquorFile> liquorFiles){
        if(liquorFiles == null || liquorFiles.size() == 0) return;

        liquorFiles.forEach(liquorFile -> {
            try{
                Path file = Paths.get(liquorFileDir + liquorFile.getUploadPath() +
                        "\\" + liquorFile.getUuid() + "_" + liquorFile.getFileName());
                Files.deleteIfExists(file);
                //썸네일 관련 삭제
//                if(Files.probeContentType(file).startsWith("image")){
//                    Path thumbNail = Paths.get(liquorFileDir + liquorFile.getUploadPath() +
//                            "\\s_" + liquorFile.getUuid() + "_" + liquorFile.getFileName());
//                    Files.delete(thumbNail);
//                }
            } catch (Exception e){
                log.error("delete file error : " + e.getMessage());
            }
        });
    }

    private void deleteIngredientFiles(List<IngredientFile> ingredientFiles){
        if(ingredientFiles == null || ingredientFiles.size() == 0) return;

        ingredientFiles.forEach(ingredientFile -> {
            try{
                Path file = Paths.get(ingredientFileDir + ingredientFile.getUploadPath() +
                        "\\" + ingredientFile.getUuid() + "_" + ingredientFile.getFileName());
                Files.deleteIfExists(file);
                //썸네일 관련 삭제
//                if(Files.probeContentType(file).startsWith("image")){
//                    Path thumbNail = Paths.get(ingredientFileDir + ingredientFile.getUploadPath() +
//                            "\\s_" + ingredientFile.getUuid() + "_" + ingredientFile.getFileName());
//                    Files.delete(thumbNail);
//                }
            } catch (Exception e){
                log.error("delete file error : " + e.getMessage());
            }
        });
    }

    //******************************************************************************************************************
}