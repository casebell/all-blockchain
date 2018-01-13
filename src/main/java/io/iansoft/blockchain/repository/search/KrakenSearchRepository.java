package io.iansoft.blockchain.repository.search;

import io.iansoft.blockchain.domain.Kraken;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Kraken entity.
 */
public interface KrakenSearchRepository extends ElasticsearchRepository<Kraken, Long> {
}
