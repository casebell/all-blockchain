package io.iansoft.blockchain.repository;

import io.iansoft.blockchain.domain.Korbit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KorbitRepository extends JpaRepository<Korbit,Long>{
    @Query(value = "select * from Korbit  as a WHERE  a.createdat = (select createdat from KORBIT group by createdat order by createdat desc limit 1 ) ",nativeQuery = true)
    List<Korbit> findKorbits();
}
