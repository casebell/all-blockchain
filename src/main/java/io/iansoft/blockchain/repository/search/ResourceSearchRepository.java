package io.iansoft.blockchain.repository.search;

import io.iansoft.blockchain.domain.Resource;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Resource entity.
 */
public interface ResourceSearchRepository extends ElasticsearchRepository<Resource, Long> {
}
