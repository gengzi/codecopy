package fun.gengzi.codecopy.business.luckdraw.dao;

import fun.gengzi.codecopy.business.luckdraw.entity.TbPrize;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


/**
 * <h1>奖品dao层</h1>
 *
 * @author gengzi
 * @date 2020年9月9日10:48:03
 */
@Repository
public interface PrizeDao extends JpaRepository<TbPrize, String> {

    /**
     * 获取当前活动对应的奖品信息
     *
     * @param activityId 活动id
     * @return {@link List<TbPrize>} 奖品信息列表
     */
    @Cacheable(cacheManager = "localhostCacheManager", value = "LUCKDRAW_LOCALCACHE", key = "getMethodName()")
    List<TbPrize> findByActivityidOrderByProbability(String activityId);


    /**
     * 获取当前时间生效的活动奖品信息
     * @param date
     * @return
     */
    @Query("SELECT t FROM TbPrize t WHERE t.activityid IN ( SELECT t1.id FROM TbActivity t1 WHERE t1.isInvalid = 0 AND ?1 >= t1.starttime and  ?1 <= t1.endtime  )")
    List<TbPrize> qryTbPrizeByEffectActivityId(Date date);





}
