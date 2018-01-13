package io.iansoft.blockchain.repository;

import io.iansoft.blockchain.domain.Coinis;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Coinis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoinisRepository extends JpaRepository<Coinis, Long> {

}
