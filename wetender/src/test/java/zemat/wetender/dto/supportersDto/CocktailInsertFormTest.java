package zemat.wetender.dto.supportersDto;

import org.junit.jupiter.api.Test;
import zemat.wetender.domain.cocktail.Cocktail;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CocktailInsertFormTest {

    @Test
    void 입력값_검증(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        CocktailInsertForm cocktailInsertForm = new CocktailInsertForm();
        cocktailInsertForm.setName(" ");
        cocktailInsertForm.setAbv(101);
        cocktailInsertForm.setContent("내용");

        Set<ConstraintViolation<CocktailInsertForm>> violations = validator.validate(cocktailInsertForm);
        for (ConstraintViolation<CocktailInsertForm> violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("violation.getMessage() = " + violation.getMessage());
        }
    }
}