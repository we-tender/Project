package zemat.wetender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.suggestion.Suggestion;
import zemat.wetender.repository.SuggestionRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SuggestionService {

    private final SuggestionRepository suggestionRepository;

    // 건의사항 저장
    public Long insert(Suggestion suggestion) {

        Suggestion suggestion1 = suggestionRepository.save(suggestion);

        return suggestion1.getId();

    }

    // 건의사항 전체 조회
    public List<Suggestion> findAll()
    {
        List<Suggestion> result = suggestionRepository.findAll();

        return result;
    }

    // 건의사항 하나 조회
    public Optional<Suggestion> findById(Long suggestionId)
    {
        Optional<Suggestion> result = suggestionRepository.findById(suggestionId);

        return result;
    }

    // 건의사항 삭제
    public void delete(Long suggestionId)
    {

        Optional<Suggestion> suggestionFind = suggestionRepository.findById(suggestionId);
        Suggestion suggestion = suggestionFind.get();
        suggestionRepository.delete(suggestion);

    }


    // 건의사항 페이지로 조회하기
    public Page<Suggestion> page(String searchText, Pageable pageable)
    {
        return suggestionRepository.findBySuggestionTitleOrSuggestionContentContaining(searchText, searchText, pageable);
    }


}
