package fun.gengzi.codecopy.business.elasticsearch.service;

import fun.gengzi.codecopy.business.elasticsearch.entity.UserEntity;

/**
 * <h1>使用spring data 操作 es 完成 增删改查</h1>
 */
public interface ElasticSearchSpringDataShowService {

    /**
     * 保存user 文档对象
     *
     * @param userEntity {@link UserEntity} 用户对象
     * @return userEntity {@link UserEntity} 用户对象
     */
    UserEntity saveUserInfo(UserEntity userEntity);


}
