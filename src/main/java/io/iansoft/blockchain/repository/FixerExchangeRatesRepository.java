package io.iansoft.blockchain.repository;


import io.iansoft.blockchain.domain.FixerExchangeRates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FixerExchangeRatesRepository extends JpaRepository<FixerExchangeRates,Long> {
    FixerExchangeRates findFirst1ByBaseOrderByCreatedDateDesc(String usd);
}
