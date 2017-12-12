package io.iansoft.blockchain.repository.search;

import io.iansoft.blockchain.domain.NewsLike;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the NewsLike entity.
 */
public interface NewsLikeSearchRepository extends ElasticsearchRepository<NewsLike, Long> {
}
