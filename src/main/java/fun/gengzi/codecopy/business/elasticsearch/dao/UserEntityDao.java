package fun.gengzi.codecopy.business.elasticsearch.dao;

import fun.gengzi.codecopy.business.elasticsearch.entity.UserEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 *  <h1>spring data es dao 层对 UserEntity </h1>
 */
@Repository
public interface UserEntityDao extends ElasticsearchRepository<UserEntity, String> {




}