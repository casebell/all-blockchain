package io.iansoft.blockchain.repository;

import io.iansoft.blockchain.domain.Bithumb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BithumbRepository extends JpaRepository<Bithumb,Long> {



    @Query(value = "select * from BITHUMB  as a WHERE  a.createdat = (select createdat from bithumb group by createdat order by createdat desc limit 1 ) ",nativeQuery = true)
    List<Bithumb> findBihumbs();
}
