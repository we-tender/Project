package zemat.wetender.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import zemat.wetender.domain.suggestion.Suggestion;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {

    // 검색 기능 ( 제목 + 내용 )
    // 인터페이스를 작성할 때 컬럼의 이름이 소문자로 시작해도 대문자로 적어야 한다.
    Page<Suggestion> findBySuggestionTitleOrSuggestionContentContaining(String suggestionTitle, String suggestionContent, Pageable pageable);


}
