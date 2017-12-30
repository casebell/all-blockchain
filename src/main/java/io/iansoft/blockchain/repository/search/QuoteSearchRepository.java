package io.iansoft.blockchain.repository.search;

import io.iansoft.blockchain.domain.Quote;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Quote entity.
 */
public interface QuoteSearchRepository extends ElasticsearchRepository<Quote, Long> {
}
