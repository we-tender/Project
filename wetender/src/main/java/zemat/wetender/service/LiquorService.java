package zemat.wetender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.repository.LiquorRepository;

import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LiquorService {

    private final LiquorRepository liquorRepository;

    public Liquor findById(Long liquorId){
        return liquorRepository.findById(liquorId).get();
    }

    public Page<Liquor> pageFindAll(Pageable pageable){
        return liquorRepository.findAll(pageable);
    }

    public Page<Liquor> pageFindKeyword(Pageable pageable,String keyword){
        return liquorRepository.findByLiquorNameContainingIgnoreCaseOrLiquorEnameContainingIgnoreCase(pageable,keyword,keyword);
    }
}
