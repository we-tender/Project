package zemat.wetender.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.suggestion.Suggestion;
import zemat.wetender.repository.SuggestionRepository;

import javax.persistence.EntityManager;
import java.util.List;
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
    public void 건의사항_조회() throws Exception {

        // given
        Suggestion suggestion = new Suggestion("A", "A");
        Long insertId = suggestionService.insert(suggestion);

        // when
        Optional<Suggestion> suggestionFind = suggestionService.findById(insertId);


        // then  이게 맞나...??
        assertThat(suggestionFind.get()).isEqualTo(suggestion);


    }

    @Test
    public void 건의사항_삭제() throws Exception {

        // given
        Suggestion suggestion = new Suggestion("A", "A");
        Long insertId = suggestionService.insert(suggestion);
        List<Suggestion> all = suggestionService.findAll();

        // when
        suggestionService.delete(insertId);

        // then
        List<Suggestion> suggestions = suggestionService.findAll();
        assertThat(suggestions).isEmpty();

    }

    @Test
    public void 건의사항_검색() throws Exception {

        // given
        Suggestion suggestion = new Suggestion("A", "A");
        Long insertId = suggestionService.insert(suggestion);
        String searchText1 = "A";
        String searchText2 = "B";


        Pageable pageable = PageRequest.of(0, 5);

        // when
        Page<Suggestion> page = suggestionService.page(searchText1, pageable);

        // then
        System.out.println(page);
        assertThat(page.getTotalElements()).isSameAs(1L);


    }

}