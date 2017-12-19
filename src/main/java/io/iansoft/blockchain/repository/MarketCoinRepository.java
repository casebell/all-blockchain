package io.iansoft.blockchain.repository;

import io.iansoft.blockchain.domain.MarketCoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the MarketCoin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketCoinRepository extends JpaRepository<MarketCoin, Long> {

}
