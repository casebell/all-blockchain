package io.iansoft.blockchain.repository;


import io.iansoft.blockchain.domain.EcbExchangeRates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EcbExchangeRatesRepository extends JpaRepository<EcbExchangeRates,Long> {
}
