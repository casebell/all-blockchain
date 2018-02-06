package io.iansoft.blockchain.repository;

import io.iansoft.blockchain.domain.MarketCoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the MarketCoin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketCoinRepository extends JpaRepository<MarketCoin, Long> {

    List<MarketCoin> findAllByMarketId(long id);

}
