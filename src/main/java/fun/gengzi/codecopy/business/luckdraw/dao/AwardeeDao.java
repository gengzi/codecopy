package fun.gengzi.codecopy.business.luckdraw.dao;

import fun.gengzi.codecopy.business.luckdraw.entity.TbAwardee;
import fun.gengzi.codecopy.business.luckdraw.entity.TbIntegral;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <h1>发奖记录dao层</h1>
 *
 * @author gengzi
 * @date 2020年9月11日13:36:51
 */
@Repository
public interface AwardeeDao extends JpaRepository<TbAwardee, Integer> {

    /**
     * 返回当前最新的五条获奖信息
     *
     * @param activityId 活动id
     * @return List<TbAwardee>
     */
    @Cacheable(cacheManager = "loclRedisCacheManagers", value = "LUCKDRAW_ACTIVITY_AWARDEE_LIMIT5", key = "#activityId", sync = true)
    @Query(value = "SELECT * FROM tb_awardee t WHERE t.activity_id = :activityId ORDER BY awardee_time LIMIT  0,5", nativeQuery = true)
    List<TbAwardee> qryTbAwardeeInfoByActivityIdAndDateNew(String activityId);


    /**
     * 根据活动id和获奖人id，查询获奖信息
     * @param activityId 活动id
     * @param awardeeId 获奖人id
     * @return
     */
    List<TbAwardee> findTbAwardeeByActivityIdAndAwardeeIdOrderByAwardeeTime(String activityId,String awardeeId);

    /**
     * 根据活动id和获奖人id，查询最新一条数据
     * @param activityId 活动id
     * @param awardeeId 获奖人id
     * @return
     */
    TbAwardee findTopByActivityIdAndAwardeeIdOrderByAwardeeTime(String activityId,String awardeeId);







}
