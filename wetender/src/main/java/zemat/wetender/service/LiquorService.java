package zemat.wetender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.repository.LiquorRepository;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LiquorService {

    private final LiquorRepository liquorRepository;

    public Page<Liquor> liquorPageFindAll(Pageable pageable){
        return liquorRepository.findAll(pageable);
    }
}
