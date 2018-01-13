package io.iansoft.blockchain.repository;

import io.iansoft.blockchain.domain.Quote;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Quote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

}
