package io.iansoft.blockchain.repository;

import io.iansoft.blockchain.domain.Ticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the Ticker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TickerRepository extends JpaRepository<Ticker, Long> {

}
