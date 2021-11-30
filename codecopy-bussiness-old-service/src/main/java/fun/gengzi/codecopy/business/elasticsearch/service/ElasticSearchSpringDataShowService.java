//package fun.gengzi.codecopy.business.elasticsearch.service;
//
//import fun.gengzi.codecopy.business.elasticsearch.entity.UserEntity;
//
//import java.util.List;
//
///**
// * <h1>使用spring data 操作 es 完成 增删改查</h1>
// */
//public interface ElasticSearchSpringDataShowService {
//
//    /**
//     * 保存user 文档对象
//     *
//     * @param userEntity {@link UserEntity} 用户对象
//     * @return userEntity {@link UserEntity} 用户对象
//     */
//    UserEntity saveUserInfo(UserEntity userEntity);
//
//
//    /**
//     * 全文检索，根据 username 查询
//     * @param userName 用户名称
//     * @return {@link List<UserEntity>}  返回匹配的用户列表
//     */
//    List<UserEntity> searchUserInfo(String userName);
//
//
//}
