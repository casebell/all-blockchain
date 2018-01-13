package io.iansoft.blockchain.repository;

import io.iansoft.blockchain.domain.CoinBoard;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the CoinBoard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoinBoardRepository extends JpaRepository<CoinBoard, Long> {

    @Query("select coin_board from CoinBoard coin_board where coin_board.user.login = ?#{principal.username}")
    List<CoinBoard> findByUserIsCurrentUser();

}
