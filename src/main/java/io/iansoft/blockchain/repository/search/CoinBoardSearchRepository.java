package io.iansoft.blockchain.repository.search;

import io.iansoft.blockchain.domain.CoinBoard;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CoinBoard entity.
 */
public interface CoinBoardSearchRepository extends ElasticsearchRepository<CoinBoard, Long> {
}
