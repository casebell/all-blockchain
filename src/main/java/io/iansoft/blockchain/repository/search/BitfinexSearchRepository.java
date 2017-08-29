package io.iansoft.blockchain.repository.search;

import io.iansoft.blockchain.domain.Bitfinex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Bitfinex entity.
 */
public interface BitfinexSearchRepository extends ElasticsearchRepository<Bitfinex, Long> {
}
