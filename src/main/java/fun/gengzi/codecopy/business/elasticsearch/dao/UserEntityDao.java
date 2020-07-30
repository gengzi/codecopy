package fun.gengzi.codecopy.business.elasticsearch.dao;

import fun.gengzi.codecopy.business.elasticsearch.entity.UserEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <h1>spring data es dao 层对 UserEntity </h1>
 */
@Repository
public interface UserEntityDao extends ElasticsearchRepository<UserEntity, String> {

    /**
     * 根据 userName 返回匹配的用户列表
     *
     * @param userName
     * @return
     */
    List<UserEntity> getByUserName(String userName);


}