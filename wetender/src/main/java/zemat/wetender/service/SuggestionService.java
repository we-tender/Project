package zemat.wetender.service;

import lombok.RequiredArgsConstructor;
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

    // 건의사항 목록 불러오기
    public List<Suggestion> list()
    {
        List<Suggestion> result = suggestionRepository.findAll();

        return result;
    }

    // 건의사항 하나 불러오기
    public Optional<Suggestion> find(Long suggestionId)
    {
        Optional<Suggestion> result = suggestionRepository.findById(suggestionId);

        return result;
    }


}
