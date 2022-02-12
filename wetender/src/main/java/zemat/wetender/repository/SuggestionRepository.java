package zemat.wetender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zemat.wetender.domain.suggestion.Suggestion;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {




}
