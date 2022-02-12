package zemat.wetender.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.suggestion.Suggestion;
import zemat.wetender.repository.SuggestionRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
public class SuggestionServiceTest {

    @Autowired SuggestionService suggestionService;
    @Autowired SuggestionRepository suggestionRepository;
    @Autowired EntityManager em;

    @Test
    public void 건의사항_등록() throws Exception {

        // given
        Suggestion suggestion = new Suggestion("A", "A");

        // when
        Long insertId = suggestionService.insert(suggestion);

        // then
        assertThat(insertId).isSameAs(suggestion.getId());

    }

    @Test
    public void 건의사항_찾기() throws Exception {

        // given
        Suggestion suggestion = new Suggestion("A", "A");
        Long insertId = suggestionService.insert(suggestion);

        // when
        Optional<Suggestion> suggestionFind = suggestionService.find(insertId);


        // then  이게 맞나...??
        assertThat(suggestionFind.get()).isEqualTo(suggestion);


    }

}