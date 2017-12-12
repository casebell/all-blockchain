package io.iansoft.blockchain.repository.search;

import io.iansoft.blockchain.domain.NewsBoard;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the NewsBoard entity.
 */
public interface NewsBoardSearchRepository extends ElasticsearchRepository<NewsBoard, Long> {
}
