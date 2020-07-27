package fun.gengzi.codecopy.business.elasticsearch.dao;

import fun.gengzi.codecopy.business.elasticsearch.entity.Conference;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Artur Konczak
 * @author Oliver Gierke
 */
public interface ConferenceRepository extends ElasticsearchRepository<Conference, String> {
}