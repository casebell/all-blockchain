package io.iansoft.blockchain.repository;

import io.iansoft.blockchain.domain.CoinBoardComment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the CoinBoardComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoinBoardCommentRepository extends JpaRepository<CoinBoardComment,Long> {

    @Query("select coin_board_comment from CoinBoardComment coin_board_comment where coin_board_comment.user.login = ?#{principal.username}")
    List<CoinBoardComment> findByUserIsCurrentUser();
    
}
