package io.iansoft.blockchain.repository;

import io.iansoft.blockchain.domain.OkCoinCn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OkCoinCnRepository extends JpaRepository<OkCoinCn,Long> {

    @Query(value = "select * from okcoincn  as a WHERE  a.createdat = (select createdat from okcoincn group by createdat order by createdat desc limit 1 ) ",nativeQuery = true)
    List<OkCoinCn> findOkCOinCn();
}
