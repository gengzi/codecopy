package fun.gengzi.codecopy.business.elasticsearch.service.impl;

import cn.hutool.core.date.DateUtil;
import fun.gengzi.codecopy.business.elasticsearch.dao.UserEntityDao;
import fun.gengzi.codecopy.business.elasticsearch.entity.UserEntity;
import fun.gengzi.codecopy.business.elasticsearch.service.ElasticSearchSpringDataShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElasticSearchSpringDataShowServiceImpl implements ElasticSearchSpringDataShowService {

    @Autowired
    private UserEntityDao  userEntityDao;
    /**
     * 保存user 文档对象
     *
     * @param userEntity {@link UserEntity} 用户对象
     * @return userEntity {@link UserEntity} 用户对象
     */
    @Override
    public UserEntity saveUserInfo(UserEntity userEntity) {
       return userEntityDao.save(userEntity);
    }
}
