package io.iansoft.blockchain.repository;

import io.iansoft.blockchain.domain.Coinis;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Coinis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoinisRepository extends JpaRepository<Coinis,Long> {
    @Query(value = "select * from coinis  as a WHERE  a.createdat = (select createdat from coinis group by createdat order by createdat desc limit 1 ) ",nativeQuery = true)
    List<Coinis> findCoinis();
}
