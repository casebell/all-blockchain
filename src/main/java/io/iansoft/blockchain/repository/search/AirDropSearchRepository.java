package io.iansoft.blockchain.repository.search;

import io.iansoft.blockchain.domain.AirDrop;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AirDrop entity.
 */
public interface AirDropSearchRepository extends ElasticsearchRepository<AirDrop, Long> {
}
