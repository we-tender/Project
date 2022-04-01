package zemat.wetender.dto.liquorDto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import zemat.wetender.domain.cocktail.CocktailIngredient;
import zemat.wetender.domain.cocktail.CocktailLiquor;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.liquor.LiquorFile;
import zemat.wetender.domain.liquor.LiquorReply;
import zemat.wetender.dto.AttachFileDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter @Setter
public class LiquorDto {

    private Long id;

    private String name;

    private String eName;

    private int abv;

    private String oneLine;

    private String content;

    private List<AttachFileDto> images = new ArrayList<>();

    private List<CocktailLiquor> cocktails = new ArrayList<>();

    // 댓글
    private List<LiquorReply> liquorReplyList = new ArrayList<>();

    // baseEntity
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String createdBy;
    private String lastModifiedBy;

    // BasePostEntity
    private long views;
    private long likes;
    private long replies;

    public void addLiquorFileDto(LiquorFile liquorFile){
        images.add(new AttachFileDto(liquorFile));
    }

    public LiquorDto(Liquor liquor) {
        this.id = liquor.getId();
        this.name = liquor.getLiquorName();
        this.eName = liquor.getLiquorEname();
        this.abv = liquor.getLiquorAbv();
        this.oneLine = liquor.getLiquorOneLine();
        this.content = liquor.getLiquorContent();
        this.cocktails = liquor.getCocktailLiquors();

        List<LiquorFile> liquorFiles = liquor.getLiquorFiles();
        for (LiquorFile liquorFile : liquorFiles) {
            addLiquorFileDto(liquorFile);
        }

        this.createdDate = liquor.getCreatedDate();
        this.lastModifiedDate = liquor.getLastModifiedDate();
        this.createdBy = liquor.getCreatedBy();
        this.lastModifiedBy = liquor.getLastModifiedBy();

        this.liquorReplyList = liquor.getLiquorReplyList();
        this.views = liquor.getViews();
        this.likes = liquor.getViews();
        this.replies = liquor.getReplies();

    }

    //현재 미사용
//    public Liquor toEntity(){
//
//        List<LiquorFile> liquorFiles = new ArrayList<>();
//        for (LiquorFileDto image : images) {
//            LiquorFile liquorFile = image.toEntity();
//            liquorFiles.add(liquorFile);
//        }
//
//        return Liquor.builder()
//                .id(id)
//                .liquorOneLine(oneLine)
//                .liquorAbv(abv)
//                .liquorName(name)
//                .liquorEname(eName)
//                .liquorContent(content)
//                .liquorFiles(liquorFiles)
//                .build();
//
//    }
}
