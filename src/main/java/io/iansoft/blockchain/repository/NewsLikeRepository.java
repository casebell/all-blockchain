package io.iansoft.blockchain.repository;

import io.iansoft.blockchain.domain.NewsLike;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the NewsLike entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NewsLikeRepository extends JpaRepository<NewsLike, Long>, JpaSpecificationExecutor<NewsLike> {

    @Query("select news_like from NewsLike news_like where news_like.user.login = ?#{principal.username}")
    List<NewsLike> findByUserIsCurrentUser();

}
