package io.iansoft.blockchain.repository.search;

import io.iansoft.blockchain.domain.MarketCoin;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MarketCoin entity.
 */
public interface MarketCoinSearchRepository extends ElasticsearchRepository<MarketCoin, Long> {
}
