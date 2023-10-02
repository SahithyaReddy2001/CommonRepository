package Demo.CRUDoperations.repository;

import Demo.CRUDoperations.elasticEntity.ElasticOrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticRepository extends ElasticsearchRepository<ElasticOrder,Integer> {

}
