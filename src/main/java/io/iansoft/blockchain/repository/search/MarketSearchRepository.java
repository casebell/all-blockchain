package io.iansoft.blockchain.repository.search;

import io.iansoft.blockchain.domain.Market;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Market entity.
 */
public interface MarketSearchRepository extends ElasticsearchRepository<Market, Long> {
}
