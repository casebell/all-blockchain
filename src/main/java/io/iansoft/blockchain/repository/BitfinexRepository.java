package io.iansoft.blockchain.repository;

import io.iansoft.blockchain.domain.Bitfinex;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Bitfinex entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BitfinexRepository extends JpaRepository<Bitfinex, Long> {
    @Query(value = "select * from Bitfinex  as a WHERE  a.createdat = (select createdat from Bitfinex group by createdat order by createdat desc limit 1 ) ",nativeQuery = true)
    List<Bitfinex> findBitfinex();
}
