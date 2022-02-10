package zemat.wetender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zemat.wetender.domain.liquor.Liquor;

public interface LiquorRepository extends JpaRepository<Liquor,Long> {
}
