package io.iansoft.blockchain.repository;

import io.iansoft.blockchain.domain.AirDrop;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AirDrop entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AirDropRepository extends JpaRepository<AirDrop, Long> {

}
