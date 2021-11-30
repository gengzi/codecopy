package fun.gengzi.codecopy.business.luckdraw.dao;

import fun.gengzi.codecopy.business.luckdraw.entity.TbIntegral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * <h1>积分dao层</h1>
 *
 * @author gengzi
 * @date 2020年9月9日16:01:17
 */
@Repository
public interface IntergralDao extends JpaRepository<TbIntegral, Integer> {


    /**
     * 根据活动id，用户id 获取当前用户的积分信息
     *
     * @param activityid 活动id
     * @param uid        用户id
     * @return {@link TbIntegral} 用户活动积分信息
     */
    TbIntegral findFirstByActivityidAndUid(String activityid, String uid);


    /**
     * 根据活动id，用户id，扣除积分数目
     *
     * @param activityId   活动id
     * @param uid          用户id
     * @param intergralNum 扣除积分数目
     * @return
     */
    @Transactional
    @Modifying
    @Query("UPDATE TbIntegral SET integral = integral - ?3 WHERE uid = ?2 AND activityid = ?1 AND integral - ?3 >= 0")
    Integer deductionIntergral(String activityId, String uid, Integer intergralNum);
}
