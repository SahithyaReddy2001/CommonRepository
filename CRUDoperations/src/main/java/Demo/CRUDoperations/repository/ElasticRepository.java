package Demo.CRUDoperations.repository;

import Demo.CRUDoperations.elasticEntity.ElastcOrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticRepository extends ElasticsearchRepository<ElastcOrder,Integer> {

}
