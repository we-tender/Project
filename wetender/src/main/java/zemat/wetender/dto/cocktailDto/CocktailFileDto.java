//package zemat.wetender.dto.cocktailDto;
//
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//import zemat.wetender.domain.cocktail.CocktailFile;
//
//@Data
//public class CocktailFileDto {
//
//    private Long id;
//    private String uuid;
//    private String uploadPath;
//    private String fileName;
//    private boolean fileType;
//
//    public CocktailFileDto(){
//
//    }
//
//    public CocktailFileDto(CocktailFile cocktailFile) {
//        this.id = cocktailFile.getId();
//        this.uuid = cocktailFile.getUuid();
//        this.uploadPath = cocktailFile.getUploadPath();
//        this.fileName = cocktailFile.getFileName();
//        this.fileType = cocktailFile.isFileType();
//    }
//
//    public CocktailFile toEntity(){
//
//        CocktailFile cocktailFile = CocktailFile.builder()
//                .fileName(fileName)
//                .fileType(fileType)
//                .uploadPath(uploadPath)
//                .uuid(uuid)
//                .fileType(fileType)
//                .build();
//
//        return cocktailFile;
//    }
//}
