package io.iansoft.blockchain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import io.iansoft.blockchain.domain.Bitflyer;

import java.util.List;

@Repository
public interface BitflyerRepository extends JpaRepository<Bitflyer,Long>{


    @Query(value = "select * from bitflyer  as a WHERE  a.createdat = (select createdat from bitflyer group by createdat order by createdat desc limit 1 ) ",nativeQuery = true)
    List<Bitflyer> findBitflyers();
}
