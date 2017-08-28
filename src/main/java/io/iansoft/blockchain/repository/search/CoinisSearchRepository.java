package io.iansoft.blockchain.repository.search;

import io.iansoft.blockchain.domain.Coinis;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Coinis entity.
 */
public interface CoinisSearchRepository extends ElasticsearchRepository<Coinis, Long> {
}
