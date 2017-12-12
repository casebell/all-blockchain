package io.iansoft.blockchain.repository;

import io.iansoft.blockchain.domain.NewsBoard;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the NewsBoard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NewsBoardRepository extends JpaRepository<NewsBoard, Long>, JpaSpecificationExecutor<NewsBoard> {

    @Query("select news_board from NewsBoard news_board where news_board.user.login = ?#{principal.username}")
    List<NewsBoard> findByUserIsCurrentUser();

}
