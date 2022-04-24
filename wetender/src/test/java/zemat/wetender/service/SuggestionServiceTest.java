package zemat.wetender.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.suggestion.Suggestion;
import zemat.wetender.dto.suggestionDto.SuggestionInsertDto;
import zemat.wetender.repository.suggestion.SuggestionRepository;
import zemat.wetender.service.suggestion.SuggestionService;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.linesOf;


@SpringBootTest
@Transactional
public class SuggestionServiceTest {

    @Autowired
    SuggestionService suggestionService;
    @Autowired SuggestionRepository suggestionRepository;
    @Autowired EntityManager em;

    @Test
    public void 건의사항_등록() throws Exception {

        // given
        SuggestionInsertDto suggestionInsertDto = new SuggestionInsertDto("A", "A");

        // when
        Long insertId = suggestionService.insert(suggestionInsertDto);

        // then
        // assertThat(insertId).isSameAs(suggestion.getId());

    }

    @Test
    public void 건의사항_조회() throws Exception {

        // given
        SuggestionInsertDto suggestionInsertDto = new SuggestionInsertDto("A", "A");
        Long insertId = suggestionService.insert(suggestionInsertDto);

        // when
//        Optional<Suggestion> suggestionFind = suggestionService.findById(insertId);


        // then  이게 맞나...??
//        assertThat(suggestionFind.get()).isEqualTo(suggestion);


    }

    @Test
    public void 건의사항_삭제() throws Exception {

        // given
        SuggestionInsertDto suggestionInsertDto = new SuggestionInsertDto("A", "A");
        Long insertId = suggestionService.insert(suggestionInsertDto);
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
        SuggestionInsertDto suggestionInsertDto = new SuggestionInsertDto("A", "A");
        Long insertId = suggestionService.insert(suggestionInsertDto);
        String searchText1 = "A";
        String searchText2 = "B";


        Pageable pageable = PageRequest.of(0, 5);

        // when
        Page<Suggestion> page = suggestionService.searchPage(searchText1, pageable);

        // then
        System.out.println(page);
        assertThat(page.getTotalElements()).isSameAs(1L);


    }


    @Test
    public void 게시글_게시판() throws Exception {

        // given
        SuggestionInsertDto suggestionInsertDto = new SuggestionInsertDto("A", "A");
        suggestionService.insert(suggestionInsertDto);
        Long suggestionId1 = 1L;

        // then
//        List<Suggestion> suggestions1 = suggestionService.detail_list(suggestionId1);

    }

}