package io.iansoft.blockchain.repository;

import io.iansoft.blockchain.domain.Bittrex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BittrexRepository extends JpaRepository<Bittrex, Long>{

    @Query(value = "select * from bittrex  as a WHERE  a.createdat = (select createdat from bittrex group by createdat order by createdat desc limit 1 ) ",nativeQuery = true)
    List<Bittrex> findBittrexs();
}
