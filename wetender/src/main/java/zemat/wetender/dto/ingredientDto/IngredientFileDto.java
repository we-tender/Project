//package zemat.wetender.dto.ingredientDto;
//
//import lombok.Getter;
//import lombok.Setter;
//import zemat.wetender.domain.ingredient.IngredientFile;
//
//@Getter @Setter
//public class IngredientFileDto {
//
//    private Long id;
//    private String uuid;
//    private String uploadPath;
//    private String fileName;
//    private boolean fileType;
//
//    public IngredientFileDto() {
//    }
//
//    public IngredientFileDto(IngredientFile ingredientFile) {
//        this.id = ingredientFile.getId();
//        this.uuid = ingredientFile.getUuid();
//        this.uploadPath = ingredientFile.getUploadPath();
//        this.fileName = ingredientFile.getFileName();
//        this.fileType = ingredientFile.isFileType();
//    }
//
//
//    public IngredientFile toEntity(){
//        return IngredientFile.builder()
//                .fileName(fileName)
//                .fileType(fileType)
//                .uploadPath(uploadPath)
//                .uuid(uuid)
//                .build();
//    }
//}
