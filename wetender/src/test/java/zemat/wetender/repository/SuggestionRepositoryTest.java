package zemat.wetender.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.suggestion.Suggestion;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@SpringBootTest
@Transactional
public class SuggestionRepositoryTest {

    @PersistenceContext EntityManager em;
    @Autowired SuggestionRepository suggestionRepository;



    @Test
    public void 건의사항_저장() throws Exception {

        // given
        Suggestion suggestionOne = new Suggestion("AB", "AB");

        // when
        Suggestion saveOne = suggestionRepository.save(suggestionOne);

        // then
        Assertions.assertThat(saveOne.getId()).isSameAs(suggestionOne.getId());


    }




}