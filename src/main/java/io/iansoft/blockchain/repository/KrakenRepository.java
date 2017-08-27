package io.iansoft.blockchain.repository;

import io.iansoft.blockchain.domain.Kraken;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Kraken entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KrakenRepository extends JpaRepository<Kraken, Long> {
    @Query(value = "select * from Kraken  as a WHERE  a.createdat = (select createdat from Kraken group by createdat order by createdat desc limit 1 ) ",nativeQuery = true)
    List<Kraken> findKrakens();
}
