package io.iansoft.blockchain.repository.search;

import io.iansoft.blockchain.domain.Ticker;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Ticker entity.
 */
public interface TickerSearchRepository extends ElasticsearchRepository<Ticker, Long> {
}
