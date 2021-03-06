package zemat.wetender.repository.liquor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zemat.wetender.domain.liquor.Liquor;

import java.util.Optional;

public interface LiquorRepository extends JpaRepository<Liquor,Long> {

    @Query(value = "select l from Liquor l", countQuery = "select count(*) from Liquor m")
    Page<Liquor> findAll(Pageable pageable);



    @Query(countQuery = "select count(*) from Liquor m")
    Page<Liquor> findByLiquorNameContainingIgnoreCaseOrLiquorEnameContainingIgnoreCase(Pageable pageable, String liquorName, String LiquorEname);

    Optional<Liquor> findByLiquorName(String liquorName);

    @Query(value = "select l from Liquor l join fetch l.liquorFiles " +
            "order by l.liquorRecommendation desc",
            countQuery = "select count(l) from Liquor l")
    Page<Liquor> findByLiquorRecommendation(Pageable pageable);


}
