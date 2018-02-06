package io.iansoft.blockchain.repository;

import io.iansoft.blockchain.domain.Ticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the Ticker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TickerRepository extends JpaRepository<Ticker, Long> {

    List<Ticker> findAllByUserId(long userId);
}
