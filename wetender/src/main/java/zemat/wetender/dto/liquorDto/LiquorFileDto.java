//package zemat.wetender.dto.liquorDto;
//
//import lombok.Getter;
//import lombok.Setter;
//import zemat.wetender.domain.cocktail.CocktailFile;
//import zemat.wetender.domain.liquor.LiquorFile;
//
//@Getter @Setter
//public class LiquorFileDto {
//
//
//    private Long id;
//    private String uuid;
//    private String uploadPath;
//    private String fileName;
//    private boolean fileType;
//
//    public LiquorFileDto() {
//    }
//
//    public LiquorFileDto(LiquorFile liquorFile) {
//        this.id = liquorFile.getId();
//        this.uuid = liquorFile.getUuid();
//        this.uploadPath = liquorFile.getUploadPath();
//        this.fileName = liquorFile.getFileName();
//        this.fileType = liquorFile.isFileType();
//    }
//
//
//    public LiquorFile toEntity(){
//
//        LiquorFile liquorFile = LiquorFile.builder()
//                .fileName(fileName)
//                .fileType(fileType)
//                .uploadPath(uploadPath)
//                .uuid(uuid)
//                .build();
//
//        return liquorFile;
//    }
//}
