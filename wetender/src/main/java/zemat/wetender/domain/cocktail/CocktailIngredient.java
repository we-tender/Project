//package zemat.wetender.domain.cocktail;
//
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import zemat.wetender.domain.base.BaseEntity;
//
//import javax.persistence.*;
//
//@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Getter
//public class CocktailIngredient extends BaseEntity {
//
//    @Id
//    @GeneratedValue
//    @Column(name = "cocktail_ingredient_id")
//    private Long id;
//
//    private String cocktailIngredientQty;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "cocktail_id")
//    private Cocktail cocktail;
//
//}
