package io.iansoft.blockchain.repository;

import io.iansoft.blockchain.domain.Kraken;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Kraken entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KrakenRepository extends JpaRepository<Kraken, Long> {

}
