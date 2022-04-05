package zemat.wetender.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import zemat.wetender.domain.cocktail.*;
import zemat.wetender.domain.ingredient.Ingredient;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.repository.cocktail.CocktailRepository;
import zemat.wetender.dto.AttachFileDto;
import zemat.wetender.dto.supportersDto.CocktailInsertForm;
import zemat.wetender.repository.IngredientRepository;
import zemat.wetender.repository.liquor.LiquorRepository;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class SupportersService {

    private final CocktailRepository cocktailRepository;
    private final LiquorRepository liquorRepository;
    private final IngredientRepository ingredientRepository;

    @Transactional
    public long cocktailSave(CocktailInsertForm form){

        // 칵테일 맛
        List<String> tastes = form.getTastes();
        List<CocktailTaste> cocktailTastes = new ArrayList<>();

        for (String taste : tastes) {
            CocktailTaste cocktailTaste = new CocktailTaste(taste);
            cocktailTastes.add(cocktailTaste);
        }

        // 칵테일 순서
        List<CocktailSequence> cocktailSequences = new ArrayList<>();
        form.getSequences().forEach(cocktailSequenceDto -> {
            if(cocktailSequenceDto.getContent() != null){
                cocktailSequences.add(cocktailSequenceDto.toEntity());
            }
        });

        // 칵테일 주류
        List<CocktailLiquor> cocktailLiquors = new ArrayList<>();

        form.getLiquors().forEach(cocktailLiquorDto -> {
            if(cocktailLiquorDto.getId() != null){
                Liquor liquor = liquorRepository.findById(cocktailLiquorDto.getId()).get();
                CocktailLiquor cocktailLiquor = CocktailLiquor.builder()
                        .liquor(liquor)
                        .cocktailIngredientQty(cocktailLiquorDto.getQuantity())
                        .build();
                cocktailLiquors.add(cocktailLiquor);
            }
        });

        // 칵테일 재료
        List<CocktailIngredient> cocktailIngredients = new ArrayList<>();

        form.getIngredients().forEach(cocktailIngredientDto -> {
            if(cocktailIngredientDto.getId() != null){
                Ingredient ingredient = ingredientRepository.findById(cocktailIngredientDto.getId()).get();
                CocktailIngredient cocktailIngredient = CocktailIngredient.builder()
                        .ingredient(ingredient)
                        .cocktailIngredientQty(cocktailIngredientDto.getQuantity())
                        .build();
                cocktailIngredients.add(cocktailIngredient);
            }
        });

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

        Cocktail cocktail1 = cocktailRepository.save(cocktail);

        return cocktail1.getId();
    }

    @Transactional
    public void initSave(Cocktail cocktail){
        cocktailRepository.save(cocktail);
    }



    @Transactional
    public long liquorSave(Liquor liquor){
        Liquor liquor1 = liquorRepository.save(liquor);

        return liquor1.getId();
    }

    @Transactional
    public long ingredientSave(Ingredient ingredient){
        Ingredient ingredient1 = ingredientRepository.save(ingredient);

        return ingredient1.getId();
    }

    public List<AttachFileDto> uploadImage(String uploadFolderPath, File uploadPath, MultipartFile[] uploadFile){
        List<AttachFileDto> list = new ArrayList<>();
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

        return list;
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
}
