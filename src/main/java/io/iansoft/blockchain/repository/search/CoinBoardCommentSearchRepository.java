package io.iansoft.blockchain.repository.search;

import io.iansoft.blockchain.domain.CoinBoardComment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CoinBoardComment entity.
 */
public interface CoinBoardCommentSearchRepository extends ElasticsearchRepository<CoinBoardComment, Long> {
}
