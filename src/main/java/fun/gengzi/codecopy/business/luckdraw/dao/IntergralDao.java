package fun.gengzi.codecopy.business.luckdraw.dao;

import fun.gengzi.codecopy.business.luckdraw.entity.TbIntegral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * <h1>用户dao层</h1>
 *
 * @author gengzi
 * @date 2020年9月9日16:01:17
 */
@Repository
public interface IntergralDao extends JpaRepository<TbIntegral, Integer> {


    /**
     * 根据活动id，用户id 获取当前用户的积分信息
     * @param activityid 活动id
     * @param uid 用户id
     * @return {@link TbIntegral} 用户活动积分信息
     */
    TbIntegral findFirstByActivityidAndUid(String activityid, String uid);
}
