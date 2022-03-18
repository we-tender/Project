package zemat.wetender.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import zemat.wetender.domain.cocktail.*;
import zemat.wetender.domain.ingredient.Ingredient;
import zemat.wetender.domain.ingredient.IngredientFile;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.liquor.LiquorFile;
import zemat.wetender.dto.AttachFileDto;
import zemat.wetender.dto.ingredientDto.IngredientDto;
import zemat.wetender.dto.liquorDto.LiquorPopDto;
import zemat.wetender.service.*;
import zemat.wetender.dto.supportersDto.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
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

    @GetMapping("/main")
    public String main(Model model) {
        model.addAttribute("sessionMember", memberService.getSessionMember());
        return "supporters/main";
    }

    // insert 관련 get,post
    @GetMapping("/insert/cocktail")
    public String cocktailInsertForm(Model model){

        model.addAttribute("form", new CocktailInsertForm());
        model.addAttribute("sessionMember", memberService.getSessionMember());

        return  "supporters/insert/cocktail";
    }

    @PostMapping("/insert/cocktail")
    public String testPost(@Validated @ModelAttribute("form") CocktailInsertForm form, BindingResult bindingResult,
                                 HttpServletRequest request){
        log.info("post 들어옴");
        log.info("register : " + form.toString());
        if(form.getAttachList() != null){
            form.getAttachList().forEach(attach -> log.info("attach: " + String.valueOf(attach)));
        }

//         검증 실패 로직
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "/supporters/insert/cocktail";
        }

        // 칵테일 맛
        List<String> tastes = form.getTastes();
        List<CocktailTaste> cocktailTastes = new ArrayList<>();

        for (String taste : tastes) {
            CocktailTaste cocktailTaste = new CocktailTaste(taste);
            cocktailTastes.add(cocktailTaste);
        }

        // 칵테일 순서
        List<CocktailSequence> cocktailSequences = new ArrayList<>();

        String[] sequenceContents = request.getParameterValues("sequenceContent");
        for (String sequence : sequenceContents) {
            CocktailSequence cocktailSequence = new CocktailSequence(sequence);
            cocktailSequences.add(cocktailSequence);
        }

        // 칵테일 주류 재료
        List<CocktailLiquor> cocktailLiquors = new ArrayList<>();


        String[] liquorIngredientIds = request.getParameterValues("liquorIngredientId");
        String[] liquorIngredientQties = request.getParameterValues("liquorIngredientQty");

        if(liquorIngredientIds != null) {
            for (int i = 0; i < liquorIngredientIds.length; i++) {
                if (liquorIngredientIds[0].equals("")) continue;
                long liquorId = Long.parseLong(liquorIngredientIds[i]);
                String qty = liquorIngredientQties[i];
                Liquor liquor = liquorService.findById(liquorId);

                CocktailLiquor cocktailLiquor = CocktailLiquor.builder()
                        .cocktailIngredientQty(qty)
                        .liquor(liquor)
                        .build();

                cocktailLiquors.add(cocktailLiquor);
            }
        }


        // 칵테일 재료
        List<CocktailIngredient> cocktailIngredients = new ArrayList<>();

        String[] cocktailIngredientIds = request.getParameterValues("cocktailIngredientId");
        String[] cocktailIngredientQties = request.getParameterValues("cocktailIngredientQty");

        if(cocktailIngredientIds != null){
            for(int i = 0; i < cocktailIngredientIds.length; i++){
                if(cocktailIngredientIds[0].equals("")) continue;
                long ingredientId = Long.parseLong(cocktailIngredientIds[i]);
                String qty = cocktailIngredientQties[i];
                System.out.println("id값: " + ingredientId);
                Ingredient ingredient = ingredientService.findById(ingredientId);

                CocktailIngredient cocktailIngredient = CocktailIngredient.builder()
                        .ingredient(ingredient)
                        .cocktailIngredientQty(qty)
                        .build();

                cocktailIngredients.add(cocktailIngredient);
            }
        }

        //칵테일 파일
        List<CocktailFile> cocktailFiles = new ArrayList<>();
        form.getAttachList().forEach(cocktailFileDto -> cocktailFiles.add(cocktailFileDto.toCocktailFileEntity()));

        // 칵테일 엔티티 생성
        Cocktail cocktail = Cocktail.builder()
                .cocktailName(form.getName())
                .cocktailEName(form.getEName())
                .cocktailAbv(form.getAbv())
                .cocktailBase(form.getBase())
                .cocktailContent(form.getContent())
                .cocktailOneLine(form.getOneLine())
                .cocktailTastes(cocktailTastes)
                .cocktailFiles(cocktailFiles)
                .cocktailSequences(cocktailSequences)
                .cocktailLiquors(cocktailLiquors)
                .cocktailIngredients(cocktailIngredients)
                .cocktailRecommendation(0)
                .build();

        supportersService.cocktailSave(cocktail);

        return "redirect:/supporters/main";
    }

    @PostMapping(value = "/upload/image/cocktail", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<AttachFileDto>> uploadCocktailImage(MultipartFile[] uploadFile) throws IOException {
        List<AttachFileDto> list = new ArrayList<>();

        String uploadFolderPath = getFolder();
        File uploadPath = new File(cocktailFileDir, uploadFolderPath);
        log.info("upload path : " +uploadPath);

        if(uploadPath.exists() == false){
            uploadPath.mkdirs();
        }

        for (MultipartFile multipartFile : uploadFile) {
            log.info("----------------------");
            log.info("upload file name : " + multipartFile.getOriginalFilename());
            log.info("upload file size : " +     multipartFile.getSize());

            String uploadFileName = multipartFile.getOriginalFilename();
            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") +1);

            log.info("only file name : " + uploadFileName);

            UUID uuid = UUID.randomUUID();
            String storeFileName = uuid.toString() + "_" + uploadFileName;

            try {
                File savefile = new File(uploadPath,storeFileName);
                multipartFile.transferTo(savefile);

                if(checkImageType(savefile)){
                    AttachFileDto attachFileDto = new AttachFileDto(uploadFileName, uploadFolderPath, uuid.toString(), true);
                    FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + storeFileName));

                    FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(savefile.toPath()), false, savefile.getName(), (int) savefile.length(), savefile.getParentFile());
                    try {
                        InputStream input = new FileInputStream(savefile);
                        OutputStream os = fileItem.getOutputStream();
                        IOUtils.copy(input, os);
                        input.close();
                        os.close();
                    } catch (IOException ex) {
                        // do something.
                    }

                    MultipartFile saveMultipartFile = new CommonsMultipartFile(fileItem);
                    InputStream inputStream = saveMultipartFile.getInputStream();
                    Thumbnailator.createThumbnail(inputStream,thumbnail,100,100);

                    thumbnail.close();
                    inputStream.close();

                    list.add(attachFileDto);
                }
            } catch (Exception e){
                log.error(e.getMessage());
            }
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/upload/image/liquor", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<AttachFileDto>> uploadLiquorImage(MultipartFile[] uploadFile) throws IOException {
        List<AttachFileDto> list = new ArrayList<>();

        String uploadFolderPath = getFolder();
        File uploadPath = new File(liquorFileDir, uploadFolderPath);
        log.info("upload path : " +uploadPath);

        if(uploadPath.exists() == false){
            uploadPath.mkdirs();
        }

        for (MultipartFile multipartFile : uploadFile) {
            log.info("----------------------");
            log.info("upload file name : " + multipartFile.getOriginalFilename());
            log.info("upload file size : " +     multipartFile.getSize());

            String uploadFileName = multipartFile.getOriginalFilename();
            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") +1);

            log.info("only file name : " + uploadFileName);

            UUID uuid = UUID.randomUUID();
            String storeFileName = uuid.toString() + "_" + uploadFileName;

            try {
                File savefile = new File(uploadPath,storeFileName);
                multipartFile.transferTo(savefile);

                if(checkImageType(savefile)){
                    AttachFileDto attachFileDto = new AttachFileDto(uploadFileName, uploadFolderPath, uuid.toString(), true);
                    FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + storeFileName));

                    FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(savefile.toPath()), false, savefile.getName(), (int) savefile.length(), savefile.getParentFile());
                    try {
                        InputStream input = new FileInputStream(savefile);
                        OutputStream os = fileItem.getOutputStream();
                        IOUtils.copy(input, os);
                        input.close();
                        os.close();
                    } catch (IOException ex) {
                        // do something.
                    }

                    MultipartFile saveMultipartFile = new CommonsMultipartFile(fileItem);
                    InputStream inputStream = saveMultipartFile.getInputStream();
                    Thumbnailator.createThumbnail(inputStream,thumbnail,100,100);

                    thumbnail.close();
                    inputStream.close();

                    list.add(attachFileDto);
                }
            } catch (Exception e){
                log.error(e.getMessage());
            }
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/upload/image/ingredient", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<AttachFileDto>> uploadIngredientImage(MultipartFile[] uploadFile) throws IOException {
        List<AttachFileDto> list = new ArrayList<>();

        String uploadFolderPath = getFolder();
        File uploadPath = new File(ingredientFileDir, uploadFolderPath);
        log.info("upload path : " +uploadPath);

        if(uploadPath.exists() == false){
            uploadPath.mkdirs();
        }

        for (MultipartFile multipartFile : uploadFile) {
            log.info("----------------------");
            log.info("upload file name : " + multipartFile.getOriginalFilename());
            log.info("upload file size : " +     multipartFile.getSize());

            String uploadFileName = multipartFile.getOriginalFilename();
            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") +1);

            log.info("only file name : " + uploadFileName);

            UUID uuid = UUID.randomUUID();
            String storeFileName = uuid.toString() + "_" + uploadFileName;

            try {
                File savefile = new File(uploadPath,storeFileName);
                multipartFile.transferTo(savefile);

                if(checkImageType(savefile)){
                    AttachFileDto attachFileDto = new AttachFileDto(uploadFileName, uploadFolderPath, uuid.toString(), true);
                    FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + storeFileName));

                    FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(savefile.toPath()), false, savefile.getName(), (int) savefile.length(), savefile.getParentFile());
                    try {
                        InputStream input = new FileInputStream(savefile);
                        OutputStream os = fileItem.getOutputStream();
                        IOUtils.copy(input, os);
                        input.close();
                        os.close();
                    } catch (IOException ex) {
                        // do something.
                    }

                    MultipartFile saveMultipartFile = new CommonsMultipartFile(fileItem);
                    InputStream inputStream = saveMultipartFile.getInputStream();
                    Thumbnailator.createThumbnail(inputStream,thumbnail,100,100);

                    thumbnail.close();
                    inputStream.close();

                    list.add(attachFileDto);
                }
            } catch (Exception e){
                log.error(e.getMessage());
            }
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/deleteFile")
    @ResponseBody
    public ResponseEntity<String> deleteFile(String fileName, String type){
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

    private boolean checkImageType(File file){
        try{
            String contentType = Files.probeContentType(file.toPath());
            return contentType.startsWith("image");
        } catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    private String getFolder(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sdf.format(date);
        return str.replace("-",File.separator);
    }

    @GetMapping("/display/cocktail")
    @ResponseBody
    public ResponseEntity<byte[]> cocktailGetFile(String fileName){
        log.info("fileName : " + fileName);
        File file = new File(cocktailFileDir + fileName);
        log.info("file : " + file);
        ResponseEntity<byte[]> result = null;

        try{
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", Files.probeContentType(file.toPath()));
            FileCopyUtils.copyToByteArray(file);
            result = new ResponseEntity<byte[]>(
                    FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @GetMapping("/display/liquor")
    @ResponseBody
    public ResponseEntity<byte[]> liquorGetFile(String fileName){
        log.info("fileName : " + fileName);
        File file = new File(liquorFileDir + fileName);
        log.info("file : " + file);
        ResponseEntity<byte[]> result = null;

        try{
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", Files.probeContentType(file.toPath()));
            FileCopyUtils.copyToByteArray(file);
            result = new ResponseEntity<byte[]>(
                    FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @GetMapping("/display/ingredient")
    @ResponseBody
    public ResponseEntity<byte[]> ingredientGetFile(String fileName){
        log.info("fileName : " + fileName);
        File file = new File(ingredientFileDir + fileName);
        log.info("file : " + file);
        ResponseEntity<byte[]> result = null;

        try{
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", Files.probeContentType(file.toPath()));
            FileCopyUtils.copyToByteArray(file);
            result = new ResponseEntity<byte[]>(
                    FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @GetMapping("/insert/liquor")
    public String liquorInsertForm(Model model){

        model.addAttribute("form", new LiquorInsertForm());
        model.addAttribute("sessionMember", memberService.getSessionMember());

        return "supporters/insert/liquor";
    }

    @PostMapping("/insert/liquor")
    public String liquorInsert(LiquorInsertForm form) throws IOException {

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
        model.addAttribute("sessionMember", memberService.getSessionMember());

        return "supporters/insert/ingredient";
    }

    @PostMapping("/insert/ingredient")
    public String ingredientInsert(IngredientInsertForm form) throws IOException {

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

    //insert 과정에서의 pop 화면
    @GetMapping("/pop/liquorPop")
    public String liquorPopForm(Model model,
                                @RequestParam(value = "id",required = false) String id,
                                @RequestParam(value = "name",required = false) String name,
                                @RequestParam(value = "keyword",required = false, defaultValue = "") String keyword,
                                Pageable pageable){

        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        PageRequest pageRequest = PageRequest.of(page, 5, Sort.by(Sort.Direction.ASC, "liquorName"));
        Page<Liquor> liquors = liquorService.pageFindKeyword(pageRequest,keyword);

        Page<LiquorPopDto> liquorPopDtos = liquors.map(liquor -> new LiquorPopDto(liquor.getId(), liquor.getLiquorName(), liquor.getLiquorEname()));


        int number = liquorPopDtos.getNumber();

        model.addAttribute("id",id);
        model.addAttribute("name",name);
        model.addAttribute("liquors",liquorPopDtos);

        return "supporters/pop/liquorPop";
    }

    @GetMapping("/pop/ingredientPop")
    public String ingredientPopForm(Model model,
                                @RequestParam(value = "id",required = false) String id,
                                @RequestParam(value = "name",required = false) String name,
                                @RequestParam(value = "keyword",required = false, defaultValue = "") String keyword,
                                Pageable pageable){

        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        PageRequest pageRequest = PageRequest.of(page, 5, Sort.by(Sort.Direction.ASC, "ingredientName"));
        Page<Ingredient> ingredients = ingredientService.pageFindKeyword(pageRequest,keyword);

        Page<IngredientDto> ingredientDtos = ingredients.map(ingredient -> new IngredientDto(ingredient));

        model.addAttribute("id",id);
        model.addAttribute("name",name);
        model.addAttribute("ingredients",ingredientDtos);

        return "supporters/pop/ingredientPop";
    }

    //update 관련 get, post
    @GetMapping("cocktail/update/{cocktailId}")
    public String cocktailUpdateForm(@PathVariable("cocktailId") Long cocktailId, Model model){
        Cocktail cocktail = cocktailService.findById(cocktailId);
        CocktailUpdateForm cocktailUpdateForm = new CocktailUpdateForm(cocktail);

        int cocktailIngredientCnt = cocktail.getCocktailIngredients().size();
        int liquorIngredientCnt = cocktail.getCocktailLiquors().size();

        model.addAttribute("cocktailIngredientCnt",cocktailIngredientCnt);
        model.addAttribute("liquorIngredientCnt",liquorIngredientCnt);
        model.addAttribute("form",cocktailUpdateForm);
        return "supporters/update/cocktail";
    }

    //update 관련 get, post
    @GetMapping("liquor/update/{liquorId}")
    public String liquorUpdateForm(@PathVariable("liquorId") Long liquorId, Model model){
        Liquor liquor = liquorService.findById(liquorId);
        LiquorUpdateForm liquorUpdateForm = new LiquorUpdateForm(liquor);

        model.addAttribute("form",liquorUpdateForm);
        return "supporters/update/liquor";
    }

    //update 관련 get, post
    @GetMapping("ingredient/update/{ingredientId}")
    public String ingredientUpdateForm(@PathVariable("ingredientId") Long ingredientId, Model model){
        Ingredient ingredient = ingredientService.findById(ingredientId);
        IngredientUpdateForm ingredientUpdateForm = new IngredientUpdateForm(ingredient);

        model.addAttribute("form",ingredientUpdateForm);
        return "supporters/update/ingredient";
    }

    @GetMapping(value = "update/cocktailFileList", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<AttachFileDto>> getCocktailAttachList(Long cocktailId){
        List<CocktailFile> cocktailFiles = cocktailService.findById(cocktailId).getCocktailFiles();
        List<AttachFileDto> cocktailFileDtos = new ArrayList<>();
        for (CocktailFile cocktailFile : cocktailFiles) {
            AttachFileDto cocktailFileDto = new AttachFileDto(cocktailFile);
            cocktailFileDtos.add(cocktailFileDto);
        }
        return new ResponseEntity<>(cocktailFileDtos,HttpStatus.OK);
    }

    @GetMapping(value = "update/liquorFileList", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<AttachFileDto>> getLiquorAttachList(Long liquorId){
        List<LiquorFile> liquorFiles = liquorService.findById(liquorId).getLiquorFiles();
        List<AttachFileDto> liquorFileDtos = new ArrayList<>();
        for (LiquorFile liquorFile : liquorFiles) {
            AttachFileDto liquorFileDto = new AttachFileDto(liquorFile);
            liquorFileDtos.add(liquorFileDto);
        }
        return new ResponseEntity<>(liquorFileDtos,HttpStatus.OK);
    }

    @GetMapping(value = "update/ingredientFileList", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<AttachFileDto>> getIngredientAttachList(Long ingredientId){
        List<IngredientFile> ingredientFiles = ingredientService.findById(ingredientId).getIngredientFiles();
        List<AttachFileDto> ingredientFileDtos = new ArrayList<>();
        for (IngredientFile ingredientFile : ingredientFiles) {
            AttachFileDto ingredientFileDto = new AttachFileDto(ingredientFile);
            ingredientFileDtos.add(ingredientFileDto);
        }
        return new ResponseEntity<>(ingredientFileDtos,HttpStatus.OK);
    }

    @PostMapping("cocktail/update/{cocktailId}")
    public String cocktailUpdate(@PathVariable("cocktailId") Long cocktailId,
                                 @Validated @ModelAttribute("form") CocktailUpdateForm form,
                                 BindingResult bindingResult,
                                 HttpServletRequest request) throws IOException {

        log.info("update하러 들어옴");

        // 검증 실패 로직
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "/supporters/update/cocktail";
        }

        // 칵테일 순서
        List<CocktailSequence> cocktailSequences = new ArrayList<>();

        String[] sequenceContents = request.getParameterValues("sequenceContent");
        for (String sequence : sequenceContents) {
            CocktailSequence cocktailSequence = new CocktailSequence(sequence);
            cocktailSequences.add(cocktailSequence);
        }

        // 칵테일 주류 재료
        List<CocktailLiquor> cocktailLiquors = new ArrayList<>();


        String[] liquorIngredientIds = request.getParameterValues("liquorIngredientId");
        String[] liquorIngredientQties = request.getParameterValues("liquorIngredientQty");

        if(liquorIngredientIds != null) {
            for (int i = 0; i < liquorIngredientIds.length; i++) {
                if (liquorIngredientIds[0].equals("")) continue;
                long liquorId = Long.parseLong(liquorIngredientIds[i]);
                String qty = liquorIngredientQties[i];
                Liquor liquor = liquorService.findById(liquorId);

                CocktailLiquor cocktailLiquor = CocktailLiquor.builder()
                        .cocktailIngredientQty(qty)
                        .liquor(liquor)
                        .build();

                cocktailLiquors.add(cocktailLiquor);
            }
        }

        // 칵테일 재료
        List<CocktailIngredient> cocktailIngredients = new ArrayList<>();

        String[] cocktailIngredientIds = request.getParameterValues("cocktailIngredientId");
        String[] cocktailIngredientQties = request.getParameterValues("cocktailIngredientQty");

        if(cocktailIngredientIds != null){
            for(int i = 0; i < cocktailIngredientIds.length; i++){
                if(cocktailIngredientIds[0].equals("")) continue;
                long ingredientId = Long.parseLong(cocktailIngredientIds[i]);
                String qty = cocktailIngredientQties[i];
                System.out.println("id값: " + ingredientId);
                Ingredient ingredient = ingredientService.findById(ingredientId);

                CocktailIngredient cocktailIngredient = CocktailIngredient.builder()
                        .ingredient(ingredient)
                        .cocktailIngredientQty(qty)
                        .build();

                cocktailIngredients.add(cocktailIngredient);
            }
        }

        Cocktail cocktail = form.toEntity();

        cocktailService.update(cocktailId, cocktail, cocktailSequences, cocktailLiquors, cocktailIngredients);

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

        Liquor liquor = form.toEntity();

        liquorService.update(liquorId, liquor);

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

        Ingredient ingredient = form.toEntity();

        ingredientService.update(ingredientId, ingredient);

        return "redirect:/ingredient/{ingredientId}";
    }

    //delete 관련 get, post
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
                if(Files.probeContentType(file).startsWith("image")){
                    Path thumbNail = Paths.get(cocktailFileDir + cocktailFile.getUploadPath() +
                            "\\s_" + cocktailFile.getUuid() + "_" + cocktailFile.getFileName());
                    Files.delete(thumbNail);
                }
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
                if(Files.probeContentType(file).startsWith("image")){
                    Path thumbNail = Paths.get(liquorFileDir + liquorFile.getUploadPath() +
                            "\\s_" + liquorFile.getUuid() + "_" + liquorFile.getFileName());
                    Files.delete(thumbNail);
                }
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
                if(Files.probeContentType(file).startsWith("image")){
                    Path thumbNail = Paths.get(ingredientFileDir + ingredientFile.getUploadPath() +
                            "\\s_" + ingredientFile.getUuid() + "_" + ingredientFile.getFileName());
                    Files.delete(thumbNail);
                }
            } catch (Exception e){
                log.error("delete file error : " + e.getMessage());
            }
        });
    }
}