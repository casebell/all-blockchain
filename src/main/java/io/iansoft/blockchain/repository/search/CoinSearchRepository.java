package io.iansoft.blockchain.repository.search;

import io.iansoft.blockchain.domain.Coin;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Coin entity.
 */
public interface CoinSearchRepository extends ElasticsearchRepository<Coin, Long> {
}
