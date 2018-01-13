package io.iansoft.blockchain.repository;

import io.iansoft.blockchain.domain.Bitfinex;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Bitfinex entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BitfinexRepository extends JpaRepository<Bitfinex, Long> {

}
